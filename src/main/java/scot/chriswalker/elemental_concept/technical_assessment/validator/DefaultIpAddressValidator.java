package scot.chriswalker.elemental_concept.technical_assessment.validator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;
import scot.chriswalker.elemental_concept.technical_assessment.config.ApplicationConfig;
import scot.chriswalker.elemental_concept.technical_assessment.exception.IpAddressValidationFailedException;
import scot.chriswalker.elemental_concept.technical_assessment.exception.RequestFromBlockedDataCentreException;
import scot.chriswalker.elemental_concept.technical_assessment.exception.RequestFromBlockedRegionException;
import scot.chriswalker.elemental_concept.technical_assessment.logging.LogInfoBuilder;

import static scot.chriswalker.elemental_concept.technical_assessment.config.ApplicationConfig.IP_ADDRESS_VALIDATION_ENABLED;

@Service
@Profile(IP_ADDRESS_VALIDATION_ENABLED)
public class DefaultIpAddressValidator implements IpAddressValidator {

    private final ApplicationConfig.IpValidator ipValidator;
    private final LogInfoBuilder logInfoBuilder;

    public DefaultIpAddressValidator(ApplicationConfig applicationConfig, LogInfoBuilder logInfoBuilder) {
        this.ipValidator = applicationConfig.getIpValidator();
        this.logInfoBuilder = logInfoBuilder;
    }

    @Override
    public void validateIpAddress(String remoteAddr) {
        var validationResponse = getValidationResponse(remoteAddr);
        logResult(validationResponse);
        assertLookupSuccess(validationResponse);
        validateRegion(validationResponse);
        validateDataCentre(validationResponse);
    }

    private ValidationResponse getValidationResponse(String remoteAddr) {
        var path = "/json/" + remoteAddr + "?fields=status,message,countryCode,isp,hosting";

        RestClient client = RestClient.create();
        String body;
        try {
            body = client.get()
                    .uri(ipValidator.getUrl() + path)
                    .retrieve()
                    .body(String.class);
        } catch (HttpClientErrorException e) {
            throw new IpAddressValidationFailedException();
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(body, ValidationResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void assertLookupSuccess(ValidationResponse validationResponse) {
        if (!"success".equals(validationResponse.status)) {
            throw new IpAddressValidationFailedException();
        }
    }

    private void validateRegion(ValidationResponse validationResponse) {
        if (ipValidator.getBlockedRegions().contains(validationResponse.countryCode)) {
            throw new RequestFromBlockedRegionException();
        }
    }

    private void validateDataCentre(ValidationResponse validationResponse) {
        if (ipValidator.getBlockedIsps().contains(validationResponse.isp) && validationResponse.hosting == true) {
            throw new RequestFromBlockedDataCentreException();
        }
    }

    private void logResult(ValidationResponse validationResponse) {
        logInfoBuilder.setRequestCountryCode(validationResponse.countryCode);
        logInfoBuilder.setRequestIpProvider(validationResponse.isp);
    }

    private record ValidationResponse(String status,
                                      @Nullable String message,
                                      String countryCode,
                                      String isp,
                                      boolean hosting) {
    }
}
