package scot.chriswalker.elemental_concept.technical_assessment.validator;

import java.util.UUID;

public interface InitialLineValidator {

    void validateNumberOfFields(String[] fields, int expectedFieldCount);

    UUID validateAndGetUuid(String[] fields, int index);

    double validateAndGetDouble(String[] fields, int index);
}
