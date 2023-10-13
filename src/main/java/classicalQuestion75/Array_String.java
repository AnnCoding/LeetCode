package classicalQuestion75;


import java.util.*;

public class Array_String {


    public static void main(String[] args) {
        int a = 64,b=168;
        gcd2(a,b);

        String str1 = "abccababccab";
        String str2 = "abc";
        String result = gcdOfStrings2(str1,str2);

        int[] flowers = {1,0,0,0,0,0,1,1,1,1,1};
        int n = 2;
        canPlaceFlowers(flowers,n);

        String s = "    h  hhdi  ididi  dhidhi  dijdij ";
        String res = reverseWords(s);

        int[] testFet = {2,1,5,4,6,3};
        boolean fet = increasingTriplet2(testFet);

        char[] chars = {'a','a','b','b'};
        int finalres = compress(chars);

    }

    /**
     * 交替合并字符串
     * @param word1
     * @param word2
     * @return
     */
    public String mergeAlternately(String word1, String word2) {
        int m = word1.length(),n = word2.length();
        int i = 0, j = 0;

        StringBuilder ans = new StringBuilder();
        while (i < m || j < n){
            if (i < m){
                ans.append(word1.charAt(i));
                ++i;
            }
            if (j < n){
                ans.append(word2.charAt(j));
                ++j;
            }
        }
        return ans.toString();
    }

    /**
     * 时间复杂度：O(m+n)O(m+n)O(m+n)，其中 m 和 n 分别是字符串 word1\ word2 的长度。
     * 空间复杂度：O(1) 或 O(m+n)。如果使用的语言支持可修改的字符串，那么空间复杂度为 O(1)，否则为 O(m+n)。注意这里不计入返回值需要的空间。
     */

    /**
     * 字符串的最大公因子-数学方式
     * @param str1
     * @param str2
     * @return
     */
    public static String gcdOfStrings(String str1, String str2) {

        if (!str1.concat(str2).equals(str2.concat(str1))){
            return "";
        }
        return str1.substring(0, gcd1(str1.length(),str2.length()));
    }
    //辗转相除法
    public static int gcd2(int a,int b){
        int remainder = a%b;
        while (remainder != 0){
            a = b;
            b = remainder;
            remainder = a % b;
        }

        return b;
    }

    //辗转相除法
    public static int gcd1(int a, int b){
        while (b != 0){
            int tmp = b;
            b = a%b;
            a = tmp;
        }

        return a;
    }

    /**
     * 时间复杂度：O(n) ，字符串拼接比较是否相等需要 O(n) 的时间复杂度，求两个字符串长度的最大公约数需要 O(log n) 的时间复杂度，所以总时间复杂度为 O(n+logn)=O(n) 。
     * 空间复杂度：O(n) ，程序运行时建立了中间变量用来存储 str1 与 str2 的相加结果。
     *
     */

    /**
     * 枚举法
     * @param str1
     * @param str2
     * @return
     */
    public static String gcdOfStrings2(String str1, String str2) {
        int len1 = str1.length(),len2 = str2.length();
        String T = str1.substring(0,gcd1(len1,len2));
        if (check(T,str1) && check(T,str2)){
            return T;
        }
        return "";
    }

    //枚举校验！
    public static boolean check(String t,String s){
        int lenx = s.length() / t.length();
        StringBuilder ans = new StringBuilder();
        for (int i = 1;i<=lenx;++i){
            ans.append(t);
        }

        return ans.toString().equals(s);
    }


    /**
     * 拥有最多糖果的孩子
     * @param candies
     * @param extraCandies
     * @return
     */
    public List<Boolean> kidsWithCandies(int[] candies, int extraCandies) {
        int max = candies[0];
        for(int i = 1;i< candies.length;++i){
            if(candies[i] > max){
                max = candies[i];
            }
        }

        List<Boolean> res = new ArrayList();
        for(int i = 0;i<candies.length;++i){
            if(candies[i]+extraCandies > max){
                res.add(true);
            }else{
                res.add(false);
            }
        }
        return res;
    }

    /**
     * 时间复杂度是O(n)，空间复杂度也是O(n)。
     */

    /**
     * 优化了一下空间复杂度！
     * @param candies
     * @param extraCandies
     * @return
     */
    public List<Boolean> kidsWithCandies2(int[] candies, int extraCandies) {
        int n = candies.length;
        int maxCandies = 0;
        for (int i = 0;i<n;++i){
            maxCandies = Math.max(maxCandies,candies[i]);
        }

        List<Boolean> ret = new ArrayList<>();
        for (int i = 0;i<n;++i){
            ret.add(candies[i]+extraCandies >= maxCandies);
        }
        return ret;
    }


