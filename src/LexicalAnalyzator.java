import java.io.IOException;
import java.io.InputStream;

public class LexicalAnalyzator {
    private InputStream is;
    private int pos = 0;
    private char curChar;

    int getPos(){
        return pos;
    }

    public LexicalAnalyzator(InputStream is) throws ParseExceprion {
        this.is = is;
        pos = 0;
        nextChar();
    }

    private char nextChar() throws ParseExceprion {
        pos++;
        try {
            curChar = (char) is.read();
        } catch (IOException e) {
            throw new ParseExceprion(pos, e);
        }
        return curChar;
    }

    public Token nextToken() throws ParseExceprion {
        skipWhitespaces();

        if(curChar == (char)-1){
            return new Token(Type.VOID);
        }

        if (isDelimiter(curChar)) {
            switch (curChar) {
                case ',':
                    nextChar();
                    return new Token(Type.COMMA);
                case ':':
                    nextChar();
                    return new Token(Type.COLON);
                case ';':
                    nextChar();
                    return new Token(Type.SEMICOLON);
            }
        }

        StringBuilder res = new StringBuilder();

        if(!Character.isLetter(curChar)){
            throw new ParseExceprion("Name starts from non letter at pos: " + pos);
        }

        while (Character.isLetter(curChar) || Character.isDigit(curChar)) {
            res.append(curChar);
            nextChar();
        }
        String name = res.toString();
        if (name.equals("var")) {
            return new Token(Type.VAR);
        }
        return new Token(Type.NAME, res.toString());
    }

    private boolean isWhitespace(char ch) {
        return Character.isWhitespace(ch);
    }

    private boolean isDelimiter(char ch) {
        return isWhitespace(ch) || ch == ',' || ch == ';' || ch == ':' || ch == (char)-1;
    }

    private void skipWhitespaces() throws ParseExceprion {
        while (isWhitespace(curChar)) {
            nextChar();
        }
    }
}
