package service.impl;

import service.NumberTranslationService;
import service.ReadingDictionaryService;

import java.math.BigInteger;
import java.util.List;

public class NumberTranslationServiceImpl implements NumberTranslationService {

    // Словарь
    private  String[][] degreesNumbers;
    private String[][] unitsToHundredsNumbers;
    private List<String> numbers11To19;

    // Колличество разбиений числа на состовляющие
    private int countNumberSplitting;

    // Максимальный диапазон чисел;
    private BigInteger numberMax;


    public NumberTranslationServiceImpl(){
        ReadDictionary();
    }


    // Singleton
    private static NumberTranslationServiceImpl numberTranslationServiceInstance;

    public static NumberTranslationServiceImpl getInstance() {
        if (numberTranslationServiceInstance == null) {
            numberTranslationServiceInstance = new NumberTranslationServiceImpl();
        }

        return numberTranslationServiceInstance;
    }


    // Чтение словоря из файла
    private void ReadDictionary() {

        ReadingDictionaryService dictionaryService = new ReadingDictionaryServiceImpl();

        numbers11To19 = dictionaryService.readingNumbers11To19();
        degreesNumbers = dictionaryService.readingDegreesNumbers();
        unitsToHundredsNumbers = dictionaryService.readingNumbersUnitsToHundreds();

        countNumberSplitting = degreesNumbers[0].length;

        numberMax = new BigInteger("10");
        numberMax = numberMax.pow(3*countNumberSplitting + 1);
    }

    // Разбиение числа
    public String NumberTranslate(BigInteger num)   {

        String numText;

        // Проверка число на допустимость
        if (num.compareTo(numberMax) == 1 || num.compareTo(numberMax.divide(BigInteger.valueOf(-1))) == -1) {
            return numText = "Число выходит за диапазон (дополните словарь degreesNumbers.txt)";

        } else if (num.compareTo(new BigInteger("0")) == 0 ) {
            return numText = "ноль";

        } else if (num.compareTo(new BigInteger("0")) == -1) {
            numText = "минус ";
            num = num.abs();

        } else {
            numText = "";
        }


        // сумма переведенных в текстовую форму чисел после разбиения
        BigInteger number = new BigInteger("0");

        // Номер столбца в матрице степеней числа degreesNumbers[...][indexNumber];
        int indexNumber = 0;

        for (int i = (countNumberSplitting - 1)*3; i >= 0; i -= 3) {

            // Если число больше 1000 => разбиваем
            if (Math.pow(10, i) != (double) 1) {

                BigInteger translateNumber =  num;
                translateNumber = translateNumber.subtract(number);
                translateNumber = translateNumber.divide(new BigInteger("10").pow(i));

                number = number.add(translateNumber.multiply(new BigInteger("10").pow(i)));
                numText += NumberPartTranslate(translateNumber.intValue(), indexNumber);

            } else {
                BigInteger translateNumber = num.mod(new BigInteger("1000"));
                numText += NumberPartTranslate(translateNumber.intValue(), indexNumber);
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
            if (hundreds > 0) {
                numText = unitsToHundredsNumbers[2][hundreds] + " " + numbers11To19.get(units);
            } else {
                numText = numbers11To19.get(units);
            }

        } else {

            if (hundreds > 0 ) {

                if (decimals > 0 || units > 0 ) {
                numText += unitsToHundredsNumbers[2][hundreds] + " ";

                } else if (decimals == 0 && units  == 0){
                    numText += unitsToHundredsNumbers[2][hundreds];

                }
            }

            if (decimals > 0) {
                if (units > 0) {
                    numText += unitsToHundredsNumbers[1][decimals] + " ";
                } else {
                    numText += unitsToHundredsNumbers[1][decimals];
                }
            }

            if (units > 0) {
                numText += unitsToHundredsNumbers[0][units];
            }

        }

        // формируем окончания в единицах в тысячах
        if (countNumberSplitting - numberColDegrees == 2) {
            if (units == 1 && decimals != 1) {
                numText = numText + "на";

            } else if (units == 2 & decimals != 1) {
                numText = numText + "е";

            }
        } else {
            // в других степенях
            if (units == 1 && decimals != 1) {
                numText = numText + "ин";

            } else if (units == 2 & decimals != 1) {
                numText = numText + "а";

            }
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
