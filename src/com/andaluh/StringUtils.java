package com.andaluh;

public class StringUtils {

    static Boolean IsUpperCase(String str) {
        return str.toUpperCase().equals(str);
    }

    static Boolean IsLowerCase(String str) {
        return str.toLowerCase().equals(str);
    }

    static Boolean IsCapitalized(String word) {
        return IsUpperCase(String.valueOf(word.charAt(0))) && IsLowerCase(word.substring(1));
    }

    /* //TODO: hacer un IsWordAllCaps para las sustituciones en palabras todo mayus
        static Boolean IsAllCapitalized(String word)
        {
            String word_capitalized = new String(word);
            word_capitalized = word_capitalized.toUpperCase();
            return word.contentEquals(word_capitalized);
        }
    */
    static String Capitalize(String word) {
        return String.valueOf(word.charAt(0)).toUpperCase() + word.substring(1).toLowerCase();
    }

    // TODO: This can be improved to perform replacement in a per character basis
// NOTE: It assumes replacement_word to be already lowercase
    String KeepCase(String word, String replacement_word) {
        if (IsLowerCase(word)) return replacement_word;
        if (IsUpperCase(word)) return replacement_word.toUpperCase();
        if (IsCapitalized(word)) return Capitalize(replacement_word);
        return replacement_word;
    }

    // Useful to calculate the circumflex equivalents.
    public static final String VOWELS_ALL_NOTILDE = "aeiouâêîôûAEIOUÂÊÎÔÛ";
    public static final String VOWELS_ALL_TILDE = "áéíóúâêîôûÁÉÍÓÚÂÊÎÔÛ";

    char GetVowelTilde(char vowel) {
        final int i = VOWELS_ALL_NOTILDE.indexOf(vowel);
        // If no tilde, replace with circumflex
        if (i != -1) return VOWELS_ALL_TILDE.charAt(i);

        if (VOWELS_ALL_TILDE.contains(String.valueOf(vowel))) return vowel;

        System.out.print("Not a vowel ");
        System.out.println(vowel);

        return 0;
    }

    char GetVowelCircumflex(char vowel) {

        final int i = VOWELS_ALL_NOTILDE.indexOf(vowel);

        if (i != -1) return VOWELS_ALL_NOTILDE.charAt(i + 5);

        if (VOWELS_ALL_TILDE.contains(String.valueOf(vowel))) return vowel;

        System.out.println("Not supported");

        return 0;
    }

}
