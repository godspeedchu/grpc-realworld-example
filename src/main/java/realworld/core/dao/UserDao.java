package realworld.core.dao;

import java.util.Optional;
import realworld.proto.Profile;
import realworld.proto.internal.DbUser;

// DAO interface for users and profiles.
public interface UserDao {

  Optional<DbUser> getUser(String userId);

  Optional<DbUser> getUserByEmail(String email);

  DbUser createUser(DbUser user);

  DbUser updateUser(DbUser user);

  Optional<Profile> getProfile(Optional<String> userId, String username);

  Profile followProfile(String userId, String username);

  Profile unfollowProfile(String userId, String username);
}
