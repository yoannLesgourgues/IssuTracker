package geiffel.da4.issuetracker.issue;

import com.fasterxml.jackson.databind.ObjectMapper;
import geiffel.da4.issuetracker.exceptions.ExceptionHandlingAdvice;
import geiffel.da4.issuetracker.exceptions.ResourceNotFoundException;
import geiffel.da4.issuetracker.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest
@AutoConfigureMockMvc(addFilters = false)
@ContextConfiguration(classes = IssueController.class)
@Import(ExceptionHandlingAdvice.class)
public class IssueControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private IssueService issueService;

    private List<Issue> issues;

    private ObjectMapper mapper = new ObjectMapper();

    private User author = Mockito.mock(User.class);

    @BeforeEach
    void setup() {

        issues = new ArrayList<>(){{
            add(new Issue(1L, "blah1", "some content1", author));
            add(new Issue(2L, "blah2", "some content2", author));
            add(new Issue(3L, "blah3", "some content3", author));
            add(new Issue(4L, "blah4", "some content4", author));
            add(new Issue(5L, "blah5", "some content5", author));
        }};
        Mockito.when(issueService.getAll()).thenReturn(issues);
        Mockito.when(issueService.getByCode(1L)).thenReturn(issues.get(0));
    }

    @Test
    void whenQueryingRoot_shouldReturn5IssuesInJson() throws Exception {
        mockMvc.perform(get("/issues")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(content().contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$", hasSize(5))
        ).andDo(print());
    }

    @Test
    void whenGetWithCode1_shouldReturnIssue1() throws Exception {
        mockMvc.perform(get("/issues/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(content().contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()
        ).andExpect(jsonPath("$", hasEntry("code", 1))
        ).andDo(print());
    }

    @Test
    void whenCreatingIssue_shouldGetLinkToResource() throws Exception {
        Issue issue = new Issue(6L, "issue", "it doesn't work", author);
        Mockito.when(issueService.create(Mockito.any(Issue.class))).thenReturn(issue);

        String toSend = mapper.writeValueAsString(issue);

        mockMvc.perform(post("/issues")
                .contentType(MediaType.APPLICATION_JSON)
                .content(toSend)
        ).andExpect(status().isCreated()
        ).andExpect(header().string("Location","/issues/"+issue.getCode())
        ).andDo(print()).andReturn();
    }

    @Test
    void whenUpdateIssue_shouldBeNoContent_andPassCorrectIssueToService() throws Exception {
        Issue issue = issues.get(0);
        issue.setCode(7L);

        ArgumentCaptor<Issue> issue_received = ArgumentCaptor.forClass(Issue.class);

        mockMvc.perform(put("/issues/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(issue))
        ).andExpect(status().isNoContent()
        ).andDo(print()).andReturn();

        Mockito.verify(issueService).update(Mockito.anyLong(), issue_received.capture());
        assertEquals(issue, issue_received.getValue());
    }

    @Test
    void whenDelete_shouldCallServiceWithCorrectCode() throws Exception {
        Long code_toSend = 1L;

        mockMvc.perform(delete("/issues/"+code_toSend)
        ).andExpect(status().isNoContent()
        ).andDo(print()).andReturn();

        ArgumentCaptor<Long> code_received = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(issueService).delete(code_received.capture());
        assertEquals(code_toSend, code_received.getValue());
    }

    @Test
    void whenDeleteNonExistingResource_shouldGet404() throws Exception {
        Mockito.doThrow(ResourceNotFoundException.class).when(issueService).delete(Mockito.anyLong());

        mockMvc.perform(delete("/issues/972")
        ).andExpect(status().isNotFound()
        ).andDo(print()).andReturn();
    }

}
