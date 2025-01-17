package scot.chriswalker.elemental_concept.technical_assessment.parser;

import org.springframework.stereotype.Service;
import scot.chriswalker.elemental_concept.technical_assessment.exception.IncorrectNumberOfFieldsInLineException;
import scot.chriswalker.elemental_concept.technical_assessment.exception.InitialFileReadException;
import scot.chriswalker.elemental_concept.technical_assessment.model.InitialFileLine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    public Optional<InitialFileLine> parseLine(String line) {
        if (line.trim().isEmpty()) {
            return Optional.empty();
        }
        String[] split = line.trim().split("\\|");
        if (split.length != EXPECTED_FIELD_COUNT) {
            throw new IncorrectNumberOfFieldsInLineException(split.length);
        }
        return Optional.of(new InitialFileLine(
                UUID.fromString(split[UUID_INDEX]),
                split[ID],
                split[NAME],
                split[LIKES],
                split[TRANSPORT],
                Double.parseDouble(split[AVG_SPEED]),
                Double.parseDouble(split[TOP_SPEED])));
    }
}
