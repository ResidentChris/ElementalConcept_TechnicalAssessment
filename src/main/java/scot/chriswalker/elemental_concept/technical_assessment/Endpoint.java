package scot.chriswalker.elemental_concept.technical_assessment;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class Endpoint {
    @PostMapping("endpoint")
    public ResponseEntity<List<String>> endpoint(@RequestPart MultipartFile file) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
