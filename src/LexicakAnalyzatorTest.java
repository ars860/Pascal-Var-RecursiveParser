import junit.framework.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class LexicakAnalyzatorTest extends Assert {
    private String proceedString(String testString) throws ParseExceprion {
        InputStream is = new ByteArrayInputStream(testString.getBytes());
        LexicalAnalyzator nonRecursiveSolution = new LexicalAnalyzator(is);
        Token curr = nonRecursiveSolution.nextToken();
        StringBuilder res = new StringBuilder();
        while (curr.getType() != Type.VOID) {
//            System.out.println(curr.toString());
            res.append(curr.toString()).append(" ");
            curr = nonRecursiveSolution.nextToken();
        }
        return res.deleteCharAt(res.length() - 1).toString();
    }

    @Test
    public void simpleTest() throws ParseExceprion {
        String testString = "            var aba,         baba,       caba                                                 :type;           ";

        assertEquals("VAR NAME aba COMMA NAME baba COMMA NAME caba COLON NAME type SEMICOLON", proceedString(testString));
    }

    @Test
    public void iDontKnowHowToWriteTestsLexer() throws ParseExceprion {
        String testString = "                 \n" +
                "\n" +
                "var\n" +
                "a\n" +
                ",\n" +
                "b\n" +
                ":                                                             t1\n" +
                "\n" +
                ";\n" +
                "                             aba,                             baba,                     caba    :    type2;";
        assertEquals("VAR NAME a COMMA NAME b COLON NAME t1 SEMICOLON NAME aba COMMA NAME baba COMMA NAME caba COLON NAME type2 SEMICOLON", proceedString(testString));
    }
}
