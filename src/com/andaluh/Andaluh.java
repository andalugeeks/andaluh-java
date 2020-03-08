package com.andaluh;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.List;

import com.andaluh.AndaluhRules;

import static java.nio.charset.StandardCharsets.*;

public class Andaluh {

    public static String transliterate(String[] text)
    {
        String[] textOut = new String[text.length];
        for (int i = 0; i < text.length; i++)
        {
            textOut[i] = TransliterateSingleWord(text[i]);
        }

        return JoinStrings(textOut);
    }

    public static String TransliterateSingleWord(String palabra)
    {
        palabra = AndaluhRules.h_rules(palabra);
        palabra = AndaluhRules.x_rules(palabra);
        palabra = AndaluhRules.ch_rules(palabra);
        palabra = AndaluhRules.gj_rules(palabra);
        palabra = AndaluhRules.v_rules(palabra);
        palabra = AndaluhRules.ll_rules(palabra);
        palabra = AndaluhRules.l_rules(palabra);
        palabra = AndaluhRules.psico_pseudo_rules(palabra);
        palabra = AndaluhRules.vaf_rules(palabra);
        palabra = AndaluhRules.word_ending_rules(palabra);
        palabra = AndaluhRules.digraph_rules(palabra);
        palabra = AndaluhRules.exception_rules(palabra);
        palabra = AndaluhRules.word_interaction_rules(palabra);

        return palabra;
    }

    public static String transliterate(String[] text, boolean[] ignores)
    {
        String[] textOut = new String[text.length];
        for (int i = 0; i < text.length; i++)
        {
            textOut[i] = ignores[i] ? text[i] : TransliterateSingleWord(text[i]);
        }

        return JoinStrings(textOut);
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
        Arrays.fill(ignores, false); //TODO: iterate through all strings and set its boolean
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
