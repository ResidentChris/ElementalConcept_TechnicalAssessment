package scot.chriswalker.elemental_concept.technical_assessment.cucumber;

import io.cucumber.java.en.Given;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class RegionValidationStepDefs {

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
}
