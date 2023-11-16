package geiffel.da4.issuetracker.projet;

import geiffel.da4.issuetracker.exceptions.ResourceAlreadyExistsException;
import geiffel.da4.issuetracker.exceptions.ResourceNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ProjetServiceTest {

    private ProjetService projetService;

    private List<Projet> projets;

    @BeforeEach
    void setUp() {
        projets = new ArrayList<>() {{
            add(new Projet(1L,"Projet1"));
            add(new Projet(2L,"Projet2"));
            add(new Projet(3L,"Projet3"));
        }};
        projetService = new ProjetLocalService(projets);
    }

    @Test
    void whenGettingAll_shouldReturn3() {
        assertEquals(3, projetService.getAll().size());
    }

    @Test
    void whenGettingById_shouldReturnIfExists_andBeTheSame() {
        assertAll(
                () -> assertEquals(projets.get(0), projetService.getById(1L)),
                () -> assertThrows(ResourceNotFoundException.class, () -> projetService.getById(12L))
    );
    }

    @Test
    void whenCreating_ShouldReturnSame() {
        Projet projet = new Projet(5L, "Projet5");
        assertEquals(projet, projetService.create(projet));
    }

    @Test
    void whenExisting_ShouldNotBeCreated(){
        Projet projet = new Projet(5L, "Projet5");
        projetService.create(projet);
        assertThrows(ResourceAlreadyExistsException.class, () -> projetService.create(projet));
    }

    @Test
    void whenCreating_ShouldBeInTheList(){
        Projet projet = new Projet(5L, "Projet5");
        projetService.create(projet);
        assertEquals(projet,projetService.getById(projet.getId()));
    }

    @Test
    void whenUpdating_ShouldExist(){
        Projet projet = new Projet(5L, "Projet5");
        assertThrows(ResourceNotFoundException.class, () -> projetService.update(projet));
    }

    @Test
    void whenUpdate_ShouldModify(){
        Projet projet = new Projet(5L, "Projet5");
        Projet newProjet = new Projet(projet.getId(), "ProjetNew");
        projetService.update(projet);
        Projet updateProjet = projetService.getById(newProjet.getId());
        assertNotEquals(newProjet,updateProjet);
    }

    @Test
    void whenDeleting_ShouldNotExist(){
        Projet projet = new Projet(5L, "Projet5");
        projetService.delete(projet);
        assertFalse(projetService.getAll().contains(projet));
    }


    @Test
    void whenDeleting_ShouldDelete(){

    }
}