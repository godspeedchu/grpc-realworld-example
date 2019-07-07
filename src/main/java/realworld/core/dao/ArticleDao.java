package realworld.core.dao;

import com.google.common.collect.ImmutableList;
import java.util.Optional;
import realworld.proto.internal.DbArticle;
import realworld.proto.internal.DbComment;

// DAO interface for articles, comments, and tags.
public interface ArticleDao {

  Optional<DbArticle> getArticle(String articleId);

  Optional<DbArticle> getArticleBySlug(String slug);

  DbArticle createArticle(DbArticle article);

  DbArticle updateArticle(DbArticle article);

  void deleteArticle(String articleId);

  boolean isFavoriteArticle(String userId, String articleId);

  DbArticle favoriteArticle(String userId, String articleId);

  DbArticle unfavoriteArticle(String userId, String articleId);

  int getArticleFavoriteCount(String articleId);

  ImmutableList<DbArticle> listArticles();

  ImmutableList<DbArticle> feedArticles(String userId);

  DbComment createComment(String articleId, DbComment comment);

  ImmutableList<DbComment> listComments(String articleId);

  void deleteComment(String articleId, String commentId);

  ImmutableList<String> listTags();
}
