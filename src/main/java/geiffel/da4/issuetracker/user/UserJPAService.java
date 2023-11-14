package geiffel.da4.issuetracker.user;

import geiffel.da4.issuetracker.exceptions.ResourceAlreadyExistsException;
import geiffel.da4.issuetracker.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Qualifier("jpa")
public class UserJPAService implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new ResourceNotFoundException("User", id);
        }
    }

    @Override
    public User create(User newUser) throws ResourceAlreadyExistsException {
        if(userRepository.existsById(newUser.getId())){
            throw new ResourceAlreadyExistsException("User", newUser.getId());
        }
        else{
            return userRepository.save(newUser);
        }
    }

    @Override
    public void update(Long id, User updatedUser) throws ResourceNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            userRepository.save(updatedUser);
        }
        else{
            throw new ResourceNotFoundException("User", id);
        }
    }

    @Override
    public void delete(Long id) throws ResourceNotFoundException {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()){
            userRepository.deleteById(id);
        }
        else{
            throw new ResourceNotFoundException("User", id);
        }
    }
}
