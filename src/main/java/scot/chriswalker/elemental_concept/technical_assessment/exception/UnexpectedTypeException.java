package scot.chriswalker.elemental_concept.technical_assessment.exception;

public class UnexpectedTypeException extends RuntimeException {

    private final String expectedType;
    private final int expectedIndex;

    public UnexpectedTypeException(String expectedType, int expectedIndex) {
        this.expectedType = expectedType;
        this.expectedIndex = expectedIndex;
    }

    public String getExpectedType() {
        return expectedType;
    }

    public int getExpectedIndex() {
        return expectedIndex;
    }
}
