package realworld.core.dao.impl;

import com.google.common.collect.ImmutableList;
import java.lang.RuntimeException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import realworld.core.dao.UserDao;
import realworld.proto.internal.DbUser;

// A naive DAO implementation for users and profiles based on in-memory data structure.
final class UserDaoImpl implements UserDao {

  private final Map<String, DbUser> users = new HashMap(); // ID -> user
  private final Map<String, String> userIdByEmail = new HashMap(); // user email -> ID
  private final Map<String, String> userIdByUsername = new HashMap(); // user username -> ID
  private final Map<String, Set<String>> followers = new HashMap(); // user ID -> follower IDs
  private final Map<String, Set<String>> followings = new HashMap(); // user ID -> following IDs

  @Override
  public Optional<DbUser> getUser(String userId) {
    return users.containsKey(userId) ? Optional.of(users.get(userId)) : Optional.empty();
  }
  
  @Override
  public Optional<DbUser> getUserByEmail(String email) {
    return userIdByEmail.containsKey(email) ? 
      Optional.of(users.get(userIdByEmail.get(email))) : Optional.empty();
  }

  @Override
  public Optional<DbUser> getUserByUsername(String username) {
    return userIdByUsername.containsKey(username) ? 
      Optional.of(users.get(userIdByUsername.get(username))) : Optional.empty();
  }

  @Override
  public DbUser createUser(DbUser user) {
    user = user.toBuilder().setId(UUID.randomUUID().toString()).build();
    createDbUser(user);
    return user;
  }

  @Override
  public DbUser updateUser(DbUser user) {
    if (!users.containsKey(user.getId())) {
      throw new RuntimeException("user does not exist.");
    }
    deleteDbUser(user.getId());
    createDbUser(user);
    return user;
  }

  @Override
  public boolean isFollowingUser(String followerUserId, String targetUserId) {
    return followers.get(targetUserId).contains(followerUserId);
  }

  @Override
  public void followUser(String followerUserId, String targetUserId) {
    followers.get(targetUserId).add(followerUserId);
    followings.get(followerUserId).add(targetUserId);
  }

  @Override
  public void unfollowUser(String followerUserId, String targetUserId) {
    followers.get(targetUserId).remove(followerUserId);
    followings.get(followerUserId).remove(targetUserId);
  }

  @Override
  public ImmutableList<String> listFollowings(String userId) {
    if (followings.containsKey(userId)) {
      return ImmutableList.copyOf(followings.get(userId));
    }
    return ImmutableList.of();
  }

  private void createDbUser(DbUser dbUser) {
    if (userIdByUsername.containsKey(dbUser.getUsername())
        || userIdByEmail.containsKey(dbUser.getEmail())) {
      throw new RuntimeException("user already exists.");
    }
    users.put(dbUser.getId(), dbUser);
    userIdByEmail.put(dbUser.getEmail(), dbUser.getId());
    userIdByUsername.put(dbUser.getUsername(), dbUser.getId());
    followers.put(dbUser.getId(), new HashSet<String>());
    followings.put(dbUser.getId(), new HashSet<String>());
  }

  private void deleteDbUser(String userId) {
    DbUser storedUser = users.get(userId);
    users.remove(storedUser.getId());
    userIdByEmail.remove(storedUser.getEmail());
    userIdByUsername.remove(storedUser.getUsername());
    followers.remove(storedUser.getId());
    followings.remove(storedUser.getId());
  }
}
