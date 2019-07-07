package realworld.core.dao.impl;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import realworld.core.dao.ArticleDao;
import realworld.core.dao.UserDao;

// Module to bind the DAO implementations.
public final class DaoModule extends AbstractModule {

  @Override
  public void configure() {
    bind(UserDaoImpl.class).in(Singleton.class);
    bind(UserDao.class).to(UserDaoImpl.class).in(Singleton.class);
    bind(ArticleDaoImpl.class).in(Singleton.class);
    bind(ArticleDao.class).to(ArticleDaoImpl.class).in(Singleton.class);
  }
}
