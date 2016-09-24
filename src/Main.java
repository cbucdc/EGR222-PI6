import java.util.Random;

public class Main {
    public static int nthDigit(int n, int d){
        double N = (double) n;
        double D = d;
        return (int) ((N % (double) Math.pow(10,(D + 1))) / (double)  Math.pow(10,D));
    }

    public static int numDigits(int n){
        return (int) Math.ceil(Math.log(n) / Math.log(10));
    }

    public static int mask(int n, int offset ){
        int digits = 0;
        int nDigits = numDigits(n);
        for ( int d = 0 ; d < nDigits; d++){
            //need f(n) = n if n is even, 1 if n is odd
            int digit = nthDigit(n,d);

            digits += Math.pow(10,d)*( digit) * ((d+offset)%2);

        }
        return digits;
    }

    public static int swapDigitPairs(int n ){
        double N = n;
        int nDigits = numDigits(n);
        int first = 0;
        if ((nDigits+1) %2 == 0){
            first = (int) (Math.pow(10.0,nDigits - 1)*nthDigit(n, nDigits - 1));
            n = (int) (n % Math.pow(10.0,nDigits - 1));
        }
        int oddDigits = mask(n,0);
        int evenDigits = mask(n,1);
        return first + (evenDigits*100 + oddDigits)/10;
    }

    public static String leftpad(int n, int l){
        String s = new Integer(n).toString();
        String out = "";
        for (int i = 0; i < l - out.length(); i ++){
            out += " ";
        }
        return out + s;
    }
    public static void main(String[] args) {
        Random rand = new Random();

        for (int i = 0; i < 10; i++){
            int n = rand.nextInt(1000000);
            System.out.println(n);
            System.out.println("S: " + swapDigitPairs(n));
//            System.out.println("e: " +leftpad(mask(n,0),0));
//            System.out.println("o: " + leftpad(mask(n,1),0));
//            System.out.println("sh: " + (mask(n,0) + mask(n,1)*100)/10);
            System.out.println("-------");
        }
//        System.out.println((numDigits(123456)));
    }
}
