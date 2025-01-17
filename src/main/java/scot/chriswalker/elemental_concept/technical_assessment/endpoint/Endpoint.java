package scot.chriswalker.elemental_concept.technical_assessment.endpoint;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import scot.chriswalker.elemental_concept.technical_assessment.exception.*;
import scot.chriswalker.elemental_concept.technical_assessment.model.OutcomeFileLine;
import scot.chriswalker.elemental_concept.technical_assessment.orchestration.FileConversionOrchestrationService;
import scot.chriswalker.elemental_concept.technical_assessment.validator.IpAddressValidator;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
public class Endpoint {

    private final FileConversionOrchestrationService fileConversionOrchestrationService;
    private final IpAddressValidator ipAddressValidator;

    public Endpoint(
            FileConversionOrchestrationService fileConversionOrchestrationService,
            IpAddressValidator ipAddressValidator) {
        this.fileConversionOrchestrationService = fileConversionOrchestrationService;
        this.ipAddressValidator = ipAddressValidator;
    }

    @PostMapping("endpoint")
    public ResponseEntity<List<OutcomeFileLine>> endpoint(@RequestPart MultipartFile file, HttpServletRequest request) {
        ipAddressValidator.validateIpAddress(request.getRemoteAddr());

        if (MediaType.APPLICATION_JSON_VALUE.equals(file.getContentType())) {
            throw new IncorrectFileContentTypeException();
        }

        try {
            return endpoint(file.getInputStream());
        } catch (IOException e) {
            throw new InitialFileReadException(e);
        }
    }

    private ResponseEntity<List<OutcomeFileLine>> endpoint(InputStream inputStream) {
        var outcomeFile = fileConversionOrchestrationService.convertInputStreamToOutcomeFile(inputStream);
        return new ResponseEntity<>(outcomeFile, HttpStatus.OK);
    }

    @ExceptionHandler({MultipartException.class})
    public ResponseEntity<String> handleException(MultipartException e) {
        return new ResponseEntity<>("The request is invalid, expected a text file.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IncorrectFileContentTypeException.class})
    public ResponseEntity<String> handleException(IncorrectFileContentTypeException e) {
        return new ResponseEntity<>("The request is invalid, expected a text file.", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({IncorrectNumberOfFieldsInLineException.class})
    public ResponseEntity<String> handleException(IncorrectNumberOfFieldsInLineException e) {
        return new ResponseEntity<>(
                "The request is invalid, expected seven fields per line but found " + e.getActualFieldsCount() + ".",
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({UnexpectedTypeException.class})
    public ResponseEntity<String> handleException(UnexpectedTypeException e) {
        return new ResponseEntity<>(
                "The request is invalid, expected a " + e.getExpectedType() + " at index " + e.getExpectedIndex() + ".",
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({RequestFromBlockedRegionException.class})
    public ResponseEntity<String> handleException(RequestFromBlockedRegionException e) {
        return new ResponseEntity<>("This content is not available in your region.", HttpStatus.FORBIDDEN);
    }
}
