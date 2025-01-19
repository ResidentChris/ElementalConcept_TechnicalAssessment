package scot.chriswalker.elemental_concept.technical_assessment.cucumber;

import io.cucumber.java.en.Given;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class ValidationFailureStepDefs {

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
                  "isp": "Datapest",
                  "hosting": true
                }
                """;
        stubFor(get(urlMatching(regex)).willReturn(ok(response)));
    }
}
