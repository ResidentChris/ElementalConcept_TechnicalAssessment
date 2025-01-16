package scot.chriswalker.elemental_concept.technical_assessment.service;

import org.springframework.stereotype.Service;
import scot.chriswalker.elemental_concept.technical_assessment.model.InitialFileLine;
import scot.chriswalker.elemental_concept.technical_assessment.model.OutcomeFileLine;
import scot.chriswalker.elemental_concept.technical_assessment.parser.InitialFileParser;

import java.io.InputStream;
import java.util.List;

@Service
public class FileConversionOrchestrationService {

    private final InitialFileParser initialFileParser;

    public FileConversionOrchestrationService(InitialFileParser initialFileParser) {
        this.initialFileParser = initialFileParser;
    }

    public List<OutcomeFileLine> convertInputStreamToOutcomeFile(InputStream inputStream) {
        var initialFile = initialFileParser.parseFile(inputStream);
        var outcomeFile = convertToOutcomeFile(initialFile);

        return outcomeFile;
    }

    private List<OutcomeFileLine> convertToOutcomeFile(List<InitialFileLine> initialFile) {
        return initialFile.stream().map(this::convertToOutcomeLine).toList();
    }

    private OutcomeFileLine convertToOutcomeLine(InitialFileLine initialFileLine) {
        return new OutcomeFileLine(
                initialFileLine.name(),
                initialFileLine.transport(),
                initialFileLine.topSpeed());
    }
}
