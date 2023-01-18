package dptest;

import java.util.HashSet;
import java.util.Set;

public class MainTest {

    public static void main(String[] args) {
        System.out.println(fib(7));
    }

    //斐波那契数列
    public static int fib(int n) {
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = 1;

        for (int i = 2; i <= n; ++i) {
            dp[i] = dp[i - 1] + dp[i - 2];
            dp[i] %= 1000000007;//todo 研究下为什么要取模这个数
        }
        return dp[n];
    }


    //青蛙跳台阶
    public int numWays(int n) {
        int a = 1, b = 1, sum;
        for (int i = 0; i < n; i++) {
            sum = (a + b) % 1000000007;
            a = b;
            b = sum;
        }
        return a;

    }

    //股票的最大利润 - 只需遍历一次价格数组记录历史最低点，然后在每天考虑：如果我是在历史最低点买入那么我今天卖出能赚多少？
    public int maxProfit(int prices[]) {
        int minPrice = Integer.MAX_VALUE;//最低的价格
        int maxProfit = 0;               //最大的利润

        for (int i = 0; i < prices.length; i++) {
            if (prices[i] < minPrice) {
                minPrice = prices[i];
            } else if (prices[i] - minPrice > maxProfit) {
                maxProfit = prices[i] - minPrice;
            }
        }
        return maxProfit;
    }

    //连续子数组的最大和
    public int maxSubArray(int[] nums) {
        int pre = 0, maxAns = nums[0];
        for (int x : nums) {
            pre = Math.max(pre + x, x);
            maxAns = Math.max(maxAns, pre);
        }
        return maxAns;
    }

    //礼物的最大价值-把礼物格子转为动态规划格子
    public int maxValue(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;

        for (int j = 1; j < n; j++) { //初始化第一行
            grid[0][j] += grid[0][j - 1];
        }

        for (int i = 1; i < m; i++) {
            grid[i][0] += grid[i - 1][0];
        }

        for (int i = 1; i < m; i++) {
            for (int j = 1; j < n; j++) {
                grid[i][j] += Math.max(grid[i][j - 1], grid[i - 1][j]);
            }
        }
        return grid[m - 1][n - 1];
    }

    //把数字翻译成字符串 - 动态规划 ： 最后一个能翻译  和  最后两个能翻译的 两种方式//todo 没太懂
    public int translateNum(int num) {
        String src = String.valueOf(num);
        int n = src.length();

        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= n; ++i) {
            dp[i] += dp[i - 1];
            String pre = src.substring(i - 2, i);
            if (pre.compareTo("10") >= 0 && pre.compareTo("25") <= 0) {
                dp[i] += dp[i - 2];
            }
        }
        return dp[n];
    }

    //最长不含重复的字符的子字符串
    public int lengthOfLongestSubstring(String s) {
        //哈希集合，记录每个字符串出现过
        Set<Character> occ = new HashSet<>();
        int n = s.length();
        int rk = -1,ans = 0;
        //右指针，初始化为-1 相当于我们在字符串的左边界的左侧，还没有开始移动
        for (int i = 0;i<n;++i){
            if (i != 0){
                //左指针向右移动一格，移除一个字符串
                occ.remove(s.charAt(i-1));
            }

            while (rk+1 < n && !occ.contains(s.charAt(rk+1))){
                //不断地移动右指针
                occ.add(s.charAt(rk + 1));
                ++rk;
            }
            //第i 到 rk 个字符是一个极长的无重复字符子串
            ans = Math.max(ans,rk-i+1);
        }

        return ans;
    }


}
