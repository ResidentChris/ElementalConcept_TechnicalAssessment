package scot.chriswalker.elemental_concept.technical_assessment.validator;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import static scot.chriswalker.elemental_concept.technical_assessment.config.ApplicationConfig.IP_ADDRESS_VALIDATION_DISABLED;

@Service
@Profile(IP_ADDRESS_VALIDATION_DISABLED)
public class DisabledIpAddressValidator implements IpAddressValidator {

    @Override
    public void validateIpAddress(String remoteAddr) {
        // Do nothing.
    }
}
