package scot.chriswalker.elemental_concept.technical_assessment.cucumber;

import io.cucumber.java.en.Given;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class DataCentreValidationStepDefs {

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
}
