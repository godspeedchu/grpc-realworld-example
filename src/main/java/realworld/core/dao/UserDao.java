package realworld.core.dao;

import com.google.common.collect.ImmutableList;
import java.util.Optional;
import realworld.proto.internal.DbUser;

// DAO interface for users and profiles.
public interface UserDao {

  Optional<DbUser> getUser(String userId);

  Optional<DbUser> getUserByEmail(String email);

  Optional<DbUser> getUserByUsername(String username);

  DbUser createUser(DbUser user);

  DbUser updateUser(DbUser user);

  boolean isFollowingUser(String followerUserId, String targetUserId);

  void followUser(String followerUserId, String targetUserId);

  void unfollowUser(String followerUserId, String targetUserId);

  ImmutableList<String> listFollowings(String userId);
}
