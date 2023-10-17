package classicalQuestion75;

import com.sun.security.auth.NTNumericCredential;

public class Prefix_Add {

    public static void main(String[] args) {
        int[] aa = {-5,1,5,0,-7};
        largestAltitude(aa);

        int[] bb = {1,2,-1};
        System.out.println(pivotIndex(bb));
    }

    public static int pivotIndex(int[] nums) {
        int totalSum = 0;
        int leftSum = 0;

        for (int num :nums){
            totalSum += num;
        }

        for (int i = 0;i<nums.length;++i){
            if (leftSum == totalSum - leftSum - nums[i]){
                return i;
            }

            leftSum += nums[i];
        }

        return -1;
    }

    public static int largestAltitude(int[] gain) {
        int n = gain.length;

        int[] high = new int[n+1];
        high[0] = 0;
        int max = high[0];
        for (int i = 1;i<=n;++i){
            high[i] = gain[i-1] + high[i-1];
            max = Math.max(high[i],max);
        }

        return max;
    }

}
