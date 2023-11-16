package geiffel.da4.issuetracker.projet;

import geiffel.da4.issuetracker.exceptions.ResourceAlreadyExistsException;
import geiffel.da4.issuetracker.exceptions.ResourceNotFoundException;
import geiffel.da4.issuetracker.utils.LocalService;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;

@Service
public class ProjetLocalService extends LocalService<Projet, Long> implements ProjetService{

    public ProjetLocalService(List<Projet> projets) {
        super(projets);
    }

    @Override
    protected String getIdentifier() {
        return "id";
    }

    @Override
    public List<Projet> getAll() {
        return super.getAll();
    }

    @Override
    public Projet getById(long id) {
        return super.getByIdentifier(id);
    }

    @Override
    public Projet create(Projet projet) {
        try{
            this.findByProperty(projet.getId());
            throw new ResourceAlreadyExistsException("projet", projet.getId());}
        catch(ResourceNotFoundException e){
                allValues.add(projet);
                return projet;
            }
        }

    @Override
    public void update(Projet projet) {
        try{
            allValues.remove(projet);
            allValues.add(projet);
        }
        catch(ResourceNotFoundException e){
            throw new ResourceNotFoundException("projet", projet.getId());}
    }

    @Override
    public void delete(Projet projet) {
        try {
            allValues.remove(projet);
        }
        catch(ResourceNotFoundException e){
            throw new ResourceNotFoundException("projet", projet.getId());
        }
    }
}
