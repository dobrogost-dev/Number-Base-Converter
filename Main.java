package converter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String baseInput = "";
        String[] numberInput = new String[1];
        int sourceBase = 0;
        int targetBase = 0;
        while (true) {
            System.out.print("Enter two numbers in format: {source base} " +
                    "{target base} (To quit type /exit) ");
            baseInput = scanner.nextLine();
            if (baseInput.equals("/exit")) System.exit(0);
            if (baseInput.matches("([2-9]|[1-2][0-9]|3[0-6]) ([2-9]|[1-2][0-9]|3[0-6])")) {
                String[] base = baseInput.split(" ");
                sourceBase = Integer.parseInt(base[0]);
                targetBase = Integer.parseInt(base[1]);

                while (true) {
                    System.out.printf("Enter number in base %d to convert to " +
                            "base %d (To go back type /back) ", sourceBase, targetBase);
                    String actionInput = scanner.nextLine();
                    if (actionInput.equals("/back")) {
                        System.out.println();
                        break;
                    }
                    numberInput = actionInput.split("\\.");
                    System.out.println("Conversion result: "
                            + convertToBase(numberInput, sourceBase, targetBase));
                    System.out.println();
                }
            } else {
                System.out.println("Wrong input");
            }
        }

    }
    public static String convertToBase(String[] numberString, int sourceBase, int targetBase) {
        StringBuilder str = new StringBuilder("");
        BigInteger number = BigInteger.valueOf(convertToDecimal(numberString[0],sourceBase));
        if (number.equals(BigInteger.ZERO)) {
            if (numberString.length == 1) {
                return "0";
            } else {
                str.append("0");
            }
        }
        while (number.compareTo(BigInteger.ZERO) == 1) {
            int remainder = number.mod(BigInteger.valueOf(targetBase)).intValue();
            if (remainder < 10) {
                str.insert(0, remainder);
            } else {
                str.insert(0, (char) (remainder + 55));
            }
            number = number.divide(BigInteger.valueOf((long) targetBase));
        }
        if (numberString.length > 1) {
            str.append(".");
            BigDecimal decimal = convertFractionalToDecimal(numberString[1],sourceBase);
            int i = 0;
            do {
                decimal = decimal.multiply(BigDecimal.valueOf(targetBase));
                int calcResult = decimal.setScale(0, RoundingMode.FLOOR).intValue();
                if (calcResult > 9) {
                    str.append((char) (calcResult + 55));
                } else {
                    str.append(calcResult);
                }

                decimal = decimal.remainder(BigDecimal.ONE);
                i++;
            } while(i < 5);
        }
        return str.toString();
    }
    public static long convertToDecimal(String number, int base) {
        long sum = 0;
        for (int i = 0, j = number.length() - 1; i < number.length(); i++, j--) {
            sum += (Character.getNumericValue(number.charAt(i))) * Math.pow(base, j);
        }
        return sum;
    }
    public static BigDecimal convertFractionalToDecimal(String number, int base) {
        double sum = 0;
        for (int i = 0, j = -1; i < number.length(); i++, j--) {
            sum += (Character.getNumericValue(number.charAt(i))) * Math.pow(base, j);
        }
        BigDecimal bigDecimal = new BigDecimal(sum);
        return bigDecimal;
    }
}
