package scot.chriswalker.elemental_concept.technical_assessment.parser;

import org.springframework.stereotype.Service;
import scot.chriswalker.elemental_concept.technical_assessment.exception.InitialFileReadException;
import scot.chriswalker.elemental_concept.technical_assessment.model.InitialFileLine;
import scot.chriswalker.elemental_concept.technical_assessment.validator.InitialLineValidator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class InitialFileParser {

    private static final int EXPECTED_FIELD_COUNT = 7;
    private static final int UUID_INDEX = 0;
    private static final int ID = 1;
    private static final int NAME = 2;
    private static final int LIKES = 3;
    private static final int TRANSPORT = 4;
    private static final int AVG_SPEED = 5;
    private static final int TOP_SPEED = 6;

    private final InitialLineValidator validator;

    public InitialFileParser(InitialLineValidator validator) {
        this.validator = validator;
    }

    public List<InitialFileLine> parseFile(InputStream inputStream) {
        var outFile = new ArrayList<InitialFileLine>();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while (bufferedReader.ready()) {
                parseLine(bufferedReader.readLine()).ifPresent(outFile::add);
            }
        } catch (IOException e) {
            throw new InitialFileReadException(e);
        }
        return outFile;
    }

    private Optional<InitialFileLine> parseLine(String line) {
        String trimmedLine = line.trim();
        if (trimmedLine.isEmpty()) {
            return Optional.empty();
        }

        String[] fields = trimmedLine.split("\\|");
        validator.validateNumberOfFields(fields, EXPECTED_FIELD_COUNT);

        return Optional.of(new InitialFileLine(
                validator.validateAndGetUuid(fields, UUID_INDEX),
                fields[ID],
                fields[NAME],
                fields[LIKES],
                fields[TRANSPORT],
                validator.validateAndGetDouble(fields, AVG_SPEED),
                validator.validateAndGetDouble(fields, TOP_SPEED)));
    }
}
