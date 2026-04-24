package com.andaluh;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AndaluhTestRunner {

    private static class TestCase {
        final String input;
        final String expected;
        final char vaf;
        final char vvf;
        final boolean escapeLinks;

        TestCase(String input, String expected) {
            this(input, expected, 'ç', 'h', false);
        }

        TestCase(String input, String expected, boolean escapeLinks) {
            this(input, expected, 'ç', 'h', escapeLinks);
        }

        TestCase(String input, String expected, char vaf, char vvf, boolean escapeLinks) {
            this.input = input;
            this.expected = expected;
            this.vaf = vaf;
            this.vvf = vvf;
            this.escapeLinks = escapeLinks;
        }
    }

    public static void main(String[] args) {
        List<TestCase> cases = new ArrayList<>();

        cases.add(new TestCase(
                "Todo Xenomorfo dice: [haber], que el Éxito y el éxtasis asfixian, si no eres un xilófono Chungo.",
                "Tó Çenomorfo diçe: [abêh], que el Éççito y el éttaçî âffîççian, çi no erê un çilófono Xungo."
        ));
        cases.add(new TestCase(
                "Lleva un Guijarrito el ABuelo, ¡Qué Bueno! ¡para la VERGÜENZA!",
                "Yeba un Giharrito el AGuelo, ¡Qué Gueno! ¡pa la BERGUENÇA!"
        ));
        cases.add(new TestCase(
                "VALLA valla, si vas toda de ENVIDIA",
                "BAYA baya, çi bâ toa de EMBIDIA"
        ));
        cases.add(new TestCase(
                "Alrededor de la Alpaca había un ALfabeto ALTIVO de valkirias malnacidas",
                "Arrededôh de la Arpaca abía un ARfabeto ARTIBO de barkiriâ mânnaçidâ"
        ));
        cases.add(new TestCase(
                "En la Zaragoza y el Japón asexual se Sabía SÉriamente sILBAR con el COxis",
                "En la Çaragoça y er Hapón açêççuâh çe Çabía ÇÉriamente çIRBÂH con er CÔççî"
        ));
        cases.add(new TestCase(
                "Transportandonos a la connotación perspicaz del abstracto solsticio de Alaska, el aislante plástico adsorvente asfixió al aMnésico pseudoescritor granadino de constituciones, para ConMemorar broncas adscritas",
                "Trâpportandonô a la cônnotaçión perppicâh del âttrâtto çorttiçio de Alâkka, el aîl-lante pláttico âççorbente âffîççió al ânnéçico çeudoêccritôh granadino de côttituçionê, pa CôMMemorâh broncâ âccritâ"
        ));
        cases.add(new TestCase(
                "Venid todos a correr en anorak de visón a Cádiz con actitud y maldad, para escuchar el tríceps de Albéniz tocar ápud con virtud de laúd.",
                "Benîh tôh a corrêh en anorâh de biçón a Cádî con âttitûh y mardá, pa êccuxâh er tríçê de Arbénî tocâh ápû con birtûh de laûh."
        ));
        cases.add(new TestCase(
                "Una comida fabada con fado, y sin descuido será casada y amarrada al acolchado roido.",
                "Una comida fabada con fado, y çin dêccuido çerá caçá y amarrá al acorxao roío."
        ));
        cases.add(new TestCase(
                "Los SABuesos ChiHuaHUA comían cacaHuETes, FramBuESas y Heno, ¡y HABLAN con hálito de ESPANGLISH!",
                "Lô ÇAGueçô XiGuaGUA comían cacaGuETê, FramBuEÇâ y Eno, ¡y ABLAN con álito de ÊPPANGLÎ!"
        ));
        cases.add(new TestCase(
                "Oye sexy psiquiatra @miguel, la #web HTTPS://andaluh.es/transcriptor no çale en google.es pero çi en http://google.com?utm=13_123.html #porqueseñor",
                "Oye çêççy çiquiatra @miguel, la #web HTTPS://andaluh.es/transcriptor no çale en google.es pero çi en http://google.com?utm=13_123.html #porqueseñor",
                true
        ));

        // Parameterized tests
        cases.add(new TestCase("cena", "sena", 's', 'h', false));
        cases.add(new TestCase("gente", "jente", 'ç', 'j', false));

        int failures = 0;
        failures += runInlineCases(cases);

        double minRate = parseMinRate(args);
        String corpusPath = resolveCorpusPath(args);
        if (corpusPath != null) {
            failures += runCorpus(corpusPath, minRate);
        }

        if (failures > 0) {
            System.out.println("Total failures: " + failures);
            System.exit(1);
        } else {
            int total = cases.size();
            System.out.println("All tests passed: " + total + " + corpus");
        }
    }

    private static int runInlineCases(List<TestCase> cases) {
        int failures = 0;
        for (int i = 0; i < cases.size(); i++) {
            TestCase tc = cases.get(i);
            String actual = Andaluh.epa(tc.input, tc.vaf, tc.vvf, tc.escapeLinks, false);
            if (!tc.expected.equals(actual)) {
                failures++;
                System.out.println("FAIL #" + (i + 1));
                System.out.println("Input:    " + tc.input);
                System.out.println("Expected: " + tc.expected);
                System.out.println("Actual:   " + actual);
                System.out.println("");
            }
        }
        return failures;
    }

    private static String resolveCorpusPath(String[] args) {
        for (String arg : args) {
            if ("--no-corpus".equals(arg)) {
                return null;
            }
        }

        for (String arg : args) {
            if (arg.startsWith("--min-rate=")) {
                continue;
            }
            if (!arg.startsWith("--")) {
                return arg;
            }
        }

        String env = System.getenv("ANDALUH_LEMARIO_CSV");
        if (env != null && !env.isBlank()) {
            return env;
        }

        Path defaultPath = Paths.get("..", "andalugeeks-andaluh-py-18621fe", "tests", "lemario_cas_and.csv");
        return defaultPath.toString();
    }

    private static double parseMinRate(String[] args) {
        double minRate = 0.93;
        for (String arg : args) {
            if (arg.startsWith("--min-rate=")) {
                String value = arg.substring("--min-rate=".length());
                try {
                    minRate = Double.parseDouble(value);
                } catch (NumberFormatException ignored) {
                    System.out.println("Invalid --min-rate value, using default 0.93");
                }
            }
        }
        return minRate;
    }

    private static int runCorpus(String csvPath, double minRate) {
        int failures = 0;
        int total = 0;
        int reported = 0;
        int reportLimit = 20;

        Path path = Paths.get(csvPath);
        if (!Files.exists(path)) {
            System.out.println("Corpus not found: " + csvPath);
            return 1;
        }

        try (BufferedReader reader = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            String line;
            boolean first = true;
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) continue;
                if (line.charAt(line.length() - 1) == '\r') {
                    line = line.substring(0, line.length() - 1);
                }

                List<String> parts = parseCsvLine(line);
                if (parts.size() < 2) continue;

                if (first && "cas".equals(parts.get(0)) && "and".equals(parts.get(1))) {
                    first = false;
                    continue;
                }
                first = false;

                String input = parts.get(0);
                String expected = parts.get(1);
                total++;

                String actual = Andaluh.epa(input, 'ç', 'h', false, false);
                if (!expected.equals(actual)) {
                    failures++;
                    if (reported < reportLimit) {
                        System.out.println("CORPUS FAIL #" + failures);
                        System.out.println("Input:    " + input);
                        System.out.println("Expected: " + expected);
                        System.out.println("Actual:   " + actual);
                        System.out.println("");
                        reported++;
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading corpus: " + e.getMessage());
            return 1;
        }

        double rate = total == 0 ? 0.0 : (double) (total - failures) / total;
        String rateText = String.format(Locale.ROOT, "%.2f%%", rate * 100.0);
        String minText = String.format(Locale.ROOT, "%.2f%%", minRate * 100.0);

        if (rate >= minRate) {
            System.out.println("Corpus passed: " + total + " cases (" + rateText + ", min " + minText + ")");
            return 0;
        }

        System.out.println("Corpus failures: " + failures + " / " + total + " (" + rateText + ", min " + minText + ")");
        return failures;
    }

    private static List<String> parseCsvLine(String line) {
        List<String> parts = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    current.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                parts.add(current.toString());
                current.setLength(0);
            } else {
                current.append(c);
            }
        }
        parts.add(current.toString());
        return parts;
    }
}
