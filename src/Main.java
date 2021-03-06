import java.util.Random;

public class Main {
    //return the nth digit of a base-10 number
    public static int nthDigit(int n, int d) {
        //chop off the head of the number
        int tail = (int) (n % Math.pow(10, (d + 1)));
        //decide how far to right-shift it to chop off the tail
        int rightShift = (int) Math.pow(10, d);
        //chop chop
        return tail / rightShift;
    }

    //return the number of digits of a base-10 number
    public static int numDigits(int n) {
        //math formula for getting the digit length of a number in base-10
        return (int) Math.floor(1 + Math.log(n) / Math.log(10));
    }

    //return a base-10 number with either the even or odd digits all set to 0
    public static int mask(int n, int offset) {
        int digits = 0;
        //decide how far to iterate
        int nDigits = numDigits(n);
//        System.out.println("n, digits: " + n + ", " + nDigits);
        for (int d = 0; d < nDigits + 1; d++) {
            int digit = nthDigit(n, d);
            //where is this digit
            int place = (int) Math.pow(10, d);
            //should this digit be masked out?
            int masked = (d + offset) % 2;
            //add the digit to the final output
//            System.out.println("p, d, m: " + place + ", " + digit + ", " + masked);
            digits += place * digit * masked;
        }
        return digits;
    }

    //the star of the show
    public static int swapDigitPairs(int n) {
        int nDigits = numDigits(n);
        int firstDigit = 0;
        //handle odd-length numbers
        if ((nDigits + 1) % 2 == 0) {
            //mask out everything bust the first digit
            firstDigit = (int) (Math.pow(10.0, nDigits - 1) * nthDigit(n, nDigits - 1));
            //chop off the first, hhe simple minus operator can be used because of masking
            n = n - firstDigit;
        }
        int oddDigits = mask(n, 0);
        int evenDigits = mask(n, 1);
//        System.out.println("e, o: " + evenDigits + ", " + oddDigits);

        //this is the magic we can finally do after the masking.
        //we can simply shift the evens to the left and add to odds
        //this result must then be shifted back to get rid of trailing zeroes.

        int tail = (evenDigits * 100 + oddDigits) / 10;
        //put the first digit back on
        return firstDigit + tail;
    }

    //optimized version of swapDigitPairs
    public static int sdp(int n) {
        int temp = 0;
        int multiplier = 1;
        while (n > 9) {
            //get just two least-significant digits
            int twoDigits = n % 100;
            //swap and multiply to preserve shift
            twoDigits = ((twoDigits % 10) * 10 + twoDigits / 10) * multiplier;
            temp += twoDigits;
            //keep track of where we are in the output
            multiplier *= 100;
            //reduce n to remove already processed digits
            n /= 100;
        }
        //check for leftovers due to odd num of digits
        if (n > 0) {
            //shift that extra digit left and put it back on
            temp += n * multiplier;
        }
        return temp;
    }

    public static void main(String[] args) {
        int max = 10000;
        Random rand = new Random();

        int numTests = 100000;

        boolean success = true;
        for (int i = 1; i < numTests; i++) {
            int n = rand.nextInt(i);
//            n = 1000;
            System.out.println(n);
            int n1 = swapDigitPairs(n);
            int n2 = sdp(n);
            System.out.println(n1);
            System.out.println(n2);
            if (n1 != n2) {
                success = false;

                break;
            }
            System.out.println(new String(new char[numDigits(max)]).replace("\0", "-"));
        }
        if (success) {
            System.out.println("Succeeded in " + numTests + " tests!");
        }
    }
}
