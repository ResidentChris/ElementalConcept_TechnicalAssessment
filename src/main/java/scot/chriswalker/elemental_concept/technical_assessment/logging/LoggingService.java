package scot.chriswalker.elemental_concept.technical_assessment.logging;

import org.springframework.stereotype.Service;
import scot.chriswalker.elemental_concept.technical_assessment.logging.dao.LoggingRepository;
import scot.chriswalker.elemental_concept.technical_assessment.logging.dto.LogInfo;

import java.util.List;

@Service
public class LoggingService {

    private final LoggingRepository loggingRepository;

    public LoggingService(LoggingRepository loggingRepository) {
        this.loggingRepository = loggingRepository;
    }

    public void save(LogInfo logInfo) {
        loggingRepository.save(logInfo);
    }

    public List<LogInfo> findAll() {
        return loggingRepository.findAll();
    }

    public void deleteAll() {
        loggingRepository.deleteAll();
    }
}
