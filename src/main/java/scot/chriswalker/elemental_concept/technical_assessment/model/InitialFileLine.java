package scot.chriswalker.elemental_concept.technical_assessment.model;

import java.util.UUID;

public record InitialFileLine(UUID uuid,
                              String id,
                              String name,
                              String likes,
                              String transport,
                              double avgSpeed,
                              double topSpeed) {
}
