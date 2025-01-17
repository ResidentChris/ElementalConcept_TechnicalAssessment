package scot.chriswalker.elemental_concept.technical_assessment.cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.annotation.Nullable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.json;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class StepDefs {

    private final MockMvc mockMvc;

    public StepDefs(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Nullable
    private MvcResult result;

    @When("a request is received from Hungary")
    public void mockHungarianIpLookup() throws MalformedURLException {
        var regex = "/json/([0-9\\.]*)\\?fields=status,message,countryCode,isp,hosting";
        var response = """
                {
                  "status": "success",
                  "countryCode": "HU",
                  "isp": "A Hungarian ISP",
                  "hosting": false
                }
                """;
        stubFor(get(urlMatching(regex)).willReturn(ok(response)));
    }

    @When("a request is received from China")
    public void mockChineseIpLookup() throws MalformedURLException {
        var regex = "/json/([0-9\\.]*)\\?fields=status,message,countryCode,isp,hosting";
        var response = """
                {
                  "status": "success",
                  "countryCode": "CN",
                  "isp": "A Chinese ISP",
                  "hosting": false
                }
                """;
        stubFor(get(urlMatching(regex)).willReturn(ok(response)));
    }

    @When("a request is received from Spain")
    public void mockSpanishIpLookup() throws MalformedURLException {
        var regex = "/json/([0-9\\.]*)\\?fields=status,message,countryCode,isp,hosting";
        var response = """
                {
                  "status": "success",
                  "countryCode": "ES",
                  "isp": "A Spanish ISP",
                  "hosting": false
                }
                """;
        stubFor(get(urlMatching(regex)).willReturn(ok(response)));
    }

    @When("a request is received from America")
    public void mockAmericanIpLookup() throws MalformedURLException {
        var regex = "/json/([0-9\\.]*)\\?fields=status,message,countryCode,isp,hosting";
        var response = """
                {
                  "status": "success",
                  "countryCode": "US",
                  "isp": "An American ISP",
                  "hosting": false
                }
                """;
        stubFor(get(urlMatching(regex)).willReturn(ok(response)));
    }

    @When("the following file is uploaded:")
    public void usersUploadDataOnAProject(String fileContents) throws Exception {

        var file = new MockMultipartFile(
                "file",
                "EntryFile.txt",
                MediaType.TEXT_PLAIN_VALUE,
                fileContents.getBytes()
        );
        result = mockMvc.perform(multipart("/endpoint").file(file)).andReturn();
    }

    @When("a request is received with a content type of {word}")
    public void receiveRequestWithContentType(String contentType) throws Exception {
        result = mockMvc.perform(post("/endpoint").contentType(contentType)).andReturn();
    }

    @Then("the response code is an HTTP {int} {word}")
    public void validateReturnedData(int responseCode, String responseName) {
        assertThat(result.getResponse().getStatus()).isEqualTo(responseCode);
    }

    @And("the response body contains the following JSON:")
    public void validateReturnedJson(String expectedJson) throws UnsupportedEncodingException {
        String actualJson = result.getResponse().getContentAsString();
        assertThatJson(actualJson).isEqualTo(json(expectedJson));
    }

    @And("the response body contains the following error message:")
    public void validateReturnedErrorMessage(String errorMessage) throws UnsupportedEncodingException {
        assertThat(result.getResponse().getContentAsString()).isEqualTo(errorMessage);
    }

    @When("a multipart request is received containing a file with a content type of {word}")
    public void multipartRequestReceivedWithSpecifiedContentType(String contentType) throws Exception {
        var content = """
                            {"content": "doesn't matter"}
                """;
        var file = new MockMultipartFile(
                "file",
                "EntryFile.json",
                contentType,
                content.getBytes()
        );
        result = mockMvc.perform(multipart("/endpoint").file(file)).andReturn();
    }
}
