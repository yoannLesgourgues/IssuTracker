package geiffel.da4.issuetracker.user;

import java.util.List;

public interface UserService {

    List<User> getAll();

    User getById(Long l);

    User create(User newUser);

    void update(Long id, User updatedUser);

    void delete(Long id);
}
