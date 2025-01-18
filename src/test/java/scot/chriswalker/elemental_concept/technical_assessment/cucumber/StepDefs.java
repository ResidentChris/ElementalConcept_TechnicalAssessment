package scot.chriswalker.elemental_concept.technical_assessment.cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import jakarta.annotation.Nullable;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.UnsupportedEncodingException;

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

    @Given("a request is received from Hungary")
    public void mockHungarianIpLookup() {
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

    @Given("a request is received from China")
    public void mockChineseIpLookup() {
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

    @Given("a request is received from Spain")
    public void mockSpanishIpLookup() {
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

    @Given("a request is received from America")
    public void mockAmericanIpLookup() {
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

    @Given("a request is received from AWS")
    public void mockAwsIpLookup() {
        var regex = "/json/([0-9\\.]*)\\?fields=status,message,countryCode,isp,hosting";
        var response = """
                {
                  "status": "success",
                  "countryCode": "HU",
                  "isp": "Amazon.com, Inc.",
                  "hosting": true
                }
                """;
        stubFor(get(urlMatching(regex)).willReturn(ok(response)));
    }

    @Given("a request is received from Amazon")
    public void mockAmazonIpLookup() {
        var regex = "/json/([0-9\\.]*)\\?fields=status,message,countryCode,isp,hosting";
        var response = """
                {
                  "status": "success",
                  "countryCode": "HU",
                  "isp": "Amazon.com, Inc.",
                  "hosting": false
                }
                """;
        stubFor(get(urlMatching(regex)).willReturn(ok(response)));
    }

    @Given("a request is received from GCP")
    public void mockGcpIpLookup() {
        var regex = "/json/([0-9\\.]*)\\?fields=status,message,countryCode,isp,hosting";
        var response = """
                {
                  "status": "success",
                  "countryCode": "HU",
                  "isp": "Google LLC",
                  "hosting": true
                }
                """;
        stubFor(get(urlMatching(regex)).willReturn(ok(response)));
    }

    @Given("a request is received from Google")
    public void mockGoogleIpLookup() {
        var regex = "/json/([0-9\\.]*)\\?fields=status,message,countryCode,isp,hosting";
        var response = """
                {
                  "status": "success",
                  "countryCode": "HU",
                  "isp": "Google LLC",
                  "hosting": false
                }
                """;
        stubFor(get(urlMatching(regex)).willReturn(ok(response)));
    }

    @Given("a request is received from ADO")
    public void mockAdoIpLookup() {
        var regex = "/json/([0-9\\.]*)\\?fields=status,message,countryCode,isp,hosting";
        var response = """
                {
                  "status": "success",
                  "countryCode": "HU",
                  "isp": "Microsoft Corporation",
                  "hosting": true
                }
                """;
        stubFor(get(urlMatching(regex)).willReturn(ok(response)));
    }

    @Given("a request is received from Microsoft")
    public void mockMicrosoftIpLookup() {
        var regex = "/json/([0-9\\.]*)\\?fields=status,message,countryCode,isp,hosting";
        var response = """
                {
                  "status": "success",
                  "countryCode": "HU",
                  "isp": "Microsoft Corporation",
                  "hosting": false
                }
                """;
        stubFor(get(urlMatching(regex)).willReturn(ok(response)));
    }

    @Given("a request is received from another data centre")
    public void mockOtherDataCentreIpLookup() {
        var regex = "/json/([0-9\\.]*)\\?fields=status,message,countryCode,isp,hosting";
        var response = """
                {
                  "status": "success",
                  "countryCode": "HU",
                  "isp": "Datapest",
                  "hosting": true
                }
                """;
        stubFor(get(urlMatching(regex)).willReturn(ok(response)));
    }

    @Given("the response code from IP address validation indicates a failure")
    public void mockIpLookupFailureResponseCode() {
        var regex = "/json/([0-9\\.]*)\\?fields=status,message,countryCode,isp,hosting";
        stubFor(get(urlMatching(regex)).willReturn(badRequest()));
    }

    @Given("the response body from IP address validation indicates a failure")
    public void mockIpLookupFailureResponseBody() {
        var regex = "/json/([0-9\\.]*)\\?fields=status,message,countryCode,isp,hosting";
        var response = """
                {
                  "status": "fail",
                  "countryCode": "HU",
                  "isp": "Microsoft Corporation",
                  "hosting": true
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
