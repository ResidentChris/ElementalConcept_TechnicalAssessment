package scot.chriswalker.elemental_concept.technical_assessment.logging.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "log")
public class LogInfo {

    @Id
    private String requestId;
    private String requestUri;
    private long requestTimestamp;
    private int httpResponseCode;
    private String requestIpAddress;
    private String requestCountryCode;
    private String requestIpProvider;
    private long timeLapsed;

    public LogInfo() {
        // Do nothing.
    }

    public LogInfo(String requestId, String requestUri, long requestTimestamp, int httpResponseCode, String requestIpAddress, String requestCountryCode, String requestIpProvider, long timeLapsed) {
        this.requestId = requestId;
        this.requestUri = requestUri;
        this.requestTimestamp = requestTimestamp;
        this.httpResponseCode = httpResponseCode;
        this.requestIpAddress = requestIpAddress;
        this.requestCountryCode = requestCountryCode;
        this.requestIpProvider = requestIpProvider;
        this.timeLapsed = timeLapsed;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public void setRequestUri(String requestUri) {
        this.requestUri = requestUri;
    }

    public long getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(long requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public int getHttpResponseCode() {
        return httpResponseCode;
    }

    public void setHttpResponseCode(int httpResponseCode) {
        this.httpResponseCode = httpResponseCode;
    }

    public String getRequestIpAddress() {
        return requestIpAddress;
    }

    public void setRequestIpAddress(String requestIpAddress) {
        this.requestIpAddress = requestIpAddress;
    }

    public String getRequestCountryCode() {
        return requestCountryCode;
    }

    public void setRequestCountryCode(String requestCountryCode) {
        this.requestCountryCode = requestCountryCode;
    }

    public String getRequestIpProvider() {
        return requestIpProvider;
    }

    public void setRequestIpProvider(String requestIpProvider) {
        this.requestIpProvider = requestIpProvider;
    }

    public long getTimeLapsed() {
        return timeLapsed;
    }

    public void setTimeLapsed(long timeLapsed) {
        this.timeLapsed = timeLapsed;
    }
}
