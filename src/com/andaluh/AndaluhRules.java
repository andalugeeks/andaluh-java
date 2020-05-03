package com.andaluh;

import javafx.util.Pair;

import java.util.*;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import com.andaluh.StringUtils;

public class AndaluhRules {

    // Digraphs producers. (vowel(const(const that triggers the general digraph rule
    public static final String[] DIGRAPHS = {
            "bb", "bc", "bç", "bÇ", "bd", "bf", "bg", "bh", "bm", "bn", "bp", "bq", "bt", "bx", "by", "cb", "cc",
            "cç", "cÇ", "cd", "cf", "cg", "ch", "cm", "cn", "cp", "cq", "ct", "cx", "cy",
            "db", "dc", "dç", "dÇ", "dd", "df", "dg", "dh", "dl", "dm", "dn", "dp", "dq", "dt", "dx", "dy",
            "fb", "fc", "fç", "fÇ", "fd", "ff", "fg", "fh", "fm", "fn", "fp", "fq", "ft", "fx", "fy",
            "gb", "gc", "gç", "gÇ", "gd", "gf", "gg", "gh", "gm", "gn", "gp", "gq", "gt", "gx", "gy",
            "jb", "jc", "jç", "jÇ", "jd", "jf", "jg", "jh", "jl", "jm", "jn", "jp", "jq", "jr", "jt", "jx", "jy",
            "lb", "lc", "lç", "lÇ", "ld", "lf", "lg", "lh", "ll", "lm", "ln", "lp", "lq", "lr", "lt", "lx", "ly",
            "mm", "mn",
            "nm", "nn",
            "pb", "pc", "pç", "pÇ", "pd", "pf", "pg", "ph", "pm", "pn", "pp", "pq", "pt", "px", "py",
            "rn",
            "sb", "sc", "sç", "sÇ", "sd", "sf", "sg", "sh", "sk", "sl", "sm", "sn", "sñ", "sp", "sq", "sr", "st", "sx", "sy",
            "tb", "tc", "tç", "tÇ", "td", "tf", "tg", "th", "tl", "tm", "tn", "tp", "tq", "tt", "tx", "ty",
            "xb", "xc", "xç", "xÇ", "xd", "xf", "xg", "xh", "xl", "xm", "xn", "xp", "xq", "xr", "xt", "xx", "xy",
            "zb", "zc", "zç", "zÇ", "zd", "zf", "zg", "zh", "zl", "zm", "zn", "zp", "zq", "zr", "zt", "zx", "zy"
    };
    private static final Pattern pattern_h_general = Pattern.compile("(?i)(?<!c)(h)([aáeéiíoóuú])");
    private static final Pattern pattern_h_hua = Pattern.compile("(?i)(?<!c)(h)(ua)");
    private static final Pattern pattern_h_hue = Pattern.compile("(?i)(?<!c)(h)(u)(e)");
    private static final Pattern pattern_x_starting = Pattern.compile("([xX])([aáeéiíoóuú])");
    private static final Pattern pattern_x = Pattern.compile("([aeiouáéíóú])(x)([aeiouáéíóú])");
    private static final Pattern pattern_ch = Pattern.compile("(?i)ch");
    private static final Pattern pattern_gj = Pattern.compile("(?i)(g(?=[eiéí])|j)([aeiouáéíóú])");
    private static final Pattern pattern_gue_gui = Pattern.compile("(?i)(g)u([eiéí])");
    private static final Pattern pattern_guue_guui = Pattern.compile("(?i)(g)(ü)([eiéí])");
    private static final Pattern pattern_guen = Pattern.compile("(?i)(b)(uen)");
    private static final Pattern pattern_guel_gues = Pattern.compile("(?i)([sa])?(?<!m)(b)(ue)([ls])");
    private static final Pattern pattern_v = Pattern.compile("(?i)v");
    private static final Pattern pattern_nv = Pattern.compile("(?i)nv");
    private static final Pattern pattern_ll = Pattern.compile("(?i)ll");
    private static final Pattern pattern_l = Pattern.compile("(?i)(l)([bcçgsdfghkmpqrtxz])");
    private static final Pattern pattern_psico_pseudo = Pattern.compile("(?i)p(sic|seud)");
    private static final Pattern pattern_vaf = Pattern.compile("(?i)(c(?=[eiéíêî])|z|s)([aeiouáéíóúÁÉÍÓÚâêîôûÂÊÎÔÛ])");
    private static final Pattern pattern_intervowel_d_end = Pattern.compile("(?i)([aiíÍ])(d)([oa])(s?)\\b");
    private static final Pattern pattern_eps_end = Pattern.compile("(?i)(e)(ps)");
    private static final Pattern pattern_d_end = Pattern.compile("(?i)([aeiouáéíóú])(d)\\b");
    private static final Pattern pattern_s_end = Pattern.compile("(?i)([aeiouáéíóú])(s)\\b");
    private static final Pattern pattern_const_end = Pattern.compile("(?i)([aeiouáâçéíóú])([bcfgjkprtxz]\\b)");
    private static final Pattern pattern_l_end = Pattern.compile("(?i)([aeiouáâçéíóú])(l\\b)");
    private static final Pattern pattern_digraph_special_1 = Pattern.compile("(?i)([aeiouáéíóú])([lr])s(t)");
    private static final Pattern pattern_digraph_special_2 = Pattern.compile("(?i)(tr|p)([ao])(?:ns|st)([bcçdfghjklmnpqstvwxyz])");
    private static final Pattern pattern_digraph_special_3 = Pattern.compile("(?i)([aeiouáéíóú])([bdnr])(s)([bcçdfghjklmnpqstvwxyz])");
    private static final Pattern pattern_digraph_special_4 = Pattern.compile("(?i)([aeiouáéíóú])[djrstxz](l)");
    private static final Pattern pattern_digraph_general = Pattern.compile("(?i)([aeiouáéíóú])(" + String.join("|", DIGRAPHS) + ")");
    private static final Pattern pattern_exception = Pattern.compile("(?i)ç?\\w+\\b");
    private static final Pattern pattern_word_interaction = Pattern.compile("(?i)(l\\b)(\\s)([bcçdfghjklmnñpqstvwxyz])");
    private static final Pattern pattern_vocal_tilde = Pattern.compile("(?i)á|é|í|ó|ú");

