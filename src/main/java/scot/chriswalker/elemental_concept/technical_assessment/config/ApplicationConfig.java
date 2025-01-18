package scot.chriswalker.elemental_concept.technical_assessment.config;

import jakarta.annotation.Nullable;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties
public class ApplicationConfig {

    public static final String IP_ADDRESS_VALIDATION_DISABLED = "ipAddressValidationDisabled";
    public static final String IP_ADDRESS_VALIDATION_ENABLED = "!" + IP_ADDRESS_VALIDATION_DISABLED;

    @Nullable
    private IpValidator ipValidator;

    @Nullable
    public IpValidator getIpValidator() {
        return ipValidator;
    }

    public void setIpValidator(@Nullable IpValidator ipValidator) {
        this.ipValidator = ipValidator;
    }

    public static class IpValidator {
        @Nullable
        private String url;

        @Nullable
        private List<String> blockedRegions;

        @Nullable
        private List<String> blockedIsps;

        @Nullable
        public String getUrl() {
            return url;
        }

        public void setUrl(@Nullable String url) {
            this.url = url;
        }

        @Nullable
        public List<String> getBlockedRegions() {
            return blockedRegions;
        }

        public void setBlockedRegions(@Nullable List<String> blockedRegions) {
            this.blockedRegions = blockedRegions;
        }

        @Nullable
        public List<String> getBlockedIsps() {
            return blockedIsps;
        }

        public void setBlockedIsps(@Nullable List<String> blockedIsps) {
            this.blockedIsps = blockedIsps;
        }
    }
}
