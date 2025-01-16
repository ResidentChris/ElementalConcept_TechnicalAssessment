package scot.chriswalker.elemental_concept.technical_assessment.cucumber;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class StepDefs {

    private final MockMvc mockMvc;

    public StepDefs(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    private MvcResult result;

    @When("the following file is uploaded:")
    public void usersUploadDataOnAProject(String fileContents) throws Exception {

        var file = new MockMultipartFile(
                "file",
                "EntryFile.txt",
                MediaType.TEXT_PLAIN_VALUE,
                fileContents.getBytes()
        );
        result = mockMvc.perform(multipart("/endpoint").file(file)).andExpect(status().isOk()).andReturn();
    }

    @Then("the following JSON is returned:")
    public void validateReturnedData(String expectedJson) throws UnsupportedEncodingException {
        String contentAsString = result.getResponse().getContentAsString();
        assertThatJson(contentAsString).isEqualTo(json(expectedJson));
    }
}
