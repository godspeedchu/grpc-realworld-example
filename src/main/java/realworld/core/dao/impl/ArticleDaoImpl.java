package realworld.core.dao.impl;

import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import realworld.core.dao.ArticleDao;
import realworld.core.dao.UserDao;
import realworld.proto.internal.DbArticle;
import realworld.proto.internal.DbComment;

// A naive DAO implementation for articles, comments, and tags based on in-memory data structure.
final class ArticleDaoImpl implements ArticleDao {

  private final Map<String, DbArticle> articles = new HashMap(); // ID -> article
  private final Map<String, String> articleIdBySlug = new HashMap(); // slug -> article ID
  private final Map<String, Set<String>> articlesByAuthor = new HashMap(); // author user ID -> article IDs
  private final Map<String, Set<String>> articlesByTag = new HashMap(); // tag -> article IDs
  private final Map<String, Set<String>> favoritedUsers = new HashMap(); // article ID -> favorited user IDs
  private final Map<String, Map<String, DbComment>> comments = new HashMap(); // article ID -> (comment ID -> comments)
  private final UserDao userDao;

  @Inject
  ArticleDaoImpl(UserDao userDao) {
    this.userDao = userDao;
  }

  @Override
  public Optional<DbArticle> getArticle(String articleId) {
    return articles.containsKey(articleId)
      ? Optional.of(articles.get(articleId))
      : Optional.empty();
  }

  @Override
  public Optional<DbArticle> getArticleBySlug(String slug) {
    return articleIdBySlug.containsKey(slug)
      ? Optional.of(articles.get(articleIdBySlug.get(slug)))
      : Optional.empty();
  }

  @Override
  public DbArticle createArticle(DbArticle article) {
    Instant now = Instant.now();
    Timestamp nowTs = Timestamp.newBuilder()
      .setSeconds(now.getEpochSecond())
      .setNanos(now.getNano())
      .build();
    article = article.toBuilder()
      .setId(UUID.randomUUID().toString())
      .setSlug(article.getTitle().replace(' ', '-'))
      .setCreatedAt(nowTs)
      .setUpdatedAt(nowTs)
      .build();
    createDbArticle(article);
    return article;
  }

  @Override
  public DbArticle updateArticle(DbArticle article) {
    if (!articles.containsKey(article.getId())) {
      throw new RuntimeException("article doesn't exist.");
    }
    deleteDbArticle(article.getId());

    Instant now = Instant.now();
    Timestamp nowTs = Timestamp.newBuilder()
      .setSeconds(now.getEpochSecond())
      .setNanos(now.getNano())
      .build();
    article = article.toBuilder()
        .setSlug(article.getTitle().replace(' ', '-'))
        .setUpdatedAt(nowTs)
        .build();
    createDbArticle(article);
    return article;
  }

  @Override
  public void deleteArticle(String articleId) {
    if (!articles.containsKey(articleId)) {
      return;
    }
    deleteDbArticle(articleId);
  }

  @Override
  public boolean isFavoriteArticle(String userId, String articleId) {
    if (!articles.containsKey(articleId)) {
      throw new RuntimeException("article doesn't exist.");
    }
    return favoritedUsers.get(articleId).contains(userId);
  }

  @Override
  public DbArticle favoriteArticle(String userId, String articleId) {
    if (!articles.containsKey(articleId)) {
      throw new RuntimeException("article doesn't exist.");
    }
    favoritedUsers.get(articleId).add(userId);
    return articles.get(articleId);
  }

  @Override
  public DbArticle unfavoriteArticle(String userId, String articleId) {
    if (!articles.containsKey(articleId)) {
      throw new RuntimeException("article doesn't exist.");
    }
    favoritedUsers.get(articleId).remove(userId);
    return articles.get(articleId);
  }

  @Override
  public int getArticleFavoriteCount(String articleId) {
    if (!articles.containsKey(articleId)) {
      throw new RuntimeException("article doesn't exist.");
    }
    return favoritedUsers.get(articleId).size();
  }

  @Override
  public ImmutableList<DbArticle> listArticles() {
    return ImmutableList.copyOf(articles.values());
  }

  @Override
  public ImmutableList<DbArticle> feedArticles(String userId) {
    ImmutableList.Builder<DbArticle> builder = ImmutableList.builder();
    
    userDao.listFollowings(userId)
      .forEach(authorId -> builder.addAll(
            articlesByAuthor.get(authorId).stream().map(articles::get).collect(Collectors.toList())));
    return builder.build();
  }

  @Override
  public DbComment createComment(String articleId, DbComment comment) {
    Instant now = Instant.now();
    Timestamp nowTs = Timestamp.newBuilder()
      .setSeconds(now.getEpochSecond())
      .setNanos(now.getNano())
      .build();
    comment = comment.toBuilder()
      .setId(UUID.randomUUID().toString())
      .setCreatedAt(nowTs)
      .setUpdatedAt(nowTs)
      .build();
    comments.get(articleId).put(comment.getId(), comment);
    return comment;
  }

  @Override
  public ImmutableList<DbComment> listComments(String articleId) {
    if (comments.containsKey(articleId)) {
      return ImmutableList.copyOf(comments.get(articleId).values());
    }
    return ImmutableList.of();
  }

  @Override
  public void deleteComment(String articleId, String commentId) {
    if (comments.containsKey(articleId)) {
      comments.get(articleId).remove(commentId);
    }
  }

  @Override
  public ImmutableList<String> listTags() {
    return ImmutableList.copyOf(articlesByTag.keySet());
  }

  private void createDbArticle(DbArticle dbArticle) {
    if (articleIdBySlug.containsKey(dbArticle.getSlug())) {
      throw new RuntimeException("article already exists.");
    }
    articles.put(dbArticle.getId(), dbArticle);
    articleIdBySlug.put(dbArticle.getSlug(), dbArticle.getId());
    favoritedUsers.put(dbArticle.getId(), new HashSet<String>());

    if (!articlesByAuthor.containsKey(dbArticle.getAuthorId())) {
      articlesByAuthor.put(dbArticle.getAuthorId(), new HashSet<String>());
    }
    articlesByAuthor.get(dbArticle.getAuthorId()).add(dbArticle.getId());

    dbArticle.getTagListList()
      .forEach(tag -> {
        if (!articlesByTag.containsKey(tag)) {
          articlesByTag.put(tag, new HashSet<String>());
        }
        articlesByTag.get(tag).add(dbArticle.getId());
      });

    comments.put(dbArticle.getId(), new HashMap<String, DbComment>());
  }

  private void deleteDbArticle(String articleId) {
    DbArticle storedArticle = articles.get(articleId);
    articles.remove(storedArticle.getId());
    articleIdBySlug.remove(storedArticle.getSlug());
    favoritedUsers.remove(storedArticle.getId());
    articlesByAuthor.get(storedArticle.getAuthorId()).remove(storedArticle.getId());
    storedArticle.getTagListList()
      .forEach(tag -> articlesByTag.get(tag).remove(storedArticle.getId()));
    comments.remove(storedArticle.getId());
  }
}
