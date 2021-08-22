package SwordOffer;

/**
 * @author chenjiena
 * @version 1.0
 * @created 2021/8/21.
 */
public class simple {

    public static void main(String[] args) {

        int a = 15;
        int b = -2;

        System.out.println(divide(a,b));
    }

    /**
     * 001.整数除法 - 以下这种写法会超出时间时长
     */

    public  static int divide(int a, int b) {

        if(a == 0x80000000 && b == -1) {
            return Integer.MAX_VALUE;
        }

        int count = 0;
        if (a == 0 || b == 0) {
            return count;
        }


        int negative = 2;
        if(a < 0) {
            negative--;
            a = -a;
        }
        if(b < 0) {
            negative--;
            b = -b;
        }



        int tmp = b;
        while (b < a){
            b += tmp;
            ++count;
        }

        return  negative == 1 ? -count : count;

    }


}
