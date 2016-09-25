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
        for (int d = 0; d < nDigits; d++) {
            int digit = nthDigit(n, d);
            //where is this digit
            int place = (int) Math.pow(10, d);
            //should this digit be masked out?
            int masked = (d + offset) % 2;
            //add the digit to the final output
            digits +=  place * digit * masked;
        }
        return digits;
    }

    //the star of the show
    public static int swapDigitPairs(int n) {
        double N = n;
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
        //this is the magic we can finally do after the masking.
        //we can simply shift the evens to the left and add to odds
        //this result must then be shifted back to get rid of trailing zeroes.

        int tail = (evenDigits * 100 + oddDigits)/10;
        //put the first digit back on
        return firstDigit + tail;
    }

    public static int sdp(int n) {
        int out = 0;
        int count = 0;
        while (n > 9){
            //get just two least-significant digits
            int meh = n%100;
            //reduce n
            n /= 100;
            //swap
            meh = (meh%10)*10 + meh /10;
            //multiply to correctly place pair of digits
            for (int i = 0; i < count; i ++){
                meh *= 100;
            }
            out += meh;
            //keep track of where we are in the output
            count ++;
        }
        //check for leftovers due to odd num of digits
        if (n > 0){
            //shift that extra digit left and put it back on
            for ( int i = 0; i < count; i ++){
                n *= 100;
            }
            out += n;
        }
        return out;
    }

    public static void main(String[] args) {
        int max = 1000;
        Random rand = new Random();

        int numTests = 100000;
        for (int i = 0; i < numTests; i++) {
            int n = rand.nextInt(max);
            System.out.println(n);
            int n1 = swapDigitPairs(n);
            int n2 = sdp(n);
            System.out.println(n1);
            System.out.println(n2);
            if (n1 != n2) {
                System.out.println("FAIL " + n1 + " " + n2);
                break;
            }
            System.out.println(new String(new char[numDigits(max)]).replace("\0", "-"));
        }
        System.out.println("Succeeded in " + numTests + " tests!");
    }
}
