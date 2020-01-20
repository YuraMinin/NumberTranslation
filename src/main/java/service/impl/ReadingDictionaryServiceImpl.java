package service.impl;

import service.ReadingDictionaryService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadingDictionaryServiceImpl implements ReadingDictionaryService {

    public String[][] readingDegreesNumbers() {
        return readTwoDimensionalMatrixNumbers("./src/main/resources/degreesNumbers.txt");
    }

    public String[][] readingNumbersUnitsToHundreds() {
        return readTwoDimensionalMatrixNumbers("./src/main/resources/unitsToHundredsNumbers.txt");
    }

    public List<String> readingNumbers11To19() {
        String line;

        try(BufferedReader reader = new BufferedReader(new FileReader("./src/main/resources/number11to19.txt"));) {

            List<String> number = new ArrayList<String>();
            while ((line = reader.readLine()) != null) {
                number.add(line);
            }

            return number;

        } catch (IOException e) {
            System.out.println("Ошибка в чтении файла: number11to19.txt");
            return null;
        }
    }


    private String[][] readTwoDimensionalMatrixNumbers(String fileName){

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {

            String line;
            List<String> numberInFile = new ArrayList<String>();

            while ((line = reader.readLine()) != null) {
                if (line.equals("*")) {
                    line = "";
                }
                numberInFile.add(line);
            }
            reader.close();


            String numbers[][] = new String[3][numberInFile.size() / 3];

            int k = 0;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < numberInFile.size() / 3; j++) {
                    numbers[i][j] = numberInFile.get(k);
                    k++;
                }
            }

            return numbers;

        } catch (IOException e) {
            System.out.println("Ошибка в чтении файла: " + fileName);
            return null;
        }
    }

}
