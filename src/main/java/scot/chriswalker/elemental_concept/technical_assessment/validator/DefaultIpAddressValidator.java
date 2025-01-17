package scot.chriswalker.elemental_concept.technical_assessment.validator;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import scot.chriswalker.elemental_concept.technical_assessment.config.ApplicationConfig;
import scot.chriswalker.elemental_concept.technical_assessment.exception.RequestFromBlockedRegionException;

@Service
public class DefaultIpAddressValidator implements IpAddressValidator {

    private final ApplicationConfig.IpValidator ipValidator;

    public DefaultIpAddressValidator(ApplicationConfig applicationConfig) {
        this.ipValidator = applicationConfig.getIpValidator();
    }

    @Override
    public void validateIpAddress(String remoteAddr) {
        var validationResponse = getValidationResponse(remoteAddr);
        validateRegion(validationResponse);
    }

    private ValidationResponse getValidationResponse(String remoteAddr) {
        var path = "/json/" + remoteAddr + "?fields=status,message,countryCode,isp,hosting";

        RestClient client = RestClient.create();
        String body = client.get()
                .uri(ipValidator.getUrl() + path)
                .retrieve()
                .body(String.class);

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(body, ValidationResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private void validateRegion(ValidationResponse validationResponse) {
        assert ipValidator.getBlockedRegions() != null;
        if (ipValidator.getBlockedRegions().contains(validationResponse.countryCode)) {
            throw new RequestFromBlockedRegionException();
        }
    }

    private record ValidationResponse(String status,
                                      @Nullable String message,
                                      String countryCode,
                                      String isp,
                                      boolean hosting) {
    }
}
