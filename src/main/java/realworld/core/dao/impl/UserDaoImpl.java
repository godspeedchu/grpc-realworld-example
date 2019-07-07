package realworld.core.dao.impl;

import java.lang.RuntimeException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import realworld.core.dao.UserDao;
import realworld.proto.Profile;
import realworld.proto.internal.DbUser;

// A naive DAO implementation for users and profiles based on in-memory data structure.
final class UserDaoImpl implements UserDao {

  private final Map<String, DbUser> users = new HashMap(); // ID -> user
  private final Map<String, String> userIdByEmail = new HashMap(); // user email -> ID
  private final Map<String, String> userIdByUsername = new HashMap(); // user username -> ID
  private final Map<String, Set<String>> followers = new HashMap(); // user ID -> follower IDs

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
  public Optional<Profile> getProfile(Optional<String> userId, String username) {
    if (!userIdByUsername.containsKey(username)) {
      return Optional.empty();
    }
    DbUser dbUser = users.get(userIdByUsername.get(username));
    Profile.Builder builder = Profile.newBuilder()
        .setUsername(dbUser.getUsername())
        .setBio(dbUser.getBio())
        .setImage(dbUser.getImage());
    if (userId.isPresent()) {
      builder.setFollowing(followers.get(dbUser.getId()).contains(userId.get()));
    }
    return Optional.of(builder.build());
  }

  @Override
  public Profile followProfile(String id, String username) {
    if (!userIdByUsername.containsKey(username)) {
      throw new RuntimeException("user profile not found.");
    }
    DbUser dbUser = users.get(userIdByUsername.get(username));
    followers.get(dbUser.getId()).add(id);
    return Profile.newBuilder()
        .setUsername(dbUser.getUsername())
        .setBio(dbUser.getBio())
        .setImage(dbUser.getImage())
        .setFollowing(followers.get(dbUser.getId()).contains(id))
        .build();
  }

  @Override
  public Profile unfollowProfile(String id, String username) {
    if (!userIdByUsername.containsKey(username)) {
      throw new RuntimeException("user profile not found.");
    }
    DbUser dbUser = users.get(userIdByUsername.get(username));
    followers.get(dbUser.getId()).remove(id);
    return Profile.newBuilder()
        .setUsername(dbUser.getUsername())
        .setBio(dbUser.getBio())
        .setImage(dbUser.getImage())
        .setFollowing(followers.get(dbUser.getId()).contains(id))
        .build();
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
  }

  private void deleteDbUser(String userId) {
    DbUser storedUser = users.get(userId);
    users.remove(storedUser.getId());
    userIdByEmail.remove(storedUser.getEmail());
    userIdByUsername.remove(storedUser.getUsername());
    followers.remove(storedUser.getId());
  }
}
