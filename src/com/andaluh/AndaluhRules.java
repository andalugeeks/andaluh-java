package com.andaluh;

import javafx.util.Pair;

import java.util.*;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.andaluh.StringUtils;

public class AndaluhRules {

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
    private static final Pattern pattern_const_end = Pattern.compile("(?i)([aeiouáâçéíóú])([bcfgjklprtxz](\b))");
    private static final Pattern pattern_digraph = Pattern.compile("");
    private static final Pattern pattern_exception = Pattern.compile("");
    private static final Pattern pattern_word_interaction = Pattern.compile("");
    private static final Pattern pattern_vocal_tilde = Pattern.compile("(?i)á|é|í|ó|ú");
    // EPA character for Voiceless alveolar fricative /s/ https://en.wikipedia.org/wiki/Voiceless_alveolar_fricative
    public static final String VAF = "ç";
    public static final String VAF_mayus = "Ç";

    // EPA character for Voiceless velar fricative /x/ https://en.wikipedia.org/wiki/Voiceless_velar_fricative
    public static final String VVF = "h";
    public static final String VVF_mayus = "H";

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

    public static final Map<String,String> REPL_RULES = new HashMap<String,String>()
    {
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

    public static final Map<String,String> STRESSED_RULES  = new HashMap<String,String>()
    {
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

    public static final List<Pair<String,String>> H_RULES_EXCEPT = List.of(
            new Pair<String, String>("haz", "âh"), new Pair<String, String>("hez", "êh"), new Pair<String, String>("hoz", "ôh"),
            new Pair<String, String>("oh", "ôh"),
            new Pair<String, String>("yihad", "yihá"),
            new Pair<String, String>("h", "h") // Keep an isolated h as-is
    );

    public static final List<Pair<String,String>> GJ_RULES_EXCEPT = List.of(
            new Pair<String, String>("gin", "yin"), new Pair<String, String>("jazz", "yâh"), new Pair<String, String>("jet", "yêh")
    );

    public static final List<Pair<String,String>> V_RULES_EXCEPT = List.of(
            new Pair<String, String>("vis", "bî"), new Pair<String, String>("ves", "bêh")
    );

    public static final List<Pair<String,String>> LL_RULES_EXCEPT = List.of(
            new Pair<String, String>("grill", "grîh")
    );

    public static final List<Pair<String,String>> WORDEND_D_RULES_EXCEPT = List.of(
            new Pair<String, String>("çed", "çêh")
    );

    public static final List<Pair<String,String>> WORDEND_S_RULES_EXCEPT = List.of(
            new Pair<String, String>("bies", "biêh"), new Pair<String, String>("bis", "bîh"), new Pair<String, String>("blues", "blû"), new Pair<String, String>("bus", "bûh"),
            new Pair<String, String>("dios", "diôh"), new Pair<String, String>("dos", "dôh"),
            new Pair<String, String>("gas", "gâh"), new Pair<String, String>("gres", "grêh"), new Pair<String, String>("gris", "grîh"),
            new Pair<String, String>("luis", "luîh"),
            new Pair<String, String>("mies", "miêh"), new Pair<String, String>("mus", "mûh"),
            new Pair<String, String>("os", "ô"),
            new Pair<String, String>("pis", "pîh"), new Pair<String, String>("plus", "plûh"), new Pair<String, String>("pus", "pûh"),
            new Pair<String, String>("ras", "râh"), new Pair<String, String>("res", "rêh"),
            new Pair<String, String>("tos", "tôh"), new Pair<String, String>("tres", "trêh"), new Pair<String, String>("tris", "trîh")
    );

    public static final List<Pair<String,String>> WORDEND_CONST_RULES_EXCEPT = List.of(
            new Pair<String, String>("al", "al"), new Pair<String, String>("cual", "cuâ"), new Pair<String, String>("del", "del"), new Pair<String, String>("dél", "dél"), new Pair<String, String>("el","el"), new Pair<String, String>("él","èl"), new Pair<String, String>("tal", "tal"), new Pair<String, String>("bil", "bîl"),
            // TODO, uir = huir. Maybe better to add the exceptions on h_rules?
            new Pair<String, String>("por", "por"), new Pair<String, String>("uir", "huîh"),
            // sic, tac
            new Pair<String, String>("çic", "çic"), new Pair<String, String>("tac", "tac"),
            new Pair<String, String>("yak", "yak"),
            new Pair<String, String>("stop", "êttôh"), new Pair<String, String>("bip", "bip")
    );

    public static final List<Pair<String,String>> WORDEND_D_INTERVOWEL_RULES_EXCEPT = List.of(
            // Ending with -ado
            new Pair<String, String>("fado", "fado"), new Pair<String, String>("cado", "cado"), new Pair<String, String>("nado", "nado"), new Pair<String, String>("priado", "priado"),
            // Ending with -ada
            new Pair<String, String>("fabada", "fabada"), new Pair<String, String>("fabadas","fabadas"), new Pair<String, String>("fada", "fada"), new Pair<String, String>("ada", "ada"), new Pair<String, String>("lada", "lada"), new Pair<String, String>("rada", "rada"),
            // Ending with -adas
            new Pair<String, String>("adas", "adas"), new Pair<String, String>("radas", "radas"), new Pair<String, String>("nadas", "nadas"),
            // Ending with -ido
            new Pair<String, String>("aikido", "aikido"), new Pair<String, String>("bûççido", "bûççido"), new Pair<String, String>("çido", "çido"), new Pair<String, String>("cuido", "cuido"), new Pair<String, String>("cupido", "cupido"), new Pair<String, String>("descuido", "descuido"),
            new Pair<String, String>("despido", "despido"), new Pair<String, String>("eido", "eido"), new Pair<String, String>("embido", "embido"), new Pair<String, String>("fido", "fido"), new Pair<String, String>("hido", "hido"), new Pair<String, String>("ido", "ido"), new Pair<String, String>("infido", "infido"),
            new Pair<String, String>("laido", "laido"), new Pair<String, String>("libido", "libido"), new Pair<String, String>("nido", "nido"), new Pair<String, String>("nucleido", "nucleido"), new Pair<String, String>("çonido", "çonido"), new Pair<String, String>("çuido", "çuido")
    );

    public static final List<Pair<String,String>> ENDING_RULES_EXCEPTION = List.of(
            // Exceptions to digraph rules with nm
            new Pair<String, String>("biêmmandao","bienmandao"), new Pair<String, String>("biêmmeçabe","bienmeçabe"), new Pair<String, String>("buêmmoço","buenmoço"), new Pair<String, String>("çiêmmiléçima","çienmiléçima"), new Pair<String, String>("çiêmmiléçimo","çienmiléçimo"), new Pair<String, String>("çiêmmilímetro","çienmilímetro"), new Pair<String, String>("çiêmmiyonéçima","çienmiyonéçima"), new Pair<String, String>("çiêmmiyonéçimo","çienmiyonéçimo"), new Pair<String, String>("çiêmmirmiyonéçima","çienmirmiyonéçima"), new Pair<String, String>("çiêmmirmiyonéçimo","çienmirmiyonéçimo"),
            // Exceptions to l rules
            new Pair<String, String>("marrotadôh","mârrotadôh"), new Pair<String, String>("marrotâh","mârrotâh"), new Pair<String, String>("mirrayâ","mîrrayâ"),
            // Exceptions to psico pseudo rules
            new Pair<String, String>("herôççiquiatría","heroçiquiatría"), new Pair<String, String>("herôççiquiátrico","heroçiquiátrico"), new Pair<String, String>("farmacôççiquiatría","farmacoçiquiatría"), new Pair<String, String>("metempçícoçî","metemçícoçî"), new Pair<String, String>("necróçico","necróççico"), new Pair<String, String>("pampçiquîmmo","pamçiquîmmo"),
            // Other exceptions
            new Pair<String, String>("antîççerôttármico","antiçerôttármico"), new Pair<String, String>("eclampçia","eclampçia"), new Pair<String, String>("pôttoperatorio","pôççoperatorio"), new Pair<String, String>("çáccrito","çánccrito"), new Pair<String, String>("manbîh","mambîh"), new Pair<String, String>("cômmelináçeo","commelináçeo"), new Pair<String, String>("dîmmneçia","dînneçia"), new Pair<String, String>("todo", "tó"), new Pair<String, String>("todô", "tôh"), new Pair<String, String>("toda", "toa"), new Pair<String, String>("todâ", "toâ"),
            // Other exceptions monosyllables
            new Pair<String, String>("as","âh"), new Pair<String, String>("clown","claun"), new Pair<String, String>("crack","crâh"), new Pair<String, String>("down","daun"), new Pair<String, String>("es","êh"), new Pair<String, String>("ex","êh"), new Pair<String, String>("ir","îh"), new Pair<String, String>("miss","mîh"), new Pair<String, String>("muy","mu"), new Pair<String, String>("ôff","off"), new Pair<String, String>("os","ô"), new Pair<String, String>("para","pa"), new Pair<String, String>("ring","rin"), new Pair<String, String>("rock","rôh"), new Pair<String, String>("spray","êppray"), new Pair<String, String>("sprint","êpprín"), new Pair<String, String>("wa","gua")
    );

    public static String h_hue_rules_replacer(MatchResult matchResult, String text)
    {
        int match = matchResult.start();
        String gu_correct_capitalization = StringUtils.IsCapitalized(text.substring(match,match+1)) ? "Gü" : "gü";
        return gu_correct_capitalization + text.substring(match + 2, match + 3);
    }

    public static String h_hua_rules_replacer(MatchResult matchResult, String text)
    {
        int match = matchResult.start();
        String g_correct_capitalization = StringUtils.IsCapitalized(text.substring(match,match+1)) ? "G" : "g";
        return g_correct_capitalization + text.substring(match + 1, match + 3);
    }

    public static String h_rules_replacer(MatchResult matchResult, String text)
    {
        int match = matchResult.start();
        String undetermined_vocal = text.substring(match+1,match + 2);
        if(text.charAt(match) == 'H' || text.charAt(match) == 'h') {
            return text.charAt(match) == 'H' ? undetermined_vocal.toUpperCase() : undetermined_vocal;
        }
        else return "";
    }

    public static String h_rules (String text)
    {
        Matcher matcher_hua = pattern_h_hua.matcher(text);
        String hua_rules_applied = matcher_hua.replaceAll(matchResult -> h_hua_rules_replacer(matchResult, text));

        Matcher matcher_hue = pattern_h_hue.matcher(hua_rules_applied);
        String hue_rules_applied = matcher_hue.replaceAll(matchResult -> h_hue_rules_replacer(matchResult, hua_rules_applied));

        Matcher matcher_h_general = pattern_h_general.matcher(hue_rules_applied);
        return matcher_h_general.replaceAll(matchResult -> h_rules_replacer(matchResult, hue_rules_applied));
    }

    private static String x_starting_rules_replacer(MatchResult matchResult, String text)
    {
        int match = matchResult.start();
        String VAF_correct_capitalization = StringUtils.IsCapitalized(text.substring(match,match+1)) ? VAF_mayus : VAF;
        return text.substring(match, match) + VAF_correct_capitalization + text.substring(match + 1, match + 2);
    }

    public static String x_rules_replacer(MatchResult matchResult, String text)
    {
        int match = matchResult.start();
        return text.substring(match,match + 1) + VAF + VAF + text.substring(match + 2, match + 3);
    }

    public static String x_rules (String text)
    {
        Matcher matcher_x = pattern_x.matcher(text);
        String x_rules_applied = matcher_x.replaceAll(matchResult -> x_rules_replacer(matchResult, text));

        Matcher matcher_starting_x = pattern_x_starting.matcher(x_rules_applied);
        return matcher_starting_x.replaceAll(matchResult -> x_starting_rules_replacer(matchResult, x_rules_applied));
    }

    public static String ch_rules_replacer(MatchResult matchResult, String text)
    {
        int match = matchResult.start();
        String x_correct_capitalization = StringUtils.IsCapitalized(text.substring(match,match+1)) ? "X" : "x";
        return text.substring(match, match) + x_correct_capitalization + text.substring(match + 2, match + 2);
    }

    public static String ch_rules (String text)
    {
        Matcher matcher_ch = pattern_ch.matcher(text);
        return matcher_ch.replaceAll(matchResult -> ch_rules_replacer(matchResult, text));
    }

    public static String gj_rules_replacer(MatchResult matchResult, String text)
    {
        int match = matchResult.start();
        String x_correct_capitalization = StringUtils.IsCapitalized(text.substring(match,match+1)) ? VVF_mayus : VVF;
        return x_correct_capitalization + text.substring(match + 1, match + 2);
    }

    public static String gue_gui_rules_replacer(MatchResult matchResult, String text)
    {
        int match = matchResult.start();
        int matchEnd = matchResult.end();
        return text.substring(match, match + 1) + text.substring(match + 2, matchEnd);
    }

    public static String guue_guui_rules_replacer(MatchResult matchResult, String text)
    {
        int match = matchResult.start();
        int matchEnd = matchResult.end();
        String u_correct_capitalization = StringUtils.IsCapitalized(text.substring(match + 1,match+2)) ? "U" : "u";

        return text.substring(match, match + 1) + u_correct_capitalization + text.substring(match + 2, matchEnd);
    }

    public static String guen_rules_replacer(MatchResult matchResult, String text)
    {
        int match = matchResult.start();
        int matchEnd = matchResult.end();
        String g_correct_capitalization = StringUtils.IsCapitalized(text.substring(match,match+1)) ? "G" : "g";
        return g_correct_capitalization + text.substring(match + 1, matchEnd);
    }

    public static String guel_gues_rules_replacer(MatchResult matchResult, String text)
    {
        int match = matchResult.start();
        int matchEnd = matchResult.end();
        String g_correct_capitalization = StringUtils.IsCapitalized(text.substring(match,match+1)) ? "G" : "g";
        return g_correct_capitalization + text.substring(match + 1, matchEnd);
    }

    public static String gj_rules (String text)
    {
        Matcher matcher_gj = pattern_gj.matcher(text);
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

    public static String nv_rules_replacer(MatchResult matchResult, String text)
    {
        int match = matchResult.start();
        return text.substring(match,match) + "mb" + text.substring(match + 2, match + 2);
    }

    public static String v_rules_replacer(MatchResult matchResult, String text)
    {
        int match = matchResult.start();
        String v_correct_capitalization = StringUtils.IsCapitalized(text.substring(match,match+1)) ? "B" : "b";
        return text.substring(match, match) + v_correct_capitalization + text.substring(match + 2, match + 2);
    }

    public static String v_rules (String text)
    {
        Matcher matcher_nv = pattern_nv.matcher(text);
        String nv_rules_applied = matcher_nv.replaceAll(matchResult -> nv_rules_replacer(matchResult, text));

        Matcher matcher_v = pattern_v.matcher(nv_rules_applied);
        return matcher_v.replaceAll(matchResult -> v_rules_replacer(matchResult, nv_rules_applied));
    }

    public static String ll_rules_replacer(MatchResult matchResult, String text)
    {
        int match = matchResult.start();
        String ll_correct_capitalization = StringUtils.IsCapitalized(text.substring(match,match+1)) ? "Y" : "y";
        return text.substring(match, match) + ll_correct_capitalization + text.substring(match + 2, match + 2);
    }

    public static String ll_rules (String text)
    {
        Matcher matcher_ll = pattern_ll.matcher(text);
        return matcher_ll.replaceAll(matchResult -> ll_rules_replacer(matchResult, text));
    }

    public static String l_rules_replacer(MatchResult matchResult, String text)
    {
        int match = matchResult.start();
        String l_correct_capitalization = StringUtils.IsCapitalized(text.substring(match,match+1)) ? "R" : "r";
        return text.substring(match, match) + l_correct_capitalization + text.substring(match + 1, match + 2);
    }

    public static String l_rules (String text)
    {
        Matcher matcher_l = pattern_l.matcher(text);
        return matcher_l.replaceAll(matchResult -> l_rules_replacer(matchResult, text));
    }
    public static String psico_pseudo_rules_replacer(MatchResult matchResult, String text)
    {
        int match = matchResult.start();
        int matchEnd = matchResult.end();
        String undetermined_consonant = text.substring(match+1,match + 2);
        if(text.charAt(match) == 'P' || text.charAt(match) == 'p') {
            String undetermined_correct_capitalization = text.charAt(match) == 'P' ? undetermined_consonant.toUpperCase() : undetermined_consonant;
            return undetermined_correct_capitalization + text.substring(match + 2, matchEnd);
        }
        else return "";
    }

    public static String psico_pseudo_rules (String text)
    {
        Matcher matcher_psico_pseudo = pattern_psico_pseudo.matcher(text);
        return matcher_psico_pseudo.replaceAll(matchResult -> psico_pseudo_rules_replacer(matchResult, text));
    }

    public static String vaf_rules_replacer(MatchResult matchResult, String text)
    {
        int match = matchResult.start();
        String vaf_correct_capitalization = StringUtils.IsCapitalized(text.substring(match,match+1)) ? VAF_mayus : VAF;
        return text.substring(match, match) + vaf_correct_capitalization + text.substring(match + 1, match + 2);
    }

    public static String vaf_rules (String text)
    {
        Matcher matcher_vaf = pattern_vaf.matcher(text);
        return matcher_vaf.replaceAll(matchResult -> vaf_rules_replacer(matchResult, text));
    }

    public static Boolean contain_vocal_tilde(String text)
    {
        return pattern_vocal_tilde.matcher(text).find();
    }

    private static final char[] caracteresNoPalabra = {' ', ',', '.', ';', ':', '-', '_', '?', '¿', '(', '"', '\'', '\n', '\r', '['};

    public static int get_indice_principio_palabra(String text)
    {
        int indice = 0;
        int indiceMax = text.length();
        for (char c : caracteresNoPalabra) {
            int indiceCandidato = text.lastIndexOf(c);
            if(indiceCandidato < 0 || indiceCandidato > indiceMax) continue;
            indice = Integer.max(indiceCandidato, indice);
        }
        return indice;
    }

    public static String get_prefix(MatchResult matchResult, String text)
    {
        int match = matchResult.start();
        int matchEnd = matchResult.end();
        String prePalabra = text.substring(0, match);
        int indicePrincipioPalabra = get_indice_principio_palabra(prePalabra);
        return text.substring(indicePrincipioPalabra, match);
    }

    public static String intervowel_d_end_rules_replacer(MatchResult matchResult, String text)
    {
        int match = matchResult.start();
        int matchEnd = matchResult.end();

        String prefix = get_prefix(matchResult, text);

        String suffix = text.substring(match, matchEnd);

        if (contain_vocal_tilde(prefix)) return suffix;

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

    public static String eps_end_rules_replacer(MatchResult matchResult, String text)
    {
        int match = matchResult.start();
        int matchEnd = matchResult.end();

        String prefix = get_prefix(matchResult, text);

        String suffix = text.substring(match, matchEnd);

        if (!contain_vocal_tilde(prefix)) return suffix;
        return "ê";
    }

    public static String d_end_rules_replacer(MatchResult matchResult, String text)
    {
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

    public static String s_end_rules_replacer(MatchResult matchResult, String text)
    {
        int match = matchResult.start();
        int matchEnd = matchResult.end();

        String prefix = get_prefix(matchResult, text);

        String suffix = text.substring(match, matchEnd);

        if (!contain_vocal_tilde(Character.toString(suffix.charAt(0)))) {
            return apply_repl_rules(Character.toString(suffix.charAt(0)));
        }

        return apply_repl_rules(Character.toString(suffix.charAt(0))) + "h";
    }

    public static String const_end_rules_replacer(MatchResult matchResult, String text)
    {
        int match = matchResult.start();
        int matchEnd = matchResult.end();

        String prefix = get_prefix(matchResult, text);

        String suffix = text.substring(match, matchEnd);
        if (contain_vocal_tilde(prefix)) {
            return apply_repl_rules(Character.toString(suffix.charAt(0)));
        }

        return apply_repl_rules(Character.toString(suffix.charAt(0))) + "h";
    }

    public static String word_ending_rules (String text)
    {
        Matcher matcher_intervowel_d_end = pattern_intervowel_d_end.matcher(text);
        String intervowel_d_end_rules_applied = matcher_intervowel_d_end.replaceAll(matchResult -> intervowel_d_end_rules_replacer(matchResult, text));

        Matcher matcher_eps_end = pattern_eps_end.matcher(intervowel_d_end_rules_applied);
        String eps_end_rules_applied = matcher_eps_end.replaceAll(matchResult -> eps_end_rules_replacer(matchResult, intervowel_d_end_rules_applied));

        Matcher matcher_d_end = pattern_d_end.matcher(eps_end_rules_applied);
        String d_end_rules_applied = matcher_d_end.replaceAll(matchResult -> d_end_rules_replacer(matchResult, eps_end_rules_applied));

        Matcher matcher_s_end = pattern_s_end.matcher(d_end_rules_applied);
        String s_end_rules_applied = matcher_s_end.replaceAll(matchResult -> s_end_rules_replacer(matchResult, d_end_rules_applied));

        Matcher matcher_const_end = pattern_const_end.matcher(s_end_rules_applied);
        return matcher_const_end.replaceAll(matchResult -> const_end_rules_replacer(matchResult, s_end_rules_applied));
    }

    public static String digraph_rules_replacer(MatchResult matchResult, String text)
    {
        return text;
    }

    public static String digraph_rules (String text)
    {
        return text;
    }

    public static String exception_rules_replacer(MatchResult matchResult, String text)
    {
        return text;
    }

    public static String exception_rules (String text)
    {
        return text;
    }

    public static String word_interaction_rules_replacer(MatchResult matchResult, String text)
    {
        return text;
    }

    public static String word_interaction_rules (String text)
    {
        return text;
    }

}
