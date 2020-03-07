package com.andaluh;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.List;

import static java.nio.charset.StandardCharsets.*;

public class Andaluh {

    // EPA character for Voiceless alveolar fricative /s/ https://en.wikipedia.org/wiki/Voiceless_alveolar_fricative
    public static final char VAF = 'ç';

    // EPA character for Voiceless velar fricative /x/ https://en.wikipedia.org/wiki/Voiceless_velar_fricative
    public static final char VVF = 'h';

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

    public static final List<Pair<String,String>> H_RULES_EXCEPT = List.of(
            new Pair("haz", "âh"), new Pair("hez", "êh"), new Pair("hoz", "ôh"),
            new Pair("oh", "ôh"),
            new Pair("yihad", "yihá"),
            new Pair("h", "h") // Keep an isolated h as-is
    );

    public static final List<Pair<String,String>> GJ_RULES_EXCEPT = List.of(
            new Pair("gin", "yin"), new Pair("jazz", "yâh"), new Pair("jet", "yêh")
    );

    public static final List<Pair<String,String>> V_RULES_EXCEPT = List.of(
            new Pair("vis", "bî"), new Pair("ves", "bêh")
    );

    public static final List<Pair<String,String>> LL_RULES_EXCEPT = List.of(
            new Pair("grill", "grîh")
    );

    public static final List<Pair<String,String>> WORDEND_D_RULES_EXCEPT = List.of(
            new Pair("çed", "çêh")
    );

    public static final List<Pair<String,String>> WORDEND_S_RULES_EXCEPT = List.of(
            new Pair("bies", "biêh"), new Pair("bis", "bîh"), new Pair("blues", "blû"), new Pair("bus", "bûh"),
            new Pair("dios", "diôh"), new Pair("dos", "dôh"),
            new Pair("gas", "gâh"), new Pair("gres", "grêh"), new Pair("gris", "grîh"),
            new Pair("luis", "luîh"),
            new Pair("mies", "miêh"), new Pair("mus", "mûh"),
            new Pair("os", "ô"),
            new Pair("pis", "pîh"), new Pair("plus", "plûh"), new Pair("pus", "pûh"),
            new Pair("ras", "râh"), new Pair("res", "rêh"),
            new Pair("tos", "tôh"), new Pair("tres", "trêh"), new Pair("tris", "trîh")
    );

    public static final List<Pair<String,String>> WORDEND_CONST_RULES_EXCEPT = List.of(
            new Pair("al", "al"), new Pair("cual", "cuâ"), new Pair("del", "del"), new Pair("dél", "dél"), new Pair("el","el"), new Pair("él","èl"), new Pair("tal", "tal"), new Pair("bil", "bîl"),
            // TODO, uir = huir. Maybe better to add the exceptions on h_rules?
            new Pair("por", "por"), new Pair("uir", "huîh"),
            // sic, tac
            new Pair("çic", "çic"), new Pair("tac", "tac"),
            new Pair("yak", "yak"),
            new Pair("stop", "êttôh"), new Pair("bip", "bip")
    );

    public static final List<Pair<String,String>> WORDEND_D_INTERVOWEL_RULES_EXCEPT = List.of(
            // Ending with -ado
            new Pair("fado", "fado"), new Pair("cado", "cado"), new Pair("nado", "nado"), new Pair("priado", "priado"),
            // Ending with -ada
            new Pair("fabada", "fabada"), new Pair("fabadas","fabadas"), new Pair("fada", "fada"), new Pair("ada", "ada"), new Pair("lada", "lada"), new Pair("rada", "rada"),
            // Ending with -adas
            new Pair("adas", "adas"), new Pair("radas", "radas"), new Pair("nadas", "nadas"),
            // Ending with -ido
            new Pair("aikido", "aikido"), new Pair("bûççido", "bûççido"), new Pair("çido", "çido"), new Pair("cuido", "cuido"), new Pair("cupido", "cupido"), new Pair("descuido", "descuido"),
            new Pair("despido", "despido"), new Pair("eido", "eido"), new Pair("embido", "embido"), new Pair("fido", "fido"), new Pair("hido", "hido"), new Pair("ido", "ido"), new Pair("infido", "infido"),
            new Pair("laido", "laido"), new Pair("libido", "libido"), new Pair("nido", "nido"), new Pair("nucleido", "nucleido"), new Pair("çonido", "çonido"), new Pair("çuido", "çuido")
    );

    public static final List<Pair<String,String>> ENDING_RULES_EXCEPTION = List.of(
            // Exceptions to digraph rules with nm
            new Pair("biêmmandao","bienmandao"), new Pair("biêmmeçabe","bienmeçabe"), new Pair("buêmmoço","buenmoço"), new Pair("çiêmmiléçima","çienmiléçima"), new Pair("çiêmmiléçimo","çienmiléçimo"), new Pair("çiêmmilímetro","çienmilímetro"), new Pair("çiêmmiyonéçima","çienmiyonéçima"), new Pair("çiêmmiyonéçimo","çienmiyonéçimo"), new Pair("çiêmmirmiyonéçima","çienmirmiyonéçima"), new Pair("çiêmmirmiyonéçimo","çienmirmiyonéçimo"),
            // Exceptions to l rules
            new Pair("marrotadôh","mârrotadôh"), new Pair("marrotâh","mârrotâh"), new Pair("mirrayâ","mîrrayâ"),
            // Exceptions to psico pseudo rules
            new Pair("herôççiquiatría","heroçiquiatría"), new Pair("herôççiquiátrico","heroçiquiátrico"), new Pair("farmacôççiquiatría","farmacoçiquiatría"), new Pair("metempçícoçî","metemçícoçî"), new Pair("necróçico","necróççico"), new Pair("pampçiquîmmo","pamçiquîmmo"),
            // Other exceptions
            new Pair("antîççerôttármico","antiçerôttármico"), new Pair("eclampçia","eclampçia"), new Pair("pôttoperatorio","pôççoperatorio"), new Pair("çáccrito","çánccrito"), new Pair("manbîh","mambîh"), new Pair("cômmelináçeo","commelináçeo"), new Pair("dîmmneçia","dînneçia"), new Pair("todo", "tó"), new Pair("todô", "tôh"), new Pair("toda", "toa"), new Pair("todâ", "toâ"),
            // Other exceptions monosyllables
            new Pair("as","âh"), new Pair("clown","claun"), new Pair("crack","crâh"), new Pair("down","daun"), new Pair("es","êh"), new Pair("ex","êh"), new Pair("ir","îh"), new Pair("miss","mîh"), new Pair("muy","mu"), new Pair("ôff","off"), new Pair("os","ô"), new Pair("para","pa"), new Pair("ring","rin"), new Pair("rock","rôh"), new Pair("spray","êppray"), new Pair("sprint","êpprín"), new Pair("wa","gua")
    );

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
