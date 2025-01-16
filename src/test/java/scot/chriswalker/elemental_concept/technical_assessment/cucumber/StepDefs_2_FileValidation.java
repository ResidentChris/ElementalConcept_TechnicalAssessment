package scot.chriswalker.elemental_concept.technical_assessment.cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class StepDefs_2_FileValidation {

    private final MockMvc mockMvc;

    public StepDefs_2_FileValidation(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    private MvcResult result;

    @When("a request is received with a content type of {word}")
    public void usersUploadDataOnAProject(String contentType) throws Exception {
        result = mockMvc.perform(post("/endpoint").contentType(contentType)).andReturn();
    }

    @Then("the response code is an HTTP {int} {word}")
    public void validateReturnedData(int responseCode, String responseName) {
        assertThat(result.getResponse().getStatus()).isEqualTo(responseCode);
    }

    @And("the response body is:")
    public void validateReturnedData(String errorMessage) throws UnsupportedEncodingException {
        assertThat(result.getResponse().getContentAsString()).isEqualTo(errorMessage);
    }
}
