import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// START -> VAR DECLARATION DECLARATIONS
// DECLARATIONS -> DECLARATION DECLARATIONS
// DECLARATIONS -> e
// DECLARATION -> VARIABLE VARIABLES : TYPE ;
// VARIABLES -> , VARIABLE VARIABLES
// VARIABLES -> e
public class Parser {
    private enum nodeType {
        START, DECLARATIONS, DECLARATION, VARIABLES, VARIABLE, TYPE
    }

    public class Node {
        private List<Node> childs = new ArrayList<>();
        nodeType type;
        String value;

        public Node(nodeType type, Node... nodes) {
            this.type = type;
            childs.addAll(Arrays.asList(nodes));
        }

        public Node(nodeType type, String value, Node... nodes) {
            this.type = type;
            this.value = value;
            childs.addAll(Arrays.asList(nodes));
        }

        public String prettyPrint(int indent) {
            if (type == nodeType.VARIABLE || type == nodeType.TYPE) {
                return "(" + type + " : " + value + ")";
            }

            StringBuilder res = new StringBuilder(type.toString());
            for (Node child : childs) {
                if (child != null) {
                    res.append("\n");
                    for (int i = 0; i < indent; i++) {
                        res.append("*   ");
                    }
                    res.append(child.prettyPrint(indent + 1));
                    res.append(" ");
                }
            }
            res.deleteCharAt(res.length() - 1);
            return res.toString();
        }
    }

    private LexicalAnalyzator lexicalAnalyzator;
    private Token curToken;

    public Parser(InputStream is) throws ParseExceprion {
        lexicalAnalyzator = new LexicalAnalyzator(is);
        nextToken();
    }

    private void consume(Type t) throws ParseExceprion {
        if (curToken.getType() != t) {
            throw new ParseExceprion("Expected " + t + " at pos: " + lexicalAnalyzator.getPos());
        }
        nextToken();
    }

    private Token nextToken() throws ParseExceprion {
        curToken = lexicalAnalyzator.nextToken();
        return curToken;
    }

    public Node parse() throws ParseExceprion {
        return Start();
    }

    private Node Start() throws ParseExceprion {
        switch (curToken.getType()) {
            case VAR:
                nextToken();
                Node declaration = Declaration();
                Node declarations = Declarations();
                return new Node(nodeType.START, declaration, declarations);
            default:
                throw new ParseExceprion("expected var at pos: " + lexicalAnalyzator.getPos());
        }
    }

    private Node Declarations() throws ParseExceprion {
        switch (curToken.getType()) {
            case NAME:
                Node declaration = Declaration();
                Node declarations = Declarations();
                return new Node(nodeType.DECLARATIONS, declaration, declarations);
            case VOID:
                return null;
            default:
                throw new ParseExceprion("expected variable or eof at pos: " + lexicalAnalyzator.getPos());
        }
    }

    private Node Declaration() throws ParseExceprion {
        switch (curToken.getType()) {
            case NAME:
                Node variable = new Node(nodeType.VARIABLE, curToken.getValue());
                nextToken();
                Node variables = Variables();
                consume(Type.COLON);

                if (curToken.getType() != Type.NAME) {
                    throw new ParseExceprion("Expected type at pos: " + lexicalAnalyzator.getPos());
                }

                Node type = new Node(nodeType.TYPE, curToken.getValue());
                nextToken();
                consume(Type.SEMICOLON);
                return new Node(nodeType.DECLARATION, variable, variables, type);
            default:
                throw new ParseExceprion("Expected variable at pos: " + lexicalAnalyzator.getPos());
        }
    }

    private Node Variables() throws ParseExceprion {
        switch (curToken.getType()) {
            case COMMA:
                nextToken();

                if (curToken.getType() != Type.NAME) {
                    throw new ParseExceprion("Expected variable ar pos: " + lexicalAnalyzator.getPos());
                }

                Node variable = new Node(nodeType.VARIABLE, curToken.getValue());
                nextToken();
                Node variables = Variables();
                return new Node(nodeType.VARIABLES, variable, variables);
            case COLON:
                return null;
            default:
                throw new ParseExceprion("Expected comma or colon at pos: " + lexicalAnalyzator.getPos());
        }
    }
}