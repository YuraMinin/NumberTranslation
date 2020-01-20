import service.NumberTranslationService;
import service.impl.NumberTranslationServiceImpl;
;
import java.util.InputMismatchException;
import java.util.Scanner;

public class numberTranslation {

    public static void main(String[] args) {

        NumberTranslationService numberTranslationService = new NumberTranslationServiceImpl();


        while (true) {

            try {
                Scanner in = new Scanner(System.in);
                System.out.print("Введите число: ");
                double num = in.nextDouble();
                String number = numberTranslationService.NumberTranslate(num);
                System.out.println("Число в текстовом виде: " + number);

            } catch (InputMismatchException e) {
                System.out.println("Введите правильное число");

            }

        }

    }

}
