package scot.chriswalker.elemental_concept.technical_assessment.validator;

import org.springframework.stereotype.Service;
import scot.chriswalker.elemental_concept.technical_assessment.exception.IncorrectNumberOfFieldsInLineException;
import scot.chriswalker.elemental_concept.technical_assessment.exception.UnexpectedTypeException;

import java.util.UUID;

@Service
public class DefaultInitialLineValidator implements InitialLineValidator {

    @Override
    public void validateNumberOfFields(String[] fields, int expectedFieldCount) {
        if (fields.length != expectedFieldCount) {
            throw new IncorrectNumberOfFieldsInLineException(fields.length);
        }
    }

    @Override
    public UUID validateAndGetUuid(String[] fields, int index) {
        try {
            return UUID.fromString(fields[index]);
        } catch (IllegalArgumentException e) {
            throw new UnexpectedTypeException("UUID", index);
        }
    }

    @Override
    public double validateAndGetDouble(String[] fields, int index) {
        try {
            return Double.parseDouble(fields[index]);
        } catch (IllegalArgumentException e) {
            throw new UnexpectedTypeException("double", index);
        }
    }
}
