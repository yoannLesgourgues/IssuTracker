package geiffel.da4.issuetracker.projet;

import geiffel.da4.issuetracker.exceptions.ResourceNotFoundException;
import geiffel.da4.issuetracker.user.User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public interface ProjetService {
    List<Projet> getAll();

    Projet getById(long id);

    Projet create(Projet projet);

    void update(Projet projet);

    void delete(Projet projet);
}
