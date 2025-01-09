package Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parser {
   
    public  List<ArrayList<String>> parseAssembly(String filePath) {
        ArrayList<String> textInstructions = new ArrayList<>();
        ArrayList<String> dataInstructions = new ArrayList<>();
        List<ArrayList<String>> result = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            boolean parsingTextSection = false;
            boolean parsingDataSection = false;
            String line;

            while ((line = br.readLine()) != null) {
                if (line.contains(".text")) {
                    parsingTextSection = true;
                    parsingDataSection = false;
                    continue;
                }

                if (line.contains(".data")) {
                    parsingDataSection = true;
                    parsingTextSection = false;
                    continue;
                }

                if (!(parsingTextSection || parsingDataSection)) {
                    parsingTextSection=true;
                }

                line = line.split("#")[0].trim();

                if (line.isEmpty()) {
                    continue;
                }

                if (parsingTextSection) {
                    textInstructions.add(line);
                } else if (parsingDataSection) {
                    dataInstructions.add(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        result.add(textInstructions);
        result.add(dataInstructions);

        return result;
    }
}