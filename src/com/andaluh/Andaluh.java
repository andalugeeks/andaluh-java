package com.andaluh;
import javafx.util.Pair;

import java.util.Arrays;

import static java.nio.charset.StandardCharsets.*;

public class Andaluh {
    public static String transliterate(String[] text) {
        /*
        for rule in rules:
        if rule in[ x_rules, vaf_rules]:
        text = rule(text, vaf)
        elif rule ==gj_rules:
        text = rule(text, vvf)
        else:
        text = rule(text)
        if debug:
        print rule.func_name + ' => ' + text
        */
        return JoinStrings(text);
    }

    public static String transliterate(String[] text, boolean[] ignores) {

        return JoinStrings(text);
    }

    public static String JoinStrings(String[] text) {
        StringBuilder stringBuilder = new StringBuilder();
        for(String strText : text) {
            stringBuilder.append(strText);
        }
        return stringBuilder.toString();
    }


    public static String epa(String textoEspanyol, char charCedilla, char charJejeo, boolean escapeLinks, boolean debug)
    {

        if(textoEspanyol == null || textoEspanyol.isEmpty() || textoEspanyol.isBlank()) return "";

        byte[] byteArrayAux = textoEspanyol.getBytes();
        String textoEspanyolUTF8 = new String(byteArrayAux, UTF_8);
        String[] textoEspanyolUTF8_splitted = textoEspanyolUTF8.split("(?<=-)|(?<= )|(?<=,)|(?<=;)|(?<=:)|(?<=\\.)");

        /*

    rules = [
        h_rules,
        x_rules,
        ch_rules,
        gj_rules,
        v_rules,
        ll_rules,
        l_rules,
        psico_pseudo_rules,
        vaf_rules,
        word_ending_rules,
        digraph_rules,
        exception_rules,
        word_interaction_rules
    ]
        */

        String[] tags;
        if (escapeLinks)
        {
            boolean[] ignores = GetIgnores(textoEspanyolUTF8_splitted);
            String text_and = transliterate(textoEspanyolUTF8_splitted, ignores);

            return text_and;
        }
        else
        {
            return transliterate(textoEspanyolUTF8_splitted);
        }

    }

    public static boolean[] GetIgnores(String[] text)
    {
        boolean[] ignores = new boolean[text.length];
        Arrays.fill(ignores, false);
        return ignores;
    }


    public static String epa(String textoEspanyol, char charCedilla, char charJejeo, boolean escapeLinks)
    {
        return epa(textoEspanyol, charCedilla, charJejeo, escapeLinks, false);
    }

    public static String epa(String textoEspanyol, char charCedilla, char charJejeo)
    {
        return epa(textoEspanyol, charCedilla, charJejeo, false, false);
    }
    
}
