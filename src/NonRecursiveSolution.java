//import java.io.IOException;
//import java.io.InputStream;
//
//public class NonRecursiveSolution {
//    private InputStream is;
//    private int pos = 0;
//    private char curChar;
//    private Type curType;
//
//    public enum Type {
//        VAR, TYPE, VARIABLE, COMMA, COLON, SEMICOLON, VOID
//    }
//
//    public NonRecursiveSolution(InputStream is) throws ParseExceprion {
//        this.is = is;
//        pos = 0;
//        nextChar();
//    }
//
//    private char nextChar() throws ParseExceprion {
//        pos++;
//        try {
//            curChar = (char) is.read();
//        } catch (IOException e) {
//            throw new ParseExceprion(pos, e);
//        }
//        return curChar;
//    }
//
//    public Token nextToken() throws ParseExceprion {
//        skipWhitespaces();
//        if (curType == null) {
//            return parseVar();
//        }
//        switch (curType) {
//            case VAR:
//                return parseVariable();
//            case COMMA:
//            case SEMICOLON:
//            case VOID:
//                if (curChar == (char) (-1)) {
//                    curType = Type.VOID;
//                    return new Token(Type.VOID);
//                }
//                return parseVariable();
//            case VARIABLE:
//                return parseCommaOrColon();
//            case TYPE:
//                return parseSemicolon();
//            case COLON:
//                return parseType();
//            default:
//                throw new ParseExceprion("Never happen");
//        }
//    }
//
//    private boolean isWhitespace(char ch) {
//        return Character.isWhitespace(ch);
//    }
//
//    private void skipWhitespaces() throws ParseExceprion {
//        while (isWhitespace(curChar)) {
//            nextChar();
//        }
//    }
//
//    private Token parseVar() throws ParseExceprion {
//        char v = curChar;
//        if (v != 'v') {
//            throw new ParseExceprion("Declaration must starts from var", pos);
//        }
//        char a = nextChar();
//        if (a != 'a') {
//            throw new ParseExceprion("Declaration must starts from var", pos);
//        }
//        char r = nextChar();
//        if (r != 'r') {
//            throw new ParseExceprion("Declaration must starts from var", pos);
//        }
//        char blank = nextChar();
//        if (!Character.isWhitespace(blank)) {
//            throw new ParseExceprion("Declaration must starts from var", pos);
//        }
//        curType = Type.VAR;
//        return new Token(Type.VAR);
//    }
//
//    private String parseName() throws ParseExceprion {
//        StringBuilder res = new StringBuilder();
//        if (!Character.isLetter(curChar)) {
//            throw new ParseExceprion("Variable name starts from not letter", pos);
//        }
//
//        while (Character.isLetter(curChar) || Character.isDigit(curChar)) {
//            res.append(curChar);
//            nextChar();
//        }
//        return res.toString();
//    }
//
//    private Token parseVariable() throws ParseExceprion {
//        curType = Type.VARIABLE;
//        return new Token(Type.VARIABLE, parseName());
//    }
//
//    private Token parseCommaOrColon() throws ParseExceprion {
//        switch (curChar) {
//            case ':':
//                nextChar();
//                curType = Type.COLON;
//                return new Token(Type.COLON);
//            case ',':
//                nextChar();
//                curType = Type.COMMA;
//                return new Token(Type.COMMA);
//            default:
//                throw new ParseExceprion("Expected comma or colon", pos);
//        }
//    }
//
//    private Token parseSemicolon() throws ParseExceprion {
//        if (curChar != ';') {
//            throw new ParseExceprion("Excepted semicolon", pos);
//        }
//        nextChar();
//        curType = Type.SEMICOLON;
//        return new Token(Type.SEMICOLON);
//    }
//
//    private Token parseType() throws ParseExceprion {
//        curType = Type.TYPE;
//        return new Token(Type.TYPE, parseName());
//    }
//}
