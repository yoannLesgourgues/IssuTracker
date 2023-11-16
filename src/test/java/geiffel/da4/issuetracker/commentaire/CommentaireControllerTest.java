package geiffel.da4.issuetracker.commentaire;

import com.fasterxml.jackson.databind.ObjectMapper;
import geiffel.da4.issuetracker.exceptions.ExceptionHandlingAdvice;
import geiffel.da4.issuetracker.exceptions.ResourceAlreadyExistsException;
import geiffel.da4.issuetracker.exceptions.ResourceNotFoundException;
import geiffel.da4.issuetracker.issue.Issue;
import geiffel.da4.issuetracker.user.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasEntry;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@ContextConfiguration(classes = CommentaireController.class)
@Import(ExceptionHandlingAdvice.class)
public class CommentaireControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private CommentaireService commentaireService;
    private List<Commentaire> commentaires;
    private Commentaire mockCommentaire1, mockCommentaire2, mockCommentaire3, mockCommentaire4, mockCommentaire5;

    @BeforeEach
    void setUp() {
        mockCommentaire1 = Mockito.mock(Commentaire.class);
        mockCommentaire2 = Mockito.mock(Commentaire.class);
        mockCommentaire3 = Mockito.mock(Commentaire.class);
        mockCommentaire4 = Mockito.mock(Commentaire.class);
        mockCommentaire5 = Mockito.mock(Commentaire.class);
        commentaires = new ArrayList<>(){{
            add(mockCommentaire1);
            add(mockCommentaire2);
            add(mockCommentaire3);
            add(mockCommentaire4);
            add(mockCommentaire5);
        }};

        when(commentaireService.getAll()).thenReturn(commentaires);
        when(mockCommentaire1.getId()).thenReturn(1L);
        when(mockCommentaire1.getContenu()).thenReturn("Commentaire 1");
        when(commentaireService.getById(1L)).thenReturn(mockCommentaire1);
        when(commentaireService.getById(89L)).thenThrow(ResourceNotFoundException.class);
    }

    @Test
    void whenQueryingRoot_shouldbeOk_andGet5Commentaires() throws Exception {
        mockMvc.perform(get("/commentaires")
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$", hasSize(5))
        ).andDo(print());
    }

    @Test
    void whenQueryingById_shouldReturnCorrectCommentaire() throws Exception {
        mockMvc.perform(get("/commentaires/1")
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$", hasEntry("id", 1))
        ).andDo(print());
    }

    @Test
    void whenIdDoesntExist_shouldBe404() throws Exception {
        mockMvc.perform(get("/commentaires/89")
        ).andExpect(status().isNotFound()
        ).andDo(print());
    }

    @Test
    void whenCreating_shouldReturnIsCreated_andShouldReturnURL() throws Exception {
        Long id = 78L;
        Commentaire toCreate = new Commentaire(id, mock(User.class), mock(Issue.class), "To create");
        when(commentaireService.create(any())).thenReturn(toCreate);
        mockMvc.perform(post("/commentaires")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(toCreate))
        ).andExpect(status().isCreated()
        ).andExpect(header().string("Location", "/commentaires/"+id)
        ).andDo(print());
    }

    @Test
    void whenCreatingWithExistingId_shouldReturnConflict() throws Exception {
        Commentaire toCreate = new Commentaire(3L, mock(User.class), mock(Issue.class), "Machin");
        when(commentaireService.create(any())).thenThrow(ResourceAlreadyExistsException.class);

        mockMvc.perform(post("/commentaires")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(toCreate))
        ).andExpect(status().isConflict()
        ).andDo(print());
    }

    @Test
    void whenUpdating_shouldReturnNoContent_andTheCorrectCommentaireShouldHaveBeenUsed() throws Exception {
        Commentaire toUpdate = new Commentaire(3L, mock(User.class), mock(Issue.class), "Machin");
        ArgumentCaptor<Commentaire> commentaire_captor = ArgumentCaptor.forClass(Commentaire.class);

        mockMvc.perform(put("/commentaires/3")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(toUpdate))
        ).andExpect(status().isNoContent()
        ).andDo(print());

        verify(commentaireService).update(anyLong(), commentaire_captor.capture());
        Commentaire received = commentaire_captor.getValue();
        Assertions.assertEquals(toUpdate.getId(), received.getId());
        Assertions.assertEquals(toUpdate.getContenu(), received.getContenu());
    }

    @Test
    void whenDeleting_shouldUseCorrectId_andReturnNoContent() throws Exception {
        Long id = 67L;
        ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);

        mockMvc.perform(delete("/commentaires/"+id)
        ).andExpect(status().isNoContent()
        ).andDo(print());

        verify(commentaireService).delete(captor.capture());
        Assertions.assertEquals(id, captor.getValue());
    }

    @Test
    void whenDeletingNonExisting_shoudReturn404() throws Exception {
        doThrow(ResourceNotFoundException.class).when(commentaireService).delete(anyLong());

        mockMvc.perform(delete("/commentaires/"+78L)
        ).andExpect(status().isNotFound()
        ).andDo(print());
    }

}