    // EPA character for Voiceless alveolar fricative /s/ https://en.wikipedia.org/wiki/Voiceless_alveolar_fricative
    public static final String VAF = "ç";
    public static final String VAF_mayus = "Ç";

    // EPA character for Voiceless velar fricative /x/ https://en.wikipedia.org/wiki/Voiceless_velar_fricative
    public static final String VVF = "h";
    public static final String VVF_mayus = "H";


    public static final Map<String, String> REPL_RULES = new HashMap<String, String>() {
        {
            put("a", "â");
            put("A", "Â");
            put("á", "â");
            put("Á", "Â");

            put("e", "ê");
            put("E", "Ê");
            put("é", "ê");
            put("É", "Ê");

            put("i", "î");
            put("I", "Î");
            put("í", "î");
            put("Í", "Î");

            put("o", "ô");
            put("O", "Ô");
            put("ó", "ô");
            put("Ó", "Ô");

            put("u", "û");
            put("U", "Û");
            put("ú", "û");
            put("Ú", "Û");
        }
    };

    public static final Map<String, String> STRESSED_RULES = new HashMap<String, String>() {
        {
            put("a", "á");
            put("A", "Á");
            put("á", "á");
            put("Á", "Á");

            put("e", "é");
            put("E", "É");
            put("é", "é");
            put("É", "É");

            put("i", "î");
            put("I", "Î");
            put("í", "î");
            put("Í", "Î");

            put("o", "ô");
            put("O", "Ô");
            put("ó", "ô");
            put("Ó", "Ô");

            put("u", "û");
            put("U", "Û");
            put("ú", "û");
            put("Ú", "Û");
        }
    };


    public static final Map<String, String> H_RULES_EXCEPT = new HashMap<String, String>() {
        {
            put("haz", "âh");
            put("hez", "êh");
            put("hoz", "ôh");
            put("oh", "ôh");
            put("yihad", "yihá");
            put("h", "h"); // Keep an isolated h as-is
        }
    };

    public static final Map<String, String> GJ_RULES_EXCEPT = new HashMap<String, String>() {{
        put("gin", "yin");
        put("jazz", "yâh");
        put("jet", "yêh");
    }};

    public static final Map<String, String> V_RULES_EXCEPT = new HashMap<String, String>() {{
        put("vis", "bî");
        put("ves", "bêh");
    }};

    public static final Map<String, String> LL_RULES_EXCEPT = new HashMap<String, String>() {{
        put("grill", "grîh");
    }};

