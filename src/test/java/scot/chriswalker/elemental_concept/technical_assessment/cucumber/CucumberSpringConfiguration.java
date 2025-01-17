package scot.chriswalker.elemental_concept.technical_assessment.cucumber;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

@CucumberContextConfiguration
@SpringBootTest
@AutoConfigureMockMvc
@EnableWireMock(
        @ConfigureWireMock(
                name = "localhost",
                port = 8080))
public class CucumberSpringConfiguration {
}
