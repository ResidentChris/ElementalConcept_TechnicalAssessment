package scot.chriswalker.elemental_concept.technical_assessment.endpoint;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import scot.chriswalker.elemental_concept.technical_assessment.exception.IncorrectFileContentTypeException;
import scot.chriswalker.elemental_concept.technical_assessment.exception.IncorrectNumberOfFieldsInLineException;
import scot.chriswalker.elemental_concept.technical_assessment.exception.InitialFileReadException;
import scot.chriswalker.elemental_concept.technical_assessment.model.OutcomeFileLine;
import scot.chriswalker.elemental_concept.technical_assessment.orchestration.FileConversionOrchestrationService;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
public class Endpoint {

    private final FileConversionOrchestrationService fileConversionService;

    public Endpoint(FileConversionOrchestrationService fileConversionService) {
        this.fileConversionService = fileConversionService;
    }

    @PostMapping("endpoint")
    public ResponseEntity<List<OutcomeFileLine>> endpoint(@RequestPart MultipartFile file) {
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
        var outcomeFile = fileConversionService.convertInputStreamToOutcomeFile(inputStream);
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
}
