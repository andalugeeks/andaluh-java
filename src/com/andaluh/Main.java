package com.andaluh;

import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

public class Main {

    public static void main(String[] args) {
        ArgumentParser parser = ArgumentParsers.newFor("Andaluh").build().defaultHelp(true).description("Transliterate español (spanish) spelling to andaluz proposals");

        parser.addArgument("text").help("Text to transliterate. Enclose into quotes if there's more than one word");
        parser.addArgument("-e").choices("s","z","h").help("Enforce seseo, zezeo or heheo instead of cedilla");
        parser.addArgument("-j").help("Keep /x/ sounds as J instead of /h/").action(Arguments.storeTrue());

        Namespace parsedArgs = null;
        try {
            parsedArgs = parser.parseArgs(args);
        } catch (ArgumentParserException e) {
            parser.handleError(e);
            System.out.println("Aliquindoi a cómo uçar el traduttô");
            parser.printHelp();
            System.exit(1);
        }

        System.out.println(parsedArgs);

        char charJejeo;
        Boolean jejeo = parsedArgs.getBoolean("j");
        if(jejeo)
        {
            charJejeo = 'j';
        }
        else
        {
            charJejeo = 'h';
        }

        System.out.print("jejeo? ");
        System.out.println(jejeo);

        String strCedilla  = parsedArgs.getString("e");
        System.out.print("sustituimos cedilla? ");

        Boolean boolCedilla;
        if(strCedilla != null)
        {
            boolCedilla = !(strCedilla.isBlank() || strCedilla.isEmpty());
        }
        else
        {
            boolCedilla = false;
        }
        System.out.print(boolCedilla);

        char charCedilla;
        if(boolCedilla)
        {
            charCedilla = strCedilla.charAt(0);
            if(!(charCedilla == 's' || charCedilla == 'z' || charCedilla == 'h'))
                charCedilla = 'ç';

            System.out.println(", por " + charCedilla);
        }
        else
        {
            charCedilla = 'ç';
            System.out.println("");
        }



        String texto  = parsedArgs.getString("text");
        if(texto.isEmpty() || texto.isBlank())
        {
            texto = "BAÇÍO";
        }

        System.out.println(Andaluh.epa(texto,charCedilla, charJejeo));
    }
}
