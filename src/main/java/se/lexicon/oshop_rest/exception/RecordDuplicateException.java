package se.lexicon.oshop_rest.exception;

public class RecordDuplicateException extends Exception {
    private String paramName;
    private String description;

    public RecordDuplicateException(String message) {
        super(message);
    }

    public  RecordDuplicateException(String message, String paramName, String description){
        super(message);
        this.paramName = paramName;
        this.description = description;
    }

    public String getParamName() {
        return paramName;
    }

    public String getDescription() {
        return description;
    }
}
