import java.util.Random;

public class Main {
    //return the nth digit of a base-10 number
    public static int nthDigit(int n, int d) {
        int tail = (int) (n % Math.pow(10, (d + 1)));
        int rightShift = (int) Math.pow(10, d);
        return (int) tail / rightShift;
    }

    //return the number of digits of a base-10 number
    public static int numDigits(int n) {
        return (int) Math.ceil(Math.log(n) / Math.log(10));
    }

    //return a base-10 number with either the even or odd digits all set to 0
    public static int mask(int n, int offset) {
        int digits = 0;
        int nDigits = numDigits(n);
        for (int d = 0; d < nDigits; d++) {
            //need f(n) = n if n is even, 1 if n is odd
            int digit = nthDigit(n, d);
            int place = (int) Math.pow(10, d);
            int masked = (d + offset) % 2;
            digits +=  place * digit * masked;

        }
        return digits;
    }

    public static int swapDigitPairs(int n) {
        double N = n;
        int nDigits = numDigits(n);
        int firstDigit = 0;
        if ((nDigits + 1) % 2 == 0) {
            firstDigit = (int) (Math.pow(10.0, nDigits - 1) * nthDigit(n, nDigits - 1));
            n = (int) (n % Math.pow(10.0, nDigits - 1));
        }
        int oddDigits = mask(n, 0);
        int evenDigits = mask(n, 1);
        return firstDigit + (evenDigits * 100 + oddDigits) / 10;
    }

    public static String leftpad(int n, int l) {
        String s = new Integer(n).toString();
        String out = "";
        for (int i = 0; i < l - out.length(); i++) {
            out += " ";
        }
        return out + s;
    }

    public static void main(String[] args) {
        Random rand = new Random();

        for (int i = 0; i < 10; i++) {
            int n = rand.nextInt(1000000);
            System.out.println(n);
            System.out.println("S: " + swapDigitPairs(n));
            System.out.println("-------");
        }
    }
}
