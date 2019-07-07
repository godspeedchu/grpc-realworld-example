package realworld.core.dao;

import com.google.common.collect.ImmutableList;
import java.util.Optional;
import realworld.proto.Comment;
import realworld.proto.internal.DbArticle;

// DAO interface for articles, comments, and tags.
public interface ArticleDao {

  Optional<DbArticle> getArticle(String articleId);

  Optional<DbArticle> getArticleBySlug(String slug);

  DbArticle createArticle(DbArticle article);

  DbArticle updateArticle(DbArticle article);

  void deleteArticle(String articleId);

  DbArticle favoriteArticle(String userId, String articleId);

  DbArticle unfavoriteArticle(String userId, String articleId);

  ImmutableList<DbArticle> listArticles();

  Comment createComment(String slug, Comment comment);

  ImmutableList<Comment> listComments(String slug);

  void deleteComment(String slug, String id);

  ImmutableList<String> listTags();
}
