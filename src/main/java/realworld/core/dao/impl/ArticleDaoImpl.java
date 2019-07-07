package realworld.core.dao.impl;

import com.google.common.collect.ImmutableList;
import com.google.protobuf.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import realworld.core.dao.ArticleDao;
import realworld.proto.Comment;
import realworld.proto.internal.DbArticle;

// A naive DAO implementation for articles, comments, and tags based on in-memory data structure.
final class ArticleDaoImpl implements ArticleDao {

  private final Map<String, DbArticle> articles = new HashMap(); // ID -> article
  private final Map<String, String> articleIdBySlug = new HashMap(); // slug -> article ID
  private final Map<String, HashSet<String>> articlesByAuthor = new HashMap(); // author user ID -> article IDs
  private final Map<String, HashSet<String>> articlesByTag = new HashMap(); // tag -> article IDs
  private final Map<String, HashSet<String>> favoritedUsers = new HashMap(); // article ID -> favorited user IDs

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
  public ImmutableList<DbArticle> listArticles() {
    return ImmutableList.of();
  }

  @Override
  public Comment createComment(String slug, Comment comment) {
    return Comment.getDefaultInstance();
  }

  @Override
  public ImmutableList<Comment> listComments(String slug) {
    return ImmutableList.of();
  }

  @Override
  public void deleteComment(String slug, String id) {
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
  }

  private void deleteDbArticle(String articleId) {
    DbArticle storedArticle = articles.get(articleId);
    articles.remove(storedArticle.getId());
    articleIdBySlug.remove(storedArticle.getSlug());
    favoritedUsers.remove(storedArticle.getId());
    articlesByAuthor.get(storedArticle.getAuthorId()).remove(storedArticle.getId());
    storedArticle.getTagListList()
      .forEach(tag -> articlesByTag.get(tag).remove(storedArticle.getId()));
  }
}
