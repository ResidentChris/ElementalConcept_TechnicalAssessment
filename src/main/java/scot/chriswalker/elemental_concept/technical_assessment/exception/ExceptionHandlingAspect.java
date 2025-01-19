package scot.chriswalker.elemental_concept.technical_assessment.exception;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Aspect
@Order(2)
@Service
public class ExceptionHandlingAspect {

    @Around("execution(* scot.chriswalker..Endpoint.endpoint(..))")
    public Object endpoint_endpoint(ProceedingJoinPoint pjp) throws Throwable {
        try {
            return pjp.proceed();

        } catch (IncorrectFileContentTypeException e) {
            return new ResponseEntity<>("The request is invalid, expected a text file.", HttpStatus.BAD_REQUEST);

        } catch (IncorrectNumberOfFieldsInLineException e) {
            return new ResponseEntity<>(
                    "The request is invalid, expected seven fields per line but found " + e.getActualFieldsCount() + ".",
                    HttpStatus.BAD_REQUEST);

        } catch (UnexpectedTypeException e) {
            return new ResponseEntity<>(
                    "The request is invalid, expected a " + e.getExpectedType() + " at index " + e.getExpectedIndex() + ".",
                    HttpStatus.BAD_REQUEST);

        } catch (RequestFromBlockedRegionException e) {
            return new ResponseEntity<>("This content is not available in your region.", HttpStatus.FORBIDDEN);

        } catch (RequestFromBlockedDataCentreException e) {
            return new ResponseEntity<>("This IP address has been blocked because it is a data centre.", HttpStatus.FORBIDDEN);

        } catch (IpAddressValidationFailedException e) {
            return new ResponseEntity<>("IP address validation failed due to an error.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
