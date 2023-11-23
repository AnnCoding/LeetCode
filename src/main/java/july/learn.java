package july;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class learn {

    public static void main(String[] args) {
        int n = 0;
//        System.out.println(fib(n));


        int i = 9,j=9;

//        System.out.println(j == i++); true
//        System.out.println(i == i); true
//        System.out.println(j == ++i); false
//        System.out.println(++j == i); false
//        System.out.println(j++ == i); true


        ArrayList<String> a = new ArrayList<>();
        a.add("a");
        a.add("b");

        String[] str = new String[]{"a","b"};
        List<String> b = Arrays.asList(str);

//        ArrayList<String> c = (ArrayList<String>) a.subList(0,1); //报错了
//        String[] d = (String[]) a.toArray(); //报错

        b.remove(0);//报错


    }

    public static int fib(int n){
        if (n == 0){
            return 0;
        }
        if (n <= 2){
            return 1;
        }
        return fib(n-1) + fib(n-2);
    }
}
