package com.andaluh;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.*;

public class Andaluh {

    private static final Pattern TO_IGNORE_RE = Pattern.compile(
            "(?:[h|H][t|T][t|T][p|P][s|S]?://)?(?:www\\.)?(?:[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z|A-Z]{2,6})[\\/?]?[-a-zA-Z0-9@:%._\\+~#=&]{0,256}"
                    + "|(?:@\\w+\\b)"
                    + "|(?:#\\w+\\b)"
                    + "|(?=\\b[MCDXLVI]{1,8}\\b)M{0,4}(?:CM|CD|D?C{0,3})(?:XC|XL|L?X{0,3})(?:IX|IV|V?I{0,3})",
            Pattern.UNICODE_CASE | Pattern.UNICODE_CHARACTER_CLASS
    );

    public static String transliterate(String text) {
        return TransliterateString(text, AndaluhRules.VAF, AndaluhRules.VVF, false);
    }

    public static String transliterate(String[] text) {
        String[] textOut = new String[text.length];
        for (int i = 0; i < text.length; i++) {
            textOut[i] = TransliterateString(text[i], AndaluhRules.VAF, AndaluhRules.VVF, false);
        }

        return JoinStrings(textOut);
    }

    public static String TransliterateString(String palabra) {
        return TransliterateString(palabra, AndaluhRules.VAF, AndaluhRules.VVF, false);
    }

    public static String TransliterateString(String palabra, String vaf, String vvf, boolean debug) {
        palabra = AndaluhRules.h_rules(palabra);
        if(debug) System.out.println("h_rules: " + palabra);
        palabra = AndaluhRules.x_rules(palabra, vaf);
        if(debug) System.out.println("x_rules: " + palabra);
        palabra = AndaluhRules.ch_rules(palabra);
        if(debug) System.out.println("ch_rules: " + palabra);
        palabra = AndaluhRules.gj_rules(palabra, vvf);
        if(debug) System.out.println("gj_rules: " + palabra);
        palabra = AndaluhRules.v_rules(palabra);
        if(debug) System.out.println("v_rules: " + palabra);
        palabra = AndaluhRules.ll_rules(palabra);
        if(debug) System.out.println("ll_rules: " + palabra);
        palabra = AndaluhRules.l_rules(palabra);
        if(debug) System.out.println("l_rules: " + palabra);
        palabra = AndaluhRules.psico_pseudo_rules(palabra);
        if(debug) System.out.println("psico_pseudo_rules: " + palabra);
        palabra = AndaluhRules.vaf_rules(palabra, vaf);
        if(debug) System.out.println("vaf_rules: " + palabra);
        palabra = AndaluhRules.word_ending_rules(palabra);
        if(debug) System.out.println("word_ending_rules: " + palabra);
        palabra = AndaluhRules.digraph_rules(palabra);
        if(debug) System.out.println("digraph_rules: " + palabra);
        palabra = AndaluhRules.exception_rules(palabra);
        if(debug) System.out.println("exception_rules: " + palabra);
        palabra = AndaluhRules.word_interaction_rules(palabra);
        if(debug) System.out.println("word_interaction_rules: " + palabra);
        return palabra;
    }

    public static String transliterate(String[] text, boolean[] ignores) {
        String[] textOut = new String[text.length];
        for (int i = 0; i < text.length; i++) {
            textOut[i] = ignores[i] ? text[i] : TransliterateString(text[i], AndaluhRules.VAF, AndaluhRules.VVF, false);
        }

        return JoinStrings(textOut);
    }

    public static String JoinStrings(String[] text) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String strText : text) {
            stringBuilder.append(strText);
        }
        return stringBuilder.toString();
    }


    public static String epa(String textoEspanyol, char charCedilla, char charJejeo, boolean escapeLinks, boolean debug) {

        if (textoEspanyol == null || textoEspanyol.isEmpty() || textoEspanyol.isBlank()) return "";

        byte[] byteArrayAux = textoEspanyol.getBytes();
        String textoEspanyolUTF8 = new String(byteArrayAux, UTF_8);

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

        String vaf = String.valueOf(charCedilla);
        String vvf = String.valueOf(charJejeo);

        if (escapeLinks) {
            return transliterateWithEscapeLinks(textoEspanyolUTF8, vaf, vvf, debug);
        }
        return TransliterateString(textoEspanyolUTF8, vaf, vvf, debug);

    }

    private static String transliterateWithEscapeLinks(String text, String vaf, String vvf, boolean debug) {
        Matcher matcher = TO_IGNORE_RE.matcher(text);
        List<String> tokens = new ArrayList<>();
        List<String> ignores = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        Random random = new Random();
        Set<String> used = new HashSet<>();

        while (matcher.find()) {
            String token = generateUniqueToken(text, used, random);
            tokens.add(token);
            ignores.add(matcher.group(0));
            matcher.appendReplacement(sb, Matcher.quoteReplacement(token));
        }
        matcher.appendTail(sb);

        if (tokens.isEmpty()) {
            return TransliterateString(text, vaf, vvf, debug);
        }

        String transliterated = TransliterateString(sb.toString(), vaf, vvf, debug);
        for (int i = 0; i < tokens.size(); i++) {
            transliterated = transliterated.replace(tokens.get(i), ignores.get(i));
        }
        return transliterated;
    }

    private static String generateUniqueToken(String text, Set<String> used, Random random) {
        String token;
        do {
            int number = random.nextInt(999_999_999) + 1;
            token = Integer.toString(number);
        } while (text.contains(token) || used.contains(token));
        used.add(token);
        return token;
    }


    public static String epa(String textoEspanyol, char charCedilla, char charJejeo, boolean escapeLinks) {
        return epa(textoEspanyol, charCedilla, charJejeo, escapeLinks, false);
    }

    public static String epa(String textoEspanyol, char charCedilla, char charJejeo) {
        return epa(textoEspanyol, charCedilla, charJejeo, false, false);
    }

    public static String epa(String textoEspanyol) {
        return epa(textoEspanyol, 'ç', 'h', false, false);
    }

}
