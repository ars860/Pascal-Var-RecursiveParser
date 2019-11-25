public class ParseExceprion extends Exception {
    public ParseExceprion(int pos, Throwable cause) {
        super("parse error at " + pos, cause);
    }

    public ParseExceprion(String message, int pos, Throwable cause) {
        super(message + " at " + pos, cause);
    }


    public ParseExceprion(String message, int pos) {
        super(message + " at pos: " + pos);
    }


    public ParseExceprion(String message) {
        super(message);
    }
}