    /**
     * 种花问题——跳格子；
     * @param flowerbed
     * @param n
     * @return
     * 时间复杂度是O(n)，空间复杂度是O(1)。
     */
    public static boolean canPlaceFlowers(int[] flowerbed, int n) {
        int len = flowerbed.length;
        int i = 0;
        while(i < len){
            if(flowerbed[i] == 1){ //有花，跳过1
                i=i+2;
            } else if (i == flowerbed.length-1 || flowerbed[i+1] == 0){ //下一个没花，种上，跳过1
                n--;
                i=i+2;
            } else { //下一个有花，跳过2
                i+=3;
            }
        }
        return n == 0;
    }

    /**
     * 反转字符串中的元音字母——双指针！
     * @param s
     * @return
     *
     * 时间复杂度是O(n)，空间复杂度是O(n)。
     */
    public String reverseVowels(String s) {
        int n = s.length();
        char[] arr = s.toCharArray();
        int i=0, j=n-1;

        while (i < j){
            while (i < n && !isVowel(arr[i])){
                ++i;
            }
            while (j > 0 && !isVowel(arr[j])){
                --j;
            }

            if (i<j){
                swap(arr,i,j);
                ++i;
                --j;
            }
        }
        return new String(arr);
    }

    public boolean isVowel(char ch){
        return "aeiouAEIOU".indexOf(ch)  >= 0;
    }

    public void swap(char[] arr,int i, int j){
        char temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    /**
     * 翻转字符串里的单词-
     * @param s
     * @return
     *
     * 时间复杂度是O(n)，空间复杂度是O(n)
     */

    public static String reverseWords(String s) {

        s = s.trim();
        List<String> wordList = Arrays.asList(s.split("\\s+"));
        Collections.reverse(wordList);
        return String.join(" ",wordList);
    }

    /**
     * 除自身以外数组的乘积
     * @param nums
     * @return
     */
    public int[] productExceptSelf(int[] nums) {
        int len =nums.length;
        int all = 1;
        int zeroCount = 0;
        for(int i=0;i<len;++i){
            if(nums[i] != 0){
                all = all * nums[i];
            }else{
                ++zeroCount;
            }
        }

        for(int i=0;i<len;++i){
            if(zeroCount > 1){
                nums[i] = 0;
            }else if(nums[i] != 0 && zeroCount == 0){
                nums[i] = all / nums[i];
            }else if(nums[i] == 0 && zeroCount == 1){
                nums[i] = all;
            }else if(nums[i] != 0 && zeroCount == 1){
                nums[i] = 0;
            }
        }
        return nums;
    }

    /**
     * 递增的三元子序列-双向遍历法
     * @param nums
     * @return
     *
     * 时间复杂度是O(n)，空间复杂度是O(n)
     */
    public static boolean increasingTriplet(int[] nums) {
        int n = nums.length;
        if (n < 3){
            return false;
        }

        int[] leftMin = new int[n];
        leftMin[0] = nums[0];
        for (int i = 1;i<n;++i){
            leftMin[i] = Math.min(leftMin[i-1],nums[i]);
        }

        int[] rightMax = new int[n];
        rightMax[n-1] = nums[n-1];
        for (int i = n-2; i>= 0; i--){
            rightMax[i] = Math.max(rightMax[i+1],nums[i]);
        }

        for (int i = 1;i<n-1;i++){
            if (nums[i] > leftMin[i-1] && nums[i] < rightMax[i+1]){
                return true;
            }
        }
        return false;
    }

    /**
     * 贪心算法:上述做法的贪心思想是：为了找到递增的三元子序列，first 和 second 应该尽可能地小，此时找到递增的三元子序列的可能性更大。
     *
     * @param nums
     * @return
     *
     *  时间复杂度是O(n)，空间复杂度是O(1)
     */
    public static boolean increasingTriplet2(int[] nums) {
        int n = nums.length;
        if (n < 3){
            return false;
        }

        int first = nums[0], second = Integer.MAX_VALUE;
        for (int i=1;i<n;i++){
            int num = nums[i];
            if (num > second){
                return true;
            } else if (num > first){
                second = num;
            } else {
                first = num;
            }
        }

        return false;
    }

    public static int compress(char[] chars) {
        int n = chars.length;
        int write = 0,left = 0;
        for (int read = 0; read < n ; read ++){
            if (read == n-1 || chars[read] != chars[read + 1]){
                chars[write++] = chars[read];
                int num = read - left + 1;
                if(num > 1){
                    int anchor = write;
                    while (num > 0){
                        chars[write++] = (char) (num % 10 + '0');
                        num /= 10;
                    }
                    reverse(chars,anchor,write-1);
                }

                left = read + 1;
            }
        }
        return write;
    }

    public static void reverse(char[] chars, int left, int right){
        while (left < right){
            char temp = chars[left];
            chars[left] = chars[right];
            chars[right] = temp;
            left++;
            right--;
        }
    }





}
