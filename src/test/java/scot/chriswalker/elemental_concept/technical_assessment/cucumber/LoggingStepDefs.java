package scot.chriswalker.elemental_concept.technical_assessment.cucumber;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.en.And;
import scot.chriswalker.elemental_concept.technical_assessment.logging.LoggingService;
import scot.chriswalker.elemental_concept.technical_assessment.logging.dto.LogInfo;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LoggingStepDefs {

    private LoggingService loggingService;

    public LoggingStepDefs(LoggingService loggingService) {
        this.loggingService = loggingService;
    }

    @After
    public void tearDown() {
        loggingService.deleteAll();
    }

    @And("no data is logged to the database.")
    public void noDataIsLoggedToTheDatabase() {
        List<LogInfo> logs = loggingService.findAll();
        assertThat(logs).isEmpty();
    }

    @And("the following data is logged to the database:")
    public void theFollowingDataIsLoggedToTheDatabase(DataTable dataTable) {
        List<LogInfo> logs = loggingService.findAll();
        LogInfo expected = transform(dataTable);
        assertThat(logs)
                .hasSize(1)
                .last()
                .usingRecursiveComparison()
                .ignoringFields("requestId", "requestTimestamp", "timeLapsed")
                .isEqualTo(expected);
    }

    public LogInfo transform(DataTable dataTable) {
        return dataTable.asMaps().stream().map(m -> new LogInfo(
                null,
                m.get("requestUri"),
                Long.parseLong(m.get("requestTimestamp")),
                Integer.parseInt(m.get("httpResponseCode")),
                m.get("requestIpAddress"),
                m.get("requestCountryCode"),
                m.get("requestIpProvider"),
                Long.parseLong(m.get("timeLapsed"))
        )).findFirst().get();
    }
}
