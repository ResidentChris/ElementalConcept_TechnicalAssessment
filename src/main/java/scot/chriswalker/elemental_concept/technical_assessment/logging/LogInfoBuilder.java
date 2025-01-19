package scot.chriswalker.elemental_concept.technical_assessment.logging;

import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;
import scot.chriswalker.elemental_concept.technical_assessment.logging.dto.LogInfo;

import java.util.UUID;

@Service
@RequestScope
public class LogInfoBuilder {

    private UUID requestId;
    private String requestUri;
    private long requestTimestamp;
    private int httpResponseCode;
    private String requestIpAddress;
    private String requestCountryCode;
    private String requestIpProvider;
    private long timeLapsed;

    public LogInfo build() {
        return new LogInfo(
                requestId.toString(),
                requestUri,
                requestTimestamp,
                httpResponseCode,
                requestIpAddress,
                requestCountryCode,
                requestIpProvider,
                timeLapsed
        );
    }

    public void setRequestId(UUID requestId) {
        this.requestId = requestId;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public void setRequestTimestamp(long requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public void setHttpResponseCode(int httpResponseCode) {
        this.httpResponseCode = httpResponseCode;
    }

    public void setRequestIpAddress(String requestIpAddress) {
        this.requestIpAddress = requestIpAddress;
    }

    public void setRequestCountryCode(String requestCountryCode) {
        this.requestCountryCode = requestCountryCode;
    }

    public void setRequestIpProvider(String requestIpProvider) {
        this.requestIpProvider = requestIpProvider;
    }

    public void setTimeLapsed(long timeLapsed) {
        this.timeLapsed = timeLapsed;
    }
}