    public static final Map<String, String> WORDEND_D_RULES_EXCEPT = new HashMap<String, String>() {{
        put("çed", "çêh");
    }};

    public static final Map<String, String> WORDEND_S_RULES_EXCEPT = new HashMap<String, String>() {{
        put("bies", "biêh");
        put("bis", "bîh");
        put("blues", "blû");
        put("bus", "bûh");
        put("dios", "diôh");
        put("dos", "dôh");
        put("gas", "gâh");
        put("gres", "grêh");
        put("gris", "grîh");
        put("luis", "luîh");
        put("mies", "miêh");
        put("mus", "mûh");
        put("os", "ô");
        put("pis", "pîh");
        put("plus", "plûh");
        put("pus", "pûh");
        put("ras", "râh");
        put("res", "rêh");
        put("tos", "tôh");
        put("tres", "trêh");
        put("tris", "trîh");
    }};

    public static final Map<String, String> WORDEND_CONST_RULES_EXCEPT = new HashMap<String, String>() {{
        put("al", "al");
        put("cual", "cuâ");
        put("del", "del");
        put("dél", "dél");
        put("el", "el");
        put("él", "èl");
        put("tal", "tal");
        put("bil", "bîl");
// TODO, uir = huir. Maybe better to add the exceptions on h_rules?
        put("por", "por");
        put("uir", "huîh");
// sic, tac
        put("çic", "çic");
        put("tac", "tac");
        put("yak", "yak");
        put("stop", "êttôh");
        put("bip", "bip");
    }};

    public static final Map<String, String> WORDEND_D_INTERVOWEL_RULES_EXCEPT = new HashMap<String, String>() {{
        // Ending with -ado
        put("fado", "fado");
        put("cado", "cado");
        put("nado", "nado");
        put("priado", "priado");
        // Ending with -ada
        put("fabada", "fabada");
        put("fabadas", "fabadas");
        put("fada", "fada");
        put("ada", "ada");
        put("lada", "lada");
        put("rada", "rada");
        // Ending with -adas
        put("adas", "adas");
        put("radas", "radas");
        put("nadas", "nadas");
        // Ending with -ido
        put("aikido", "aikido");
        put("bûççido", "bûççido");
        put("çido", "çido");
        put("cuido", "cuido");
        put("cupido", "cupido");
        put("descuido", "descuido");
        put("despido", "despido");
        put("eido", "eido");
        put("embido", "embido");
        put("fido", "fido");
        put("hido", "hido");
        put("ido", "ido");
        put("infido", "infido");
        put("laido", "laido");
        put("libido", "libido");
        put("nido", "nido");
        put("nucleido", "nucleido");
        put("çonido", "çonido");
        put("çuido", "çuido");
    }};

    public static final Map<String, String> ENDING_RULES_EXCEPTION = new HashMap<String, String>() {{
        // Exceptions to digraph rules with nm
        put("biêmmandao", "bienmandao");
        put("biêmmeçabe", "bienmeçabe");
        put("buêmmoço", "buenmoço");
        put("çiêmmiléçima", "çienmiléçima");
        put("çiêmmiléçimo", "çienmiléçimo");
        put("çiêmmilímetro", "çienmilímetro");
        put("çiêmmiyonéçima", "çienmiyonéçima");
        put("çiêmmiyonéçimo", "çienmiyonéçimo");
        put("çiêmmirmiyonéçima", "çienmirmiyonéçima");
        put("çiêmmirmiyonéçimo", "çienmirmiyonéçimo");
        // Exceptions to l rules
        put("marrotadôh", "mârrotadôh");
        put("marrotâh", "mârrotâh");
        put("mirrayâ", "mîrrayâ");
        // Exceptions to psico pseudo rules
        put("herôççiquiatría", "heroçiquiatría");
        put("herôççiquiátrico", "heroçiquiátrico");
        put("farmacôççiquiatría", "farmacoçiquiatría");
        put("metempçícoçî", "metemçícoçî");
        put("necróçico", "necróççico");
        put("pampçiquîmmo", "pamçiquîmmo");
        // Other exceptions
        put("antîççerôttármico", "antiçerôttármico");
        put("eclampçia", "eclampçia");
        put("pôttoperatorio", "pôççoperatorio");
        put("çáccrito", "çánccrito");
        put("manbîh", "mambîh");
        put("cômmelináçeo", "commelináçeo");
        put("dîmmneçia", "dînneçia");
        put("todo", "tó");
        put("todô", "tôh");
        put("toda", "toa");
        put("todâ", "toâ");
        // Other exceptions monosyllables
        put("as", "âh");
        put("clown", "claun");
        put("crack", "crâh");
        put("down", "daun");
        put("es", "êh");
        put("ex", "êh");
        put("ir", "îh");
        put("miss", "mîh");
        put("muy", "mu");
        put("ôff", "off");
        put("os", "ô");
        put("para", "pa");
        put("ring", "rin");
        put("rock", "rôh");
        put("spray", "êppray");
        put("sprint", "êpprín");
        put("wa", "gua");
    }};

