package dynamic;

import december.Solution;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenjiena
 * @version 1.0
 * @created 2022/1/8.
 */
public class Solution1 {


    public static class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

    public static void main(String[] args) {

        int n = 4;

//        int result = fib(n);
//        int result = tribonacci(n);

        String title = "i First leTTeR of EACH Word";
        int[] nums = {2,2,4,3,4,3,3};
        deleteAndEarn(nums);

        System.out.println(capitalizeTitle(title));
    }

    public static int deleteAndEarn(int[] nums) {

        //排序并归并成可计算的

        int maxVal = 0;
        for (int val : nums) {
            maxVal = Math.max(maxVal, val);
        }
        int[] sum = new int[maxVal + 1];
        for (int val : nums) {
            sum[val] += val;
        }

        return rob(sum);


    }

    /**
     * 给你一个整数数组 cost ，其中 cost[i] 是从楼梯第 i 个台阶向上爬需要支付的费用。一旦你支付此费用，即可选择向上爬一个或者两个台阶。
     *
     * 你可以选择从下标为 0 或下标为 1 的台阶开始爬楼梯。
     *
     * 请你计算并返回达到楼梯顶部的最低花费。
     *
     * dp方程 ：dp[i]=min(dp[i−1]+cost[i−1],dp[i−2]+cost[i−2])
     *
     * @param cost
     * @return
     */
    public int minCostClimbingStairs(int[] cost) {
        int n = cost.length;  //所要爬上去的高度

        int[] dp = new int[n+1];
        //--你可以选择从下标为 0 或下标为 1 的台阶开始爬楼梯。
        dp[0] = dp[1] = 0;

        //可以理解为离开这个台阶需要花费的金额
        for (int i = 2;i<=n;i++){
            dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
        }

        return dp[n];
    }

    public int minCostClimbingStairsV2(int[] cost) {
        int n = cost.length;  //所要爬上去的高度

        int prev = 0;
        int curr = 0;

        int next = 0;
        //可以理解为离开这个台阶需要花费的金额
        for (int i = 2;i<=n;i++){
            next = Math.min(curr + cost[i - 1], prev + cost[i - 2]);
            curr = next;
            prev = curr;
        }
        return next;

        /*
        int n = cost.length;
        int prev = 0, curr = 0;
        for (int i = 2; i <= n; i++) {
            int next = Math.min(curr + cost[i - 1], prev + cost[i - 2]);
            prev = curr;
            curr = next;
        }
        return curr;
         */
    }



    public static int climbStairs(int n) {

        if (n <= 2){
            return n;
        }

        int[] f = new int[n+1];
        f[1] = 1;
        f[2] = 2;
        for (int i = 3; i <= n; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }

        return f[n];

    }

    /**
     * 在一个大小为 n 且 n 为 偶数 的链表中，对于 0 <= i <= (n / 2) - 1 的 i ，第 i 个节点（下标从 0 开始）的孪生节点为第 (n-1-i) 个节点 。
     *
     * 比方说，n = 4 那么节点 0 是节点 3 的孪生节点，节点 1 是节点 2 的孪生节点。这是长度为 n = 4 的链表中所有的孪生节点。
     * 孪生和 定义为一个节点和它孪生节点两者值之和。
     *
     * 给你一个长度为偶数的链表的头节点 head ，请你返回链表的 最大孪生和 。
     *
     * @param head
     * @return
     */

    public static int pairSum(ListNode head) {

        //--遍历元素列表，放到List中。
        //--按照孪生和概念求最大值。

        List<Integer> list = new ArrayList<>();
        ListNode dummy = new ListNode(-1,head);

        while (dummy.next != null){
            list.add(dummy.next.val); //因为前面定义的时候是一个虚拟头结点
            dummy = dummy.next;
        }
        int max = 0;
        for (int i = 0;i<=(list.size() - 1)/2 ;i++){
            max = Math.max(max,list.get(i) + list.get(list.size() -1 -i));
        }
        return max;
    }


    public static String capitalizeTitle(String title){
        String xiao = title.toLowerCase();
        String[] sb = xiao.split(" ");
        StringBuilder sb1 = new StringBuilder();

        for (int i = 0;i<sb.length;i++){
           if (sb[i].length() <= 2){
               sb1.append(sb[i]);
           }else {
               char[] tmp = sb[i].toCharArray();
               tmp[0] = (char) (tmp[0]-32);
               sb1.append(String.valueOf(tmp));
           }
            sb1.append(" ");
        }
        sb1.delete(sb1.length()-1,sb1.length());
        return sb1.toString();
    }

    public static int fib(int n) {
        int[] f = new int[31];
        f[0] = 0;
        f[1] = 1;
        for (int i = 2; i <= n; i++) {
            f[i] = f[i - 1] + f[i - 2];
        }

        return f[n];
    }

    public static int tribonacci(int n) {
        int[] t = new int[38];

        t[0] = 0;
        t[1] = 1;
        t[2] = 1;

        for (int i = 3; i <= n; i++) {
            t[i] = t[i-3] + t[i-2] + t[i-1];
        }
        return t[n];
    }

    /**
     * 打家劫社（一）
     * @param nums
     * @return
     */
    public static int rob(int[] nums) {

        if (nums == null || nums.length == 0){
            return 0;
        }

        int length = nums.length;
        if (length == 1){
            return nums[0];
        }

        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        dp[1] = Math.max(nums[0],nums[1]);

        for (int i = 2;i<length;i++){
            dp[i] = Math.max(dp[i-2]+nums[i],dp[i-1]);
        }

        return dp[length-1];
    }

    /**
     * 打家劫社（二）
     */

    public int rob2(int[] nums) {

        if (nums == null || nums.length == 0){
            return 0;
        }

        int length = nums.length;
        if (length == 1){
            return nums[0];
        }

        if (length == 2){
            return Math.max(nums[0],nums[1]);
        }

        return Math.max(subRob(nums,0,length - 2 ),subRob(nums,0,length - 1));


    }

    //学习怎么把中间的抽出来。
    private int subRob(int[] nums, int start,int end){
        int first = nums[start];
        int second = Math.max(nums[start],nums[start + 1]);

        for (int i = start + 2; i <= end; i++) {
            int temp = second;
            second = Math.max(first + nums[i], second);
            first = temp;
        }

        return second;
    }

}
