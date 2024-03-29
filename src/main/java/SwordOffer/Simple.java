package SwordOffer;

import java.util.Arrays;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;

/**
 * @author chenjiena
 * @version 1.0
 * @created 2021/8/21.
 */
public class Simple {

    public static void main(String[] args) {

        int a = 15;
        int b = -2;

        System.out.println(divide(a, b));
    }

    /**
     * 从这里开始----------------
     * @return
     */

    //最小的k个数
    public int[] getLeastNumbers(int[] arr, int k) {
        int[] vec = new int[k];
        Arrays.sort(arr);
        for (int i = 0; i < k; ++i) {
            vec[i] = arr[i];
        }
        return vec;
    }


    PriorityQueue<Integer> queMin;
    PriorityQueue<Integer> queMax;

    public Simple() {
        queMin = new PriorityQueue<Integer>((a, b) -> (b - a));
        queMax = new PriorityQueue<Integer>((a, b) -> (a - b));
    }

    public void addNum(int num) {
        if (queMin.isEmpty() || num <= queMin.peek()) {
            queMin.offer(num);
            if (queMax.size() + 1 < queMin.size()) {
                queMax.offer(queMin.poll());
            }
        } else {
            queMax.offer(num);
            if (queMax.size() > queMin.size()) {
                queMin.offer(queMax.poll());
            }
        }
    }

    public double findMedian() {
        if (queMin.size() > queMax.size()) {
            return queMin.peek();
        }
        return (queMin.peek() + queMax.peek()) / 2.0;
    }






    /**
     * -------------------从这里结束
     * @param nums
     * @return
     */

    //把数组排成最小的数
    public String minNumber(int[] nums) {
        String[] strs = new String[nums.length];
        for (int i = 0; i < nums.length; i++)
            strs[i] = String.valueOf(nums[i]);
        Arrays.sort(strs, (x, y) -> (x + y).compareTo(y + x));
        StringBuilder res = new StringBuilder();
        for (String s : strs)
            res.append(s);
        return res.toString();
    }

    //扑克牌中的顺子
    public boolean isStraight(int[] nums) {
        Set<Integer> repeat = new HashSet<>();
        int max = 0, min = 14;
        for(int num : nums) {
            if(num == 0) continue; // 跳过大小王
            max = Math.max(max, num); // 最大牌
            min = Math.min(min, num); // 最小牌
            if(repeat.contains(num)) return false; // 若有重复，提前返回 false
            repeat.add(num); // 添加此牌至 Set
        }
        return max - min < 5; // 最大牌 - 最小牌 < 5 则可构成顺子
    }


    /**
     * 001.整数除法 - 以下这种写法会超出时间时长
     */

    public static int divide(int a, int b) {

        if (a == 0x80000000 && b == -1) {
            return Integer.MAX_VALUE;
        }

        int count = 0;
        if (a == 0 || b == 0) {
            return count;
        }


        int negative = 2;
        if (a < 0) {
            negative--;
            a = -a;
        }
        if (b < 0) {
            negative--;
            b = -b;
        }


        int tmp = b;
        while (b < a) {
            b += tmp;
            ++count;
        }

        return negative == 1 ? -count : count;

    }


}
