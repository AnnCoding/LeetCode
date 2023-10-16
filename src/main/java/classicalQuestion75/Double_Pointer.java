package classicalQuestion75;


import java.util.Arrays;

public class Double_Pointer {

    public static void main(String[] args) {
        int[] nums = {2,3,1,5,6};
        maxOperations(nums,9);
    }


    public static int maxOperations(int[] nums, int k) {
        //先对数组做排序
        Arrays.sort(nums);
        int len = nums.length;
        int i = 0,j=len-1;
        int count = 0;
        while (i < j){
            if (nums[i]+nums[j] == k){
                ++count;
                ++i;
                --j;
            }else if (nums[i]+nums[j] < k){
                ++i;
            }else if (nums[i]+nums[j] > k){
                --j;
            }
        }

        return count;
    }


    /**
     * 成最多水的容器
     * @param height
     * @return
     *
     * 时间复杂度 O(N) ： 双指针遍历一次底边宽度 N 。
     * 空间复杂度 O(1) ： 变量 i , j , res 使用常数额外空间。
     *
     *
     */
    public int maxArea(int[] height) {
        int len = height.length;
        int j = len-1;
        int i = 0;

        int res = 0;
        while(i < j){
            res = height[i] < height[j] ?
                    Math.max(res,(j-i) * height[i++]) :
                    Math.max(res,(j-i) * height[j--]) ;
        }
        return res;
    }

    /**
     * 题解：
     * 双指针 i j
     * 水槽高度 h[i] h[j]
     * 水槽面积 S(i,j)=min(h[i],h[j])×(j−i)
     *
     * 在每个状态下，无论长板或短板向中间收窄一格，都会导致水槽 底边宽度 -1 变短：
     *
     * 若向内 移动短板 ，水槽的短板 min(h[i],h[j]) 可能变大，因此下个水槽的面积 可能增大 。
     * 若向内 移动长板 ，水槽的短板 min(h[i],h[j]) 不变或变小，因此下个水槽的面积 一定变小 。
     * 因此，初始化双指针分列水槽左右两端，循环每轮将短板向内移动一格，并更新面积最大值，直到两指针相遇时跳出；即可获得最大面积。
     *
     */


    /**
     *
     * @param s
     * @param t
     * @return
     */
    public static boolean isSubsequence(String s, String t) {
        int j = 0;
        int lens = s.length();
        int lent = t.length();

        char[] chars = s.toCharArray();
        char[] chart = t.toCharArray();

        for(int i=0;i<lent;i++){
            if (j == lens){
                return true;
            }
            if (chart[i] == chars[j]){
                ++j;
            }
        }

        return j == lens;
    }

    /**
     * 移动零
     * @param nums
     */
    public void moveZeroes(int[] nums) {
        int len = nums.length;
        int j = 0;
        for(int i=0;i<len;i++){
            if(nums[i] != 0){
                nums[j++] = nums[i];
            }
        }

        for(j = j;j<len;j++){
            nums[j] = 0;
        }
    }

}
