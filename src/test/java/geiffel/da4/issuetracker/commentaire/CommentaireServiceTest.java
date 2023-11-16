package geiffel.da4.issuetracker.commentaire;



import geiffel.da4.issuetracker.issue.Issue;
import geiffel.da4.issuetracker.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ContextConfiguration(classes = CommentaireService.class)
public class CommentaireServiceTest {

    private CommentaireService commentaireService;
    private List<Commentaire> commentaires;

    private User user1, user2;
    private Issue issue1, issue2;

    @BeforeEach
    void setUp() {
        user1 = Mockito.mock(User.class);
        user2 = Mockito.mock(User.class);
        issue1 = Mockito.mock(Issue.class);
        issue2 = Mockito.mock(Issue.class);

        Mockito.when(user1.getId()).thenReturn(1L);
        Mockito.when(user2.getId()).thenReturn(2L);
        Mockito.when(issue1.getCode()).thenReturn(1L);
        Mockito.when(issue2.getCode()).thenReturn(2L);

        commentaires = new ArrayList<>(){{
            add(new Commentaire(1L, user1, issue1,"Contenu 1"));
            add(new Commentaire(2L, user1, issue1,"Contenu 2"));
            add(new Commentaire(3L, user2, issue1,"Contenu 3"));
            add(new Commentaire(4L, user2, issue1,"Contenu 4"));
            add(new Commentaire(5L, user2, issue2,"Contenu 5"));
        }};

        commentaireService = new CommentaireLocalService(commentaires);

    }

    @Test
    @Order(0)
    void whenGettingAll_shouldHave5() {
        assertEquals(5, commentaireService.getAll().size());
    }

    @Test
    void whenGettingById_shouldReturnCorrectCommentaire() {
        Commentaire expected_comment = commentaires.get(0);

        Commentaire returnedCommentaire = commentaireService.getById(expected_comment.getId());

        assertEquals(expected_comment.getId(), returnedCommentaire.getId());
        assertEquals(expected_comment.getContenu(), returnedCommentaire.getContenu());
    }

    @Test
    void whenGettingAllByAuthorId_shouldReturnCorrectNumber() {
        assertAll(
                ()->assertEquals(2, commentaireService.getAllByAuthorId(user1.getId()).size()),
                ()->assertEquals(3, commentaireService.getAllByAuthorId(user2.getId()).size()),
                ()->assertEquals(0, commentaireService.getAllByAuthorId(3L).size())
        );
    }

    @Test
    void whenGettingAllByIssueCode_shouldReturnCorrectNumber() {
        assertAll(
                ()->assertEquals(4, commentaireService.getAllByIssueCode(issue1.getCode()).size()),
                ()->assertEquals(1, commentaireService.getAllByIssueCode(issue2.getCode()).size()),
                ()->assertEquals(0, commentaireService.getAllByIssueCode(3L).size())
        );
    }

    @Test
    void whenCreating_shouldIncreaseInSize_andContainTheNewOne() {
        Commentaire commentaire6 = new Commentaire(6L, user1, issue2, "Contenu 6");
        int expectedSize = commentaires.size()+1;

        Commentaire createdCommentaire = commentaireService.create(commentaire6);

        assertAll(
                ()->assertEquals(commentaire6, createdCommentaire),
                ()->assertEquals(expectedSize, commentaireService.getAll().size()),
                ()->assertTrue(commentaireService.getAll().contains(commentaire6))
        );
    }

    @Test
    void whenUpdating_shouldBeModified() {
        Commentaire initialComment1 = commentaires.get(3);
        Commentaire initialComment2 = commentaires.get(4);
        Commentaire initialComment3 = commentaires.get(1);
        String newContenu = "Modified content.";
        Commentaire toUpdate1 = new Commentaire(initialComment1.getId(), initialComment1.getAuthor(),
                initialComment1.getIssue(), newContenu);
        Commentaire toUpdate2 = new Commentaire(initialComment2.getId(), initialComment2.getAuthor(),
                issue1, initialComment2.getContenu());
        Commentaire toUpdate3 = new Commentaire(initialComment3.getId(), user2, initialComment3.getIssue(),
                initialComment3.getContenu());


        commentaireService.update(initialComment1.getId(), toUpdate1);
        commentaireService.update(initialComment2.getId(), toUpdate2);
        commentaireService.update(initialComment3.getId(), toUpdate3);

        assertAll(
                ()->assertEquals(newContenu, commentaireService.getById(initialComment1.getId()).getContenu()),
                ()->assertEquals(issue1, commentaireService.getById(initialComment2.getId()).getIssue()),
                ()->assertEquals(user2, commentaireService.getById(initialComment3.getId()).getAuthor())
        );
    }

    @Test
    void whenDeleting_shouldBeSmaller() {
        int expectedSize = commentaires.size()-1;

        commentaireService.delete(3L);

        assertEquals(expectedSize, commentaireService.getAll().size());
    }

}
