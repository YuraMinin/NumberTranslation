package service.impl;

import service.NumberTranslationService;
import service.ReadingDictionaryService;

import java.util.List;

public class NumberTranslationServiceImpl implements NumberTranslationService {

    // Словарь
    private  String[][] degreesNumbers;
    private String[][] unitsToHundredsNumbers;
    private List<String> numbers11To19;

    // Колличество разбиений числа на состовляющие
    private int countNumberSplitting;

    // Максимальный диапазон чисел;
    private double numberMax;


    public NumberTranslationServiceImpl(){
        ReadDictionary();
    }

    // Чтение словоря из файла
    private void ReadDictionary() {

        ReadingDictionaryService dictionaryService = new ReadingDictionaryServiceImpl();

        numbers11To19 = dictionaryService.readingNumbers11To19();
        degreesNumbers = dictionaryService.readingDegreesNumbers();
        unitsToHundredsNumbers = dictionaryService.readingNumbersUnitsToHundreds();

        countNumberSplitting = degreesNumbers[0].length;
        numberMax = (long) Math.pow(10, 3*countNumberSplitting);

    }

    // Разбиение числа
    public String NumberTranslate(double num)   {


        String numText;

        // Проверка число на допустимость
        if (num < -numberMax || num > numberMax) {
            return numText = "Число выходит за диапазон (дополните словарь degreesNumbers.txt)";

        } else if (num == 0) {
            return numText = "ноль";

        } else if (num < 0) {
            numText = "минус ";

        } else {
            numText = "";
        }


        // сумма переведенных в текстовую форму чисел
        double number = 0;

        // Номер столбца в матрице степеней числа 0, 1, 2 ...
        int indexNumber = 0;

        for (int i = (countNumberSplitting - 1)*3; i >= 0; i -= 3) {

            // Если число больше 1000 => разбиваем
            if (Math.pow(10, i) != (double) 1) {


                double translateNumber =  (num - number) / Math.pow(10, i);
                int integerNumber = (int) translateNumber;

                number += integerNumber * Math.pow(10, i);
                numText += NumberPartTranslate(integerNumber, indexNumber);

            } else {
                double translateNumber = (num % 1000);
                numText += NumberPartTranslate((int) translateNumber, indexNumber);
            }

            indexNumber++;
        }

        return numText;

    }


    // Перевод состовляющего числа в текст
    private String NumberPartTranslate(int number, int numberColDegrees) {

        // разбиваем числа на сотни, десятки, единицы
        int hundreds = (int) number / 100;
        int decimals = (int) (number - (hundreds * 100)) / 10;
        int units = (int) number % 10;


        // формируем число без степени
        String numText = "";

        if (decimals == 1) {
            // Если десятки меньше 20 но больше 9
            numText = unitsToHundredsNumbers[2][hundreds] + numbers11To19.get(units);

        } else {
            numText = unitsToHundredsNumbers[2][hundreds]
                    + unitsToHundredsNumbers[1][decimals]
                    + unitsToHundredsNumbers[0][units];

        }



        // формируем окончания в единицах в тысячах
        if (countNumberSplitting - numberColDegrees == 2) {
            if (units == 1 && decimals != 1) numText = numText + "на";
            else if (units == 2 & decimals != 1) numText = numText + "е";
            if (units > 1 && decimals != 1) numText = numText + "";

        } else {

            // в других степенях
            if (units == 1 && decimals != 1) numText = numText + "ин";
            if (units == 2 & decimals != 1) {
                numText = numText + "а";
            } else if (units != 0 & decimals != 1) numText = numText + "";
        }

        // формируем степень числа
        int numberRowDegrees = 0;
        if (number != 0) {

            // число десятки начинается на единицу
            if (units == 0 || decimals == 1) {
                numberRowDegrees = 0;

            } else if (units == 1) {
                // число заканчивается на единицу
                numberRowDegrees = 1;
            } else if (units < 5) {
                numberRowDegrees = 2;

            }
            else {
                numberRowDegrees = 0;
            }
        } else {
            return numText;
        }

       if (!degreesNumbers[numberRowDegrees][numberColDegrees].equals("")) {
           numText += degreesNumbers[numberRowDegrees][numberColDegrees] + " ";
           return numText;

       } else {
           return numText;
       }
    }
}
