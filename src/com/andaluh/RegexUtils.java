package com.andaluh;

public class RegexUtils {

    String  matchWholeWordFromSubexpressionAtEnd(String exp)
    {
        return matchWholeWord("(\\\\p\\{L\\}*)" + exp);
    }

    String matchWholeWordFromSubexpression(String exp)
    {
        return matchWholeWord( "(\\\\p\\{L\\}*)" + exp + "(\\\\p\\{L\\}*)");
    }

    String matchWholeWord(String exp)
    {
        return "(?=|$|[^\\\\p\\{L\\}])" + exp + "(?=^|$|[^\\\\p\\{L\\}])";
    }

}
