package com.andaluh;
import static java.nio.charset.StandardCharsets.*;

public class Andaluh {
    public static String epa(String textoEspanyol, char charCedilla, char charJejeo, boolean escapeLinks, boolean debug)
    {

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

    # Do not start transcription if the input is empty
    if not text:
        return text

    def transliterate(text):
        for rule in rules:
            if rule in [x_rules, vaf_rules]:
                text = rule(text, vaf)
            elif rule == gj_rules:
                text = rule(text, vvf)
            else:
                text = rule(text)
            if debug: print rule.func_name + ' => ' + text

        return text

    if escapeLinks:
        ignore = to_ignore_re.findall(text) # Words in the message not to transliterate
        words = to_ignore_re.split(text) # Spanish words in the message to transliterate

        if not ignore:
            tags = []
            text = text
        else:
            # Replace words to ignore in the transliteration with randints
            tags = zip(map(lambda x: str(random.randint(1,999999999)), ignore), ignore)
            text = ''.join(reduce(lambda x, y: ''.join(x) + ''.join(y), zip(words, [x[0] for x in tags])))
            if len(words) > len(ignore): text += words[-1]

        if debug: print 'escapeLinks => ' + text
        text_and = transliterate(text)
        for tag in tags: text_and = text_and.replace(tag[0], tag[1])
        if debug: print 'unEscapeLinks => ' + text_and
        return text_and
    else:
        return transliterate(text)
        */


        // TODO
        String textoAndaluh = textoEspanyolUTF8;
        return textoAndaluh;
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
