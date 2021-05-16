package july;

import com.sun.deploy.util.StringUtils;
import sun.swing.StringUIClientPropertyKey;

import java.util.*;

public class test {

    public static void main(String[] args) {

//        int[] arr = {37,12,28,9,100,56,80,5,12};
//        printArr(arrayRankTransform(arr));

//        String testString = "aba";
//        System.out.println(breakPalindrome(testString));

        int[][] mat = {{37,98,82,45,42}};
        printArrs(mat);
        printArrs(diagonalSort(mat));

    }

    public static void printArr(int[] arr) {
        System.out.print("print: ");
        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }

    public static void printArrs(int[][] arrs) {
        System.out.print("print:");
        for (int i = 0; i < arrs.length; i++) {
            System.out.println();
            for (int j = 0;j<arrs[i].length;j++){
                System.out.print(arrs[i][j] + " ");
            }
        }
    }




    /**
     * 给你一个 m * n 的整数矩阵 mat ，请你将同一条对角线上的元素（从左上到右下）按升序排序后，返回排好序的矩阵。
     * @param mat
     * @return
     */

    public static int[][] diagonalSort(int[][] mat) {
        List<List<Integer>> numLists = new ArrayList<List<Integer>>();
        int rows = mat.length;
        int cols = mat[0].length;

        //先取出来排序
        //右上
        for (int i = rows - 1; i >= 0; i--) {
            List<Integer> numList = new ArrayList<Integer>();
            int r = i;
            int c = 0;

            while (r <= rows - 1 && c <= cols - 1) {
                numList.add(mat[r++][c++]);
            }
            Collections.sort(numList);
            numLists.add(numList);
        }

        //左下
        for (int j = 1; j < cols; j++) {
            List<Integer> numList = new ArrayList<Integer>();
            int r = 0;
            int c = j;
            while (r <= rows-1 && c <= cols - 1) {
                numList.add(mat[r++][c++]);
            }
            Collections.sort(numList);
            numLists.add(numList);
        }

        //再重新填回去
        int index1 = 0;
        //右上
        for (int i = rows - 1; i >= 0; i--) {
            int r = i;
            int c = 0;

            List<Integer> numList = numLists.get(index1++);
            int index2 = 0;
            while (r <= rows - 1 && c <= cols - 1) {
                mat[r++][c++] = numList.get(index2++);
            }
        }

        //左下
        for (int j = 1; j < cols; j++) {
            int r = 0;
            int c = j;

            List<Integer> numList = numLists.get(index1++);
            int index2 = 0;
            while (r <= rows - 1 && c <= cols - 1) {
                mat[r++][c++] = numList.get(index2++);
            }
        }

        return mat;
    }

    /**
     * 给你一个回文字符串 palindrome ，请你将其中 一个 字符用任意小写英文字母替换，使得结果字符串的字典序最小，且 不是 回文串。
     * <p>
     * 请你返回结果字符串。如果无法做到，则返回一个空串。
     *
     * @param palindrome
     * @return
     */

    public static String breakPalindrome(String palindrome) {

        if (palindrome == null) {
            return null;
        }
        int b = palindrome.length() / 2;
        String substring = palindrome.substring(0, b);
        StringBuilder builder = new StringBuilder(palindrome);
        boolean allA = false;
        for (int i = 0; i < substring.length(); i++) {
            if (substring.charAt(i) != 'a') {
                builder.replace(i, i + 1, "a");
                return builder.toString();
            }
            allA = true;
        }
        if (allA && palindrome.length() >= 2) {
            builder.replace(palindrome.length() - 1, palindrome.length(), "b");
            return builder.toString();
        }
        return "";
    }


    /**
     * 给你一个整数数组 arr ，请你将数组中的每个元素替换为它们排序后的序号。
     * <p>
     * 序号代表了一个元素有多大。序号编号的规则如下：
     * <p>
     * 序号从 1 开始编号。
     * 一个元素越大，那么序号越大。如果两个元素相等，那么它们的序号相同。
     * 每个数字的序号都应该尽可能地小。
     *
     * @param arr
     * @return
     */

    public static int[] arrayRankTransform(int[] arr) {
        if (arr.length == 0) {
            return new int[0];
        }
        int[] copyIntArr = new int[arr.length];
        for (int i = 0; i < arr.length; i++) {
            copyIntArr[i] = arr[i];
        }
        Arrays.sort(arr);
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        int b = 0;
        for (int i = 0; i < arr.length; i++) {
            if (map.get(Integer.valueOf(arr[i])) == null) {
                map.put(Integer.valueOf(arr[i]), ++b);
            }
        }
        int[] result = new int[arr.length];
        for (int i = 0; i < copyIntArr.length; i++) {
            result[i] = map.get(copyIntArr[i]);
        }
        return result;
    }


}
