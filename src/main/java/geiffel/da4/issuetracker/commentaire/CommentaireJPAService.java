package geiffel.da4.issuetracker.commentaire;

import geiffel.da4.issuetracker.exceptions.ResourceAlreadyExistsException;
import geiffel.da4.issuetracker.exceptions.ResourceNotFoundException;
import geiffel.da4.issuetracker.issue.Issue;
import geiffel.da4.issuetracker.issue.IssueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Qualifier("jpa")
public class CommentaireJPAService implements CommentaireService{

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Override
    public List<Commentaire> getAll() {
        return commentaireRepository.findAll();
    }

    @Override
    public Commentaire getById(Long id) {
        Optional< Commentaire > commentaire = commentaireRepository.findById(id);
        if (commentaire.isPresent()) {
            return commentaire.get();
        } else {
            throw new ResourceNotFoundException("Commentaire", id);
        }
    }

    @Override
    public List<Commentaire> getAllByAuthorId(Long id) {
        return commentaireRepository.findAllById(Collections.singleton(id)); //A REFAIRE CHEF
    }

    @Override
    public List<Commentaire> getAllByIssueCode(Long code) {
        return commentaireRepository.findAllById(Collections.singleton(code)); //A REFAIRE CHEF
    }

    @Override
    public Commentaire create(Commentaire commentaire6) {
        if(commentaireRepository.existsById(commentaire6.getId())){
            throw new ResourceAlreadyExistsException("Commentaire", commentaire6.getId());
        }
        {
            return commentaireRepository.save(commentaire6);
        }
    }

    @Override
    public void update(Long id, Commentaire toUpdate1) {
        Optional<Commentaire> commentaire = commentaireRepository.findById(id);
        if (commentaire.isPresent()){
            commentaireRepository.save(toUpdate1);
        }
        else{
            throw new ResourceNotFoundException("Commentaire", id);
        }
    }

    @Override
    public void delete(Long id) {
        Optional<Commentaire> commentaire = commentaireRepository.findById(id);
        if (commentaire.isPresent()){
            commentaireRepository.deleteById(id);
        }
        else{
            throw new ResourceNotFoundException("Commentaire", id);
        }
    }
}
