package com.andaluh;
import static java.nio.charset.StandardCharsets.*;

public class Andaluh {
    public static String transliterate(String text) {
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
        return text;
    }

    public static String epa(String textoEspanyol, char charCedilla, char charJejeo, boolean escapeLinks, boolean debug)
    {

        if(textoEspanyol == null || textoEspanyol.isEmpty() || textoEspanyol.isBlank()) return "";

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

        String[] tags;
        if (escapeLinks)
        {
            // TODO: idea generar una estructura de datos que sea un array de pares string y boolean solo con una llamada
            //       para esto hay que sobrecargar
            // por ejemplo: ParStringBoolean[] textoEspanyolUTF8_Ignorando = ProcessIgnorar(textoEspanyolUTF8);

            String text_and = transliterate(textoEspanyolUTF8); // aqui iria el textoEspanyolUTF8_Ignorando


            return text_and;
        }
        else
        {
            return transliterate(textoEspanyolUTF8);
        }

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
