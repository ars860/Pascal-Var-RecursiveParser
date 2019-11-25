public class Token {
    private Type type;
    private String value;

    public Token(Type type) {
        this.type = type;
    }

    public Token(Type type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return type + (value != null ? " " + value : "");
    }

    public Type getType() {
        return type;
    }

    public String getValue(){
        return value;
    }
}