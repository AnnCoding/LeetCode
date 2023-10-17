package classicalQuestion75;

public class Sliding_Window {

    public static void main(String[] args) {

        String s = "abciiidef";
        int k = 3;

        int[] nums = {1,1,1,0,0,1,1,1,1,1,0,1,1,1,1,1,1,1,1};

        maxVowels(s,3);

        longestOnes2(nums,k);

        longestSubarray(nums);


    }

    /**
     * 滑动窗口的写法：右边加一个左边减一个
     * @param nums
     * @param k
     * @return
     */
    public double findMaxAverage(int[] nums, int k) {

        int len = nums.length;
        int res = 0;

        for(int i=0;i<k;++i){
            res += nums[k];
        }

        int maxSum = res;
        //滑动窗口的写法！
        for (int j=k;j<len;++j){
            res = res - nums[j-k] + nums[j];
            maxSum = Math.max(maxSum,res);
        }

        return 1.0*maxSum / k;
    }

    public static int maxVowels(String s, int k) {
        char[] chars = s.toCharArray();
        int max = 0;
        int tmp = 0;
        StringBuilder sb1 = new StringBuilder();
        for (int i=0;i<k;++i){
            sb1.append(chars[i]);
            if (isVowel(chars[i])) {
                ++tmp;
            }
        }

        max = tmp;
        for (int j =0;j<s.length()-k;++j){
            sb1.delete(j,j+1);
            sb1.append(chars[j+k]);
            if (isVowel(chars[j])){
                tmp -= 1;
            }
            if (isVowel(chars[j+k])){
                tmp += 1;
            }
            max = Math.max(max,tmp);
        }

        return max;
    }


    public static boolean isVowel(char ch) {
        return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u';
    }

    /**
     * 最大连续1的个数（三）
     * @param nums
     * @param k
     * @return
     */
    public static int longestOnes(int[] nums, int k) {
        int len = nums.length;
        int left = 0,lsum = 0,rsum = 0;
        int ans = 0;
        for(int right=0;right<len;++right){
            rsum += 1-nums[right];
            while(lsum < rsum -k){
                lsum += 1-nums[left];
                ++left;
            }
            ans = Math.max(ans,right - left +1);
        }
        return ans;
    }


    /**
     * 给定一个二进制数组 nums 和一个整数 k，如果可以翻转最多 k 个 0 ，则返回 数组中连续 1 的最大个数 。
     * @param nums
     * @param k
     * @return
     */

    /**
     * 重点：题意转换。
     * 把「最多可以把 K 个 0 变成 1，求仅包含 1 的最长子数组的长度」
     * 转换为 「找出一个最长的子数组，该子数组内最多允许有 K 个 0 」。
     *
     * 使用 left 和 right 两个指针，分别指向滑动窗口的左右边界。
     * right 主动右移：right 指针每次移动一步。当 A[right] 为 0，说明滑动窗口内增加了一个 0；
     * left 被动右移：判断此时窗口内 0 的个数，如果超过了 K，则 left 指针被迫右移，直至窗口内的 0 的个数小于等于 K 为止。
     * 滑动窗口长度的最大值就是所求。
     *
     */
    public static int longestOnes2(int[] nums, int k) {
        int n = nums.length;
        int res = 0;
        int left = 0,right = 0;
        int zero = 0;

        while (right < n){
            if (nums[right] == 0){
                zero ++;
            }

            while (zero > k){
                if (nums[left++] == 0){
                    zero --;
                }
            }

            res = Math.max(res,right-left+1);
            right++;
        }
        return res;
    }

    /**
     * 删掉一个元素之后 全为1的最长子数组
     * @param nums
     * @return
     */
    public static int longestSubarray(int[] nums) {
        int n = nums.length;

        int[] pre = new int[n];
        int[] suf = new int[n];

        pre[0] = nums[0]; //正序累积
        for (int i=1;i<n;++i){
            pre[i] = nums[i] != 0? pre[i-1] + 1:0;
        }

        suf[n-1] = nums[n-1];//倒序累积
        for (int i=n-2;i >= 0;--i){
            suf[i] = nums[i] != 0? suf[i+1] + 1:0;
        }

        //遍历第二次的时候；其中i 就是要被消掉的那个空格
        int ans = 0;
        for (int i=0;i<n;++i){
            int preSum = i==0?0:pre[i-1];
            int sufSum = i == n-1?0:suf[i+1];
            ans = Math.max(ans,preSum+sufSum);
        }
        return ans;
    }









}
