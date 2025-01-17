package scot.chriswalker.elemental_concept.technical_assessment.exception;

public class IncorrectNumberOfFieldsInLineException extends RuntimeException {

    private final int actualFieldsCount;

    public IncorrectNumberOfFieldsInLineException(int actualFieldsCount) {
        this.actualFieldsCount = actualFieldsCount;
    }

    public int getActualFieldsCount() {
        return actualFieldsCount;
    }
}
