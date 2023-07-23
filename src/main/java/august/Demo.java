package august;


import java.util.HashSet;
import java.util.Set;

/**
 * @author chenjiena
 * @version 1.0
 * @created 2020/12/17.
 */
public class Demo {

    public static void main(String[] args) {
        String astr = "hello";
        System.out.println(isUnique(astr));
    }

    public static boolean isUnique(String astr) {
        if (astr == null){
            return false;
        }
        char[] c = astr.toCharArray();
        Set<Character> ss = new HashSet<>();
        for (int i = 0;i<c.length;i++){
            if (!ss.add(c[i])){
                return false;
            }
        }

        return true;
    }

}
