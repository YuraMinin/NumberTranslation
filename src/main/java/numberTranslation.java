import service.NumberTranslationService;
import service.impl.NumberTranslationServiceImpl;
;
import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.Scanner;

public class numberTranslation {

    public static void main(String[] args) {

        NumberTranslationService numberTranslationService = NumberTranslationServiceImpl.getInstance();


        while (true) {

            try {

                Scanner in = new Scanner(System.in);

                System.out.print("Введите число: ");
                BigInteger num = in.nextBigInteger();
                String number = numberTranslationService.NumberTranslate(num);


                System.out.println("Число в текстовом виде: " + number);

            } catch (InputMismatchException e) {
                System.out.println("Введите правильное число");

            }

        }
    }
}
