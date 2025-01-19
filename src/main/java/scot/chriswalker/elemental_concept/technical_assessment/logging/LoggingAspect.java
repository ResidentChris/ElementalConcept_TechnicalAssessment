package scot.chriswalker.elemental_concept.technical_assessment.logging;

import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Aspect
@Order(1)
@Service
public class LoggingAspect {

    private final LogInfoBuilder logInfoBuilder;
    private final LoggingService loggingService;

    public LoggingAspect(LogInfoBuilder logInfoBuilder, LoggingService loggingService) {
        this.logInfoBuilder = logInfoBuilder;
        this.loggingService = loggingService;
    }

    @Around("execution(* scot.chriswalker..Endpoint.endpoint(..))")
    public Object endpoint_endpoint(ProceedingJoinPoint pjp) throws Throwable {
        long requestTimestamp = System.currentTimeMillis();
        logInfoBuilder.setRequestTimestamp(requestTimestamp);
        logInfoBuilder.setRequestId(UUID.randomUUID());

        HttpServletRequest request = (HttpServletRequest) pjp.getArgs()[1];

        logInfoBuilder.setRequestUri(request.getRequestURL().toString());
        logInfoBuilder.setRequestIpAddress(request.getRemoteAddr());

        ResponseEntity<?> result = (ResponseEntity<?>) pjp.proceed();

        logInfoBuilder.setHttpResponseCode(result.getStatusCode().value());
        logInfoBuilder.setTimeLapsed(System.currentTimeMillis() - requestTimestamp);

        loggingService.save(logInfoBuilder.build());

        return result;
    }
}
