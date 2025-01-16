package scot.chriswalker.elemental_concept.technical_assessment.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OutcomeFileLine(String name, String transport, @JsonProperty("top-speed") double topSpeed) {
}
