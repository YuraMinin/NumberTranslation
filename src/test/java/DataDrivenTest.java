import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import service.NumberTranslationService;
import service.impl.NumberTranslationServiceImpl;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;


public class DataDrivenTest {

    @Test
    public void testGetNameZero() throws Exception {

        System.out.println("Test 1, Number 0");

        NumberTranslationService numberTranslationService = new NumberTranslationServiceImpl();

        int zero = 0;
        String ZERO = "ноль";

        System.out.println(zero + " = " + numberTranslationService.NumberTranslate(zero));
        assertEquals( "ноль", numberTranslationService.NumberTranslate(zero));
    }


    @Test
    public void testGetNameUnit() throws Exception {

        System.out.println("Test 2, Numbers 1 - 19");

        NumberTranslationService numberTranslationService = new NumberTranslationServiceImpl();

        String[] NumberText = new String[]{"один", "два", "три", "четыре",
                "пять", "шесть", "семь", "восемь", "девять", "десять", "одиннадцать", "двенадцать", "тринадцать",
                "четырнадцать", "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать"};

        //проверка чисел от одного до девятнадцати
        for (int i = 1; i < 20; i++) {
            System.out.println(i + " = " + numberTranslationService.NumberTranslate(i));
            assertEquals(NumberText[i - 1],
                    numberTranslationService.NumberTranslate(i));
        }
    }


    @Test
    public void testGetNumberFromFile() throws Exception {


        System.out.println("Test 3, reading data from a file");

        NumberTranslationService numberTranslationService = new NumberTranslationServiceImpl();


        String line;

        List<String> numberText = new ArrayList<String>();
        List<Double> number = new ArrayList<Double>();
        int iterator = 0;

        try(BufferedReader reader = new BufferedReader(new FileReader("./src/test/resources/TestNumbers.txt"));) {



            while ((line = reader.readLine()) != null) {
                if (iterator % 2 == 0)  {
                    numberText.add(line);
                    iterator++;
                } else {
                    number.add(Double.parseDouble(line));
                    iterator++;
                }
            }


        } catch (IOException e) {
            System.out.println("Ошибка в чтении файла: TestNumbers.txt");
        }


        for (int i = 0; i < iterator/2; i++) {
            System.out.println(number.get(i).intValue() + " = " + numberText.get(i));
            assertEquals(numberText.get(i), numberTranslationService.NumberTranslate(number.get(i)));
        }

    }







    }
