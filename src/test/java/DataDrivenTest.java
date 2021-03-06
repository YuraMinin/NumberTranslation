import org.junit.jupiter.api.Test;
import service.NumberTranslationService;
import service.impl.NumberTranslationServiceImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class DataDrivenTest {

    @Test
    public void testGetNameNumberZero() {

        System.out.println("Test 1, Number 0");

        NumberTranslationService numberTranslationService = NumberTranslationServiceImpl.getInstance();


        System.out.println(0 + " = " + numberTranslationService.NumberTranslate(new BigInteger("0")));

        assertEquals("ноль", numberTranslationService.NumberTranslate(new BigInteger("0")));
    }


    @Test
    public void testGetNameNumber11To19() {

        System.out.println("Test 2, Numbers 1 - 19");

        NumberTranslationService numberTranslationService = NumberTranslationServiceImpl.getInstance();

        String[] NumberText = new String[]{"один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь",
                "девять", "десять", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать", "пятнадцать",
                "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"};

        for (int i = 1; i < 20; i++) {
            System.out.println(i + " = " + numberTranslationService.NumberTranslate(BigInteger.valueOf(i)));
            assertEquals(NumberText[i - 1],
                    numberTranslationService.NumberTranslate(BigInteger.valueOf(i)));

        }
    }


    @Test
    public void testGetNumberFromFile() {

        NumberTranslationService numberTranslationService = NumberTranslationServiceImpl.getInstance();

        String line;
        List<String> numberText = new ArrayList<String>();
        List<Long> number = new ArrayList<Long>();
        int iterator = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader("./src/test/resources/TestNumbers.txt"));) {

            while ((line = reader.readLine()) != null) {
                if (iterator % 2 == 0) {
                    numberText.add(line);
                    iterator++;
                } else {
                    number.add(Long.parseLong(line));
                    iterator++;
                }
            }

        } catch (IOException e) {
            System.out.println("Ошибка в чтении файла: TestNumbers.txt");
        }

        for (int i = 0; i < iterator / 2; i++) {
            System.out.println(number.get(i).intValue() + " = " + numberText.get(i));
           assertEquals(numberText.get(i), numberTranslationService.NumberTranslate(BigInteger.valueOf(number.get(i))));
        }
    }
}
