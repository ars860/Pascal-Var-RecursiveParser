import junit.framework.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

public class ParserTest extends Assert {
    private String proceedString(String testString) throws ParseExceprion {
        InputStream is = new ByteArrayInputStream(testString.getBytes());
        Parser parser = new Parser(is);
        Parser.Node parsed = parser.parse();
        return parsed.prettyPrint(1);
    }

    @Test
    public void simpleTest() throws ParseExceprion {
        String testString = "var aba:type;aba,baba,caba:type;  ";
        System.out.println(proceedString(testString));
    }

    @Test
    public void iDontKnowHowToWriteTestsParser() throws ParseExceprion {
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
        System.out.println(proceedString(testString));
    }

    @Test
    public void noVarTest() throws ParseExceprion {
        Assertions.assertThrows(ParseExceprion.class, () -> proceedString("no var here"));
        Assertions.assertThrows(ParseExceprion.class, () -> proceedString("vhaha"));
        Assertions.assertThrows(ParseExceprion.class, () -> proceedString("vahaha"));
        Assertions.assertThrows(ParseExceprion.class, () -> proceedString("varhaha"));
    }

    @Test
    public void badNames() {
        Assertions.assertThrows(ParseExceprion.class, () -> proceedString("var V@r1aBl3"));
        Assertions.assertThrows(ParseExceprion.class, () -> proceedString("var 1variable"));
        Assertions.assertThrows(ParseExceprion.class, () -> proceedString("var a : B@d_Typ3"));
    }

    @Test
    public void intruders() {
        Assertions.assertThrows(ParseExceprion.class, () -> proceedString("var a, b, c Intruder"));
        Assertions.assertThrows(ParseExceprion.class, () -> proceedString("var a,b : int Intruder"));
        Assertions.assertThrows(ParseExceprion.class, () -> proceedString("var a,b : int; ;"));
        Assertions.assertThrows(ParseExceprion.class, () -> proceedString("var a,,b : int; ;"));
        Assertions.assertThrows(ParseExceprion.class, () -> proceedString("var ,a,b : int; ;"));
        Assertions.assertThrows(ParseExceprion.class, () -> proceedString("var a,b : :int; ;"));
    }
}
