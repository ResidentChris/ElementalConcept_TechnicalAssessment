package scot.chriswalker.elemental_concept.technical_assessment.logging.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import scot.chriswalker.elemental_concept.technical_assessment.logging.dto.LogInfo;

public interface LoggingRepository extends JpaRepository<LogInfo, Long> {
}
