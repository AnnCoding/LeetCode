package august;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author chenjiena
 * @version 1.0
 * @created 2020/8/4.
 */
public class Solution {

    public  static int removeDuplicates(int[] nums){
        Set<Integer> set = new HashSet<Integer>();
        int count = 0;
        for (int i = 0 ; i< nums.length;i++){
            if (!set.add(nums[i])){
                nums[i] = -1;
            }else {
                ++count;
            }
        }
        for (int i = 0 ; i< nums.length;i++){
        }
        return count;
    }

    public static void main(String[] args) throws ParseException {
//        int []nums = {1,1,2};
//        int count = removeDuplicates(nums);
//
//        System.out.println(count);
//        for (int i = 0 ; i< nums.length;i++) {
//            System.out.print(nums[i]);
//        }
        String authToken1 = "201217";
        String authToken2 = "210109";

        String day1 = "20" + authToken1;
        String day2 = "20" + authToken2;
        SimpleDateFormat a = new SimpleDateFormat("yyMMdd");
        Date A1 = a.parse(authToken1);
        Date A2 = a.parse(authToken2);

        System.out.println(A1.before(A2));

        SimpleDateFormat b = new SimpleDateFormat("yyMMdd");
        String day3 = b.format(new Date());

        System.out.println(day3);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + 20);
        Date today = calendar.getTime();



        System.out.println(b.format(today));

    }



}
