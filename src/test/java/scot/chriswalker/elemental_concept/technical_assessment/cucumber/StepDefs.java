package scot.chriswalker.elemental_concept.technical_assessment.cucumber;

import io.cucumber.java.en.When;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StepDefs {

    private final MockMvc mockMvc;

    public StepDefs(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @When("a valid Initial File is uploaded")
    public void usersUploadDataOnAProject() throws Exception {

        var file = new MockMultipartFile(
                "file",
                "hello.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "18148426-89e1-11ee-b9d1-0242ac120002|1X1D14|John Smith|Likes Apricots|Rides A Bike|6.2|12.1".getBytes()
        );
        mockMvc.perform(multipart("/endpoint").file(file)).andExpect(status().isOk());
    }
}