    public static Boolean check_exception(Map<String, String> diccionario, String text)
    {
        return diccionario.containsKey(text.toLowerCase());
    }

    public static String h_hue_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        String gu_correct_capitalization = StringUtils.IsCapitalized(text.substring(match, match + 1)) ? "Gü" : "gü";
        return gu_correct_capitalization + text.substring(match + 2, match + 3);
    }

    public static String h_hua_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        String g_correct_capitalization = StringUtils.IsCapitalized(text.substring(match, match + 1)) ? "G" : "g";
        return g_correct_capitalization + text.substring(match + 1, match + 3);
    }

    public static String h_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        String undetermined_vocal = text.substring(match + 1, match + 2);
        if (text.charAt(match) == 'H' || text.charAt(match) == 'h') {
            return text.charAt(match) == 'H' ? undetermined_vocal.toUpperCase() : undetermined_vocal;
        } else return "";
    }
    public static String h_exceptions_replacer(MatchResult matchResult, String text)
    {
        String palabra = matchResult.group(0);
        if(check_exception(H_RULES_EXCEPT, palabra))
        {
            return H_RULES_EXCEPT.get(palabra);
        }
        else return palabra;
    }

    public static String h_rules(String text) {

        Matcher matcher_exception = pattern_exception.matcher(text);
        String exceptions_applied = matcher_exception.replaceAll(matchResult -> h_exceptions_replacer(matchResult, text));

        Matcher matcher_hua = pattern_h_hua.matcher(exceptions_applied);
        String hua_rules_applied = matcher_hua.replaceAll(matchResult -> h_hua_rules_replacer(matchResult, text));

        Matcher matcher_hue = pattern_h_hue.matcher(hua_rules_applied);
        String hue_rules_applied = matcher_hue.replaceAll(matchResult -> h_hue_rules_replacer(matchResult, hua_rules_applied));

        Matcher matcher_h_general = pattern_h_general.matcher(hue_rules_applied);
        return matcher_h_general.replaceAll(matchResult -> h_rules_replacer(matchResult, hue_rules_applied));
    }

    private static String x_starting_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        String VAF_correct_capitalization = StringUtils.IsCapitalized(text.substring(match, match + 1)) ? VAF_mayus : VAF;
        return text.substring(match, match) + VAF_correct_capitalization + text.substring(match + 1, match + 2);
    }

    public static String x_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        return text.substring(match, match + 1) + VAF + VAF + text.substring(match + 2, match + 3);
    }

    public static String x_rules(String text) {
        Matcher matcher_x = pattern_x.matcher(text);
        String x_rules_applied = matcher_x.replaceAll(matchResult -> x_rules_replacer(matchResult, text));

        Matcher matcher_starting_x = pattern_x_starting.matcher(x_rules_applied);
        return matcher_starting_x.replaceAll(matchResult -> x_starting_rules_replacer(matchResult, x_rules_applied));
    }

    public static String ch_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        String x_correct_capitalization = StringUtils.IsCapitalized(text.substring(match, match + 1)) ? "X" : "x";
        return text.substring(match, match) + x_correct_capitalization + text.substring(match + 2, match + 2);
    }

    public static String ch_rules(String text) {
        Matcher matcher_ch = pattern_ch.matcher(text);
        return matcher_ch.replaceAll(matchResult -> ch_rules_replacer(matchResult, text));
    }

    public static String gj_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        String x_correct_capitalization = StringUtils.IsCapitalized(text.substring(match, match + 1)) ? VVF_mayus : VVF;
        return x_correct_capitalization + text.substring(match + 1, match + 2);
    }

    public static String gue_gui_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        int matchEnd = matchResult.end();
        return text.substring(match, match + 1) + text.substring(match + 2, matchEnd);
    }

    public static String guue_guui_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        int matchEnd = matchResult.end();
        String u_correct_capitalization = StringUtils.IsCapitalized(text.substring(match + 1, match + 2)) ? "U" : "u";

        return text.substring(match, match + 1) + u_correct_capitalization + text.substring(match + 2, matchEnd);
    }

    public static String guen_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        int matchEnd = matchResult.end();
        String g_correct_capitalization = StringUtils.IsCapitalized(text.substring(match, match + 1)) ? "G" : "g";
        return g_correct_capitalization + text.substring(match + 1, matchEnd);
    }

    public static String guel_gues_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        int matchEnd = matchResult.end();
        String g_correct_capitalization = StringUtils.IsCapitalized(text.substring(match, match + 1)) ? "G" : "g";
        return g_correct_capitalization + text.substring(match + 1, matchEnd);
    }
    public static String exception_gj_rules_replacer(MatchResult matchResult, String text) {
        String palabra = matchResult.group(0);
        if(check_exception(GJ_RULES_EXCEPT, palabra))
        {
            return GJ_RULES_EXCEPT.get(palabra);
        }
        else
        {
            return palabra;
        }
    }
    public static String gj_rules(String text) {
        Matcher matcher_exception_gj = pattern_exception.matcher(text);
        String exception_gj_applied = matcher_exception_gj.replaceAll(matchResult -> exception_gj_rules_replacer(matchResult, text));

        Matcher matcher_gj = pattern_gj.matcher(exception_gj_applied);
        String gj_rules_applied = matcher_gj.replaceAll(matchResult -> gj_rules_replacer(matchResult, text));

        Matcher matcher_gue_gui = pattern_gue_gui.matcher(gj_rules_applied);
        String gue_gui_rules_applied = matcher_gue_gui.replaceAll(matchResult -> gue_gui_rules_replacer(matchResult, gj_rules_applied));

        Matcher matcher_guue_guui = pattern_guue_guui.matcher(gue_gui_rules_applied);
        String guue_guui_rules_applied = matcher_guue_guui.replaceAll(matchResult -> guue_guui_rules_replacer(matchResult, gue_gui_rules_applied));

        Matcher matcher_guen = pattern_guen.matcher(guue_guui_rules_applied);
        String guen_rules_applied = matcher_guen.replaceAll(matchResult -> guen_rules_replacer(matchResult, guue_guui_rules_applied));

        Matcher matcher_guel_gues = pattern_guel_gues.matcher(guen_rules_applied);
        return matcher_guel_gues.replaceAll(matchResult -> guel_gues_rules_replacer(matchResult, guen_rules_applied));
    }

    public static String nv_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        return text.substring(match, match) + "mb" + text.substring(match + 2, match + 2);
    }

    public static String v_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        String v_correct_capitalization = StringUtils.IsCapitalized(text.substring(match, match + 1)) ? "B" : "b";
        return text.substring(match, match) + v_correct_capitalization + text.substring(match + 2, match + 2);
    }

    public static String v_exception_replacer(MatchResult matchResult, String text) {
        String palabra = matchResult.group(0);
        if(check_exception(V_RULES_EXCEPT, palabra))
        {
            return V_RULES_EXCEPT.get(palabra);
        }
        else
        {
            return palabra;
        }
    }
    public static String v_rules(String text) {
        Matcher matcher_exception = pattern_exception.matcher(text);
        String v_exception_applied = matcher_exception.replaceAll(matchResult -> v_exception_replacer(matchResult,text));

        Matcher matcher_nv = pattern_nv.matcher(v_exception_applied);
        String nv_rules_applied = matcher_nv.replaceAll(matchResult -> nv_rules_replacer(matchResult, v_exception_applied));

        Matcher matcher_v = pattern_v.matcher(nv_rules_applied);
        return matcher_v.replaceAll(matchResult -> v_rules_replacer(matchResult, nv_rules_applied));
    }

    public static String ll_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        String ll_correct_capitalization = StringUtils.IsCapitalized(text.substring(match, match + 1)) ? "Y" : "y";
        return text.substring(match, match) + ll_correct_capitalization + text.substring(match + 2, match + 2);
    }

    public static String ll_exception_replacer(MatchResult matchResult, String text) {
        String palabra = matchResult.group(0);
        if(check_exception(LL_RULES_EXCEPT, palabra))
        {
            return LL_RULES_EXCEPT.get(palabra);
        }
        else
        {
            return palabra;
        }
    }

    public static String ll_rules(String text) {
        Matcher ll_exception = pattern_exception.matcher(text);
        String ll_exception_applied = ll_exception.replaceAll(matchResult -> ll_exception_replacer(matchResult,text));

        Matcher matcher_ll = pattern_ll.matcher(ll_exception_applied);
        return matcher_ll.replaceAll(matchResult -> ll_rules_replacer(matchResult, ll_exception_applied));
    }

    public static String l_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        String l_correct_capitalization = StringUtils.IsCapitalized(text.substring(match, match + 1)) ? "R" : "r";
        return text.substring(match, match) + l_correct_capitalization + text.substring(match + 1, match + 2);
    }

    public static String l_rules(String text) {
        Matcher matcher_l = pattern_l.matcher(text);
        return matcher_l.replaceAll(matchResult -> l_rules_replacer(matchResult, text));
    }

    public static String psico_pseudo_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        int matchEnd = matchResult.end();
        String undetermined_consonant = text.substring(match + 1, match + 2);
        if (text.charAt(match) == 'P' || text.charAt(match) == 'p') {
            String undetermined_correct_capitalization = text.charAt(match) == 'P' ? undetermined_consonant.toUpperCase() : undetermined_consonant;
            return undetermined_correct_capitalization + text.substring(match + 2, matchEnd);
        } else return "";
    }

    public static String psico_pseudo_rules(String text) {
        Matcher matcher_psico_pseudo = pattern_psico_pseudo.matcher(text);
        return matcher_psico_pseudo.replaceAll(matchResult -> psico_pseudo_rules_replacer(matchResult, text));
    }

    public static String vaf_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        String vaf_correct_capitalization = StringUtils.IsCapitalized(text.substring(match, match + 1)) ? VAF_mayus : VAF;
        return text.substring(match, match) + vaf_correct_capitalization + text.substring(match + 1, match + 2);
    }

    public static String vaf_rules(String text) {
        Matcher matcher_vaf = pattern_vaf.matcher(text);
        return matcher_vaf.replaceAll(matchResult -> vaf_rules_replacer(matchResult, text));
    }

    public static Boolean contain_vocal_tilde(String text) {
        return pattern_vocal_tilde.matcher(text).find();
    }


    public static int get_indice_principio_palabra(String text) {
        int indice = 0;
        int indiceMax = text.length();
        for (char c : StringUtils.caracteresNoPalabra) {
            int indiceCandidato = text.lastIndexOf(c);
            if (indiceCandidato < 0 || indiceCandidato > indiceMax) continue;
            indice = Integer.max(indiceCandidato, indice);
        }
        return indice;
    }

    public static String get_prefix(MatchResult matchResult, String text) {
        int match = matchResult.start();
        int matchEnd = matchResult.end();
        String prePalabra = text.substring(0, match);
        int indicePrincipioPalabra = get_indice_principio_palabra(prePalabra);
        return text.substring(indicePrincipioPalabra, match);
    }

    public static String intervowel_d_end_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        int matchEnd = matchResult.end();

        String prefix = get_prefix(matchResult, text);

        String suffix = text.substring(match, matchEnd);

        if (contain_vocal_tilde(prefix)) return suffix;

        String palabra = StringUtils.GetWholeWord(text, match);

        if(check_exception(WORDEND_D_INTERVOWEL_RULES_EXCEPT, palabra))
        {
            return suffix;
        }

        switch (suffix.toLowerCase()) {
            case "ada":
                return "á";
            case "adas":
                return "âh";
            case "ado":
                return "ao";
            case "ados":
                return "áôh";
            case "idos":
            case "ídos":
                return "íôh";
            case "ido":
            case "ído":
                return "ío";
            default:
                return suffix;
        }
    }

    public static String eps_end_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        int matchEnd = matchResult.end();

        String prefix = get_prefix(matchResult, text);

        String suffix = text.substring(match, matchEnd);

        if (!contain_vocal_tilde(prefix)) return suffix;
        return "ê";
    }

    public static String d_end_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        int matchEnd = matchResult.end();

        String prefix = get_prefix(matchResult, text);

        String suffix = text.substring(match, matchEnd);

        char vocalCandidata = suffix.charAt(0);
        if (contain_vocal_tilde(prefix)) {
            return apply_repl_rules(Character.toString(vocalCandidata));
        }

        char[] vocalesValidas = {'a', 'e', 'A', 'E', 'á', 'é', 'Á', 'É'};
        for (char vocalValida : vocalesValidas) {
            if (vocalValida == vocalCandidata) {
                return apply_stressed_rules(Character.toString(vocalCandidata));
            }
        }

        return apply_stressed_rules(Character.toString(vocalCandidata)) + "h";
    }

    private static String apply_repl_rules(String vocal) {
        return REPL_RULES.get(vocal);
    }

    private static String apply_stressed_rules(String vocal) {
        return STRESSED_RULES.get(vocal);
    }

    public static String s_end_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        int matchEnd = matchResult.end();

        String prefix = get_prefix(matchResult, text);

        String suffix = text.substring(match, matchEnd);

        if (!contain_vocal_tilde(Character.toString(suffix.charAt(0)))) {
            return apply_repl_rules(Character.toString(suffix.charAt(0)));
        }

        return apply_repl_rules(Character.toString(suffix.charAt(0))) + "h";
    }

    public static String const_end_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        int matchEnd = matchResult.end();

        String prefix = get_prefix(matchResult, text);

        String suffix = text.substring(match, matchEnd);
        if (contain_vocal_tilde(prefix)) {
            return apply_repl_rules(Character.toString(suffix.charAt(0)));
        }

        return apply_repl_rules(Character.toString(suffix.charAt(0))) + "h";
    }

    public static String d_end_exceptions_replacer(MatchResult matchResult, String text) {
        String palabra = matchResult.group(0);
        if(check_exception(WORDEND_D_RULES_EXCEPT, palabra))
        {
            return WORDEND_D_RULES_EXCEPT.get(palabra);
        }
        else
        {

            return palabra;
        }
    }

    public static String s_end_exceptions_replacer(MatchResult matchResult, String text) {
        String palabra = matchResult.group(0);
        if(check_exception(WORDEND_S_RULES_EXCEPT, palabra))
        {
            return WORDEND_S_RULES_EXCEPT.get(palabra);
        }
        else
        {

            return palabra;
        }
    }


        public static String word_ending_rules(String text) {
        Matcher matcher_intervowel_d_end = pattern_intervowel_d_end.matcher(text);
        String intervowel_d_end_rules_applied = matcher_intervowel_d_end.replaceAll(matchResult -> intervowel_d_end_rules_replacer(matchResult, text));

        Matcher matcher_eps_end = pattern_eps_end.matcher(intervowel_d_end_rules_applied);
        String eps_end_rules_applied = matcher_eps_end.replaceAll(matchResult -> eps_end_rules_replacer(matchResult, intervowel_d_end_rules_applied));

        Matcher matcher_d_end_exceptions = pattern_exception.matcher(eps_end_rules_applied);
        String d_end_exceptions_applied = matcher_d_end_exceptions.replaceAll(matchResult -> d_end_exceptions_replacer(matchResult, eps_end_rules_applied));

        Matcher matcher_d_end = pattern_d_end.matcher(d_end_exceptions_applied);
        String d_end_rules_applied = matcher_d_end.replaceAll(matchResult -> d_end_rules_replacer(matchResult, d_end_exceptions_applied));

        Matcher matcher_s_end_exceptions = pattern_exception.matcher(d_end_rules_applied);
        String s_end_exceptions_applied = matcher_s_end_exceptions.replaceAll(matchResult -> s_end_exceptions_replacer(matchResult, d_end_rules_applied));

        Matcher matcher_s_end = pattern_s_end.matcher(s_end_exceptions_applied);
        String s_end_rules_applied = matcher_s_end.replaceAll(matchResult -> s_end_rules_replacer(matchResult, s_end_exceptions_applied));

        Matcher matcher_const_end = pattern_const_end.matcher(s_end_rules_applied);
        return matcher_const_end.replaceAll(matchResult -> const_end_rules_replacer(matchResult, s_end_rules_applied));
    }

    public static String l_ending_rules(String text) {
        Matcher matcher_l_end = pattern_l_end.matcher(text);
        return matcher_l_end.replaceAll(matchResult -> const_end_rules_replacer(matchResult, text));
    }

    public static String digraph_special1_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        int matchEnd = matchResult.end();

        char l_or_r = text.charAt(match + 1);
        switch (l_or_r) {
            case 'l':
                return text.substring(match, match + 1) + "rtt";
            case 'r':
                return text.substring(match, match + 2) + "tt";
            default:
                return text.substring(match, matchEnd);
        }
    }

    public static String digraph_special2_rules_replacer(MatchResult matchResult, String text) {
        String init_char = matchResult.group(1);
        char vowel_char = matchResult.group(2).charAt(0);
        char cons_char = matchResult.group(0).charAt(matchResult.group(0).length() - 1);

        if (Character.toLowerCase(cons_char) == 'l')
            return init_char + apply_repl_rules(Character.toString(vowel_char)) + cons_char + "-" + cons_char;
        else
            return init_char + apply_repl_rules(Character.toString(vowel_char)) + cons_char + cons_char;
    }

    public static String digraph_special3_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        int matchEnd = matchResult.end();
        char vowel_char = text.charAt(match);
        char cons_char = text.charAt(match + 1);
        char s_char = text.charAt(match + 2);
        char digraph_char = text.charAt(matchEnd - 1);

        if (Character.toLowerCase(cons_char) == 'r' && Character.toLowerCase(s_char) == 's') {
            return Character.toString(vowel_char) + Character.toString(cons_char) + digraph_char + digraph_char;
        } else {
            return apply_repl_rules(Character.toString(vowel_char)) + digraph_char + digraph_char;
        }
    }

    public static String digraph_special4_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        int matchEnd = matchResult.end();
        char vowel_char = text.charAt(match);
        char digraph_char = text.charAt(matchEnd - 1);

        return apply_repl_rules(Character.toString(vowel_char)) + digraph_char + "-" + digraph_char;
    }

    public static String digraph_general_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        int matchEnd = matchResult.end();
        char vowel_char = text.charAt(match);
        char digraph_char = text.charAt(matchEnd - 1);

        return apply_repl_rules(Character.toString(vowel_char)) + digraph_char + digraph_char;
    }

    public static String digraph_rules(String text) {
        Matcher matcher_digraph_special1 = pattern_digraph_special_1.matcher(text);
        String digraph_special1_rules_applied = matcher_digraph_special1.replaceAll(matchResult -> digraph_special1_rules_replacer(matchResult, text));

        Matcher matcher_digraph_special2 = pattern_digraph_special_2.matcher(digraph_special1_rules_applied);
        String digraph_special2_rules_applied = matcher_digraph_special2.replaceAll(matchResult -> digraph_special2_rules_replacer(matchResult, digraph_special1_rules_applied));

        Matcher matcher_digraph_special3 = pattern_digraph_special_3.matcher(digraph_special2_rules_applied);
        String digraph_special3_rules_applied = matcher_digraph_special3.replaceAll(matchResult -> digraph_special3_rules_replacer(matchResult, digraph_special2_rules_applied));

        Matcher matcher_digraph_special4 = pattern_digraph_special_4.matcher(digraph_special3_rules_applied);
        String digraph_special4_rules_applied = matcher_digraph_special4.replaceAll(matchResult -> digraph_special4_rules_replacer(matchResult, digraph_special3_rules_applied));

        Matcher matcher_digraph_general = pattern_digraph_general.matcher(digraph_special4_rules_applied);
        return matcher_digraph_general.replaceAll(matchResult -> digraph_general_rules_replacer(matchResult, digraph_special4_rules_applied));
    }

    public static String exception_rules_replacer(MatchResult matchResult, String text) {
        String palabra = matchResult.group(0);
        if(check_exception(ENDING_RULES_EXCEPTION, palabra))
        {
            return ENDING_RULES_EXCEPTION.get(palabra);
        }
        else
        {
            return palabra;
        }

    }

    public static String exception_rules(String text) {
        Matcher matcher_exception = pattern_exception.matcher(text);
        return matcher_exception.replaceAll(matchResult -> exception_rules_replacer(matchResult, text));
    }

    public static String word_interaction_rules_replacer(MatchResult matchResult, String text) {
        int match = matchResult.start();
        int matchEnd = matchResult.end();
        return "r" + text.substring(match + 1, matchEnd);
    }

    public static String word_interaction_rules(String text) {
        Matcher matcher_word_interaction = pattern_word_interaction.matcher(text);
        return matcher_word_interaction.replaceAll(matchResult -> word_interaction_rules_replacer(matchResult, text));
    }

}
