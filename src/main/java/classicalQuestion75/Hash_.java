package classicalQuestion75;

import java.util.*;
import java.util.stream.Collectors;

public class Hash_ {

    public static void main(String[] args) {

        String world1 = "abcdefhhh";
        String world2 = "hhabchdef";
        closeStrings(world1,world2);

    }

    /**
     * 找出两数组的不同——java8 三行
     * set 可以用来做去重
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public List<List<Integer>> findDifference(int[] nums1, int[] nums2) {

        Set<Integer> set1 = Arrays.stream(nums1).boxed().collect(Collectors.toSet());
        Set<Integer> set2 = Arrays.stream(nums2).boxed().collect(Collectors.toSet());

        return Arrays.asList(set1.stream().filter(x->!set2.contains(x)).collect(Collectors.toList()),
                set2.stream().filter(x->!set1.contains(x)).collect(Collectors.toList()));
    }

    /**
     * 哈希表+集合
     * @param arr
     * @return
     */
    public boolean uniqueOccurrences(int[] arr) {
        Map<Integer,Integer> map = new HashMap<>();
        for (int a : arr){
            map.put(a,map.containsKey(a) ? map.get(a) + 1:1);
        }

        int num = map.values().stream().distinct().collect(Collectors.toList()).size();
        return map.size() == num;
    }

    /**
     *  1、使用time1和time2数组记录word1和word2中每个字母出现的次数
     *  2、然后判断word1和word2的字母种类是否相同，不同则不可能转换成功，因为转换条件不会改变字母种类。
     *  3、对time1和time2进行排序，按位置比较出现的次数是否全部相同，不全部相同则不可能转换成功，因为次数不完全相同的情况下
     *  如果执行操作1，交换任意两个现有字符并不会改变每个字母的出现次数，所以不可能转换成功。
     *  如果执行操作2，也不行，因为排序后次数不完全相同，怎么换都是装换不成功的
     *  如这个例子：time1:0,0,1,1,2,2,3 time2:0,0,1,1,2,3,3 排序后按位置比较，
     *  只要有不同的，就永远不可能通过操作2使得相同，因为time2次数为2的只有1个，无论怎么操作都不可能多出一个次数为2的字母。
     *
     */

    public static boolean closeStrings(String word1, String word2) {
        // 对比长度
        if (word1.length() != word2.length()){
            return false;
        }
        int[] time1 = new int[26];
        int[] time2 = new int[26];

        for (int i=0;i<word1.length();i++){
            time1[word1.charAt(i) - 'a']++;
            time2[word2.charAt(i) - 'a']++;
        }

        //对比每个字母的个数
        for (int i=0;i<26;i++){
            if ((time1[i] > 0 && time2[i] == 0)
            ||(time2[i] > 0 && time2[i] == 0)){
                return false;
            }
        }

        Arrays.sort(time1);
        Arrays.sort(time2);

        for (int i=0;i<26;i++){
            if (time1[i] != time2[i]){
                return false;
            }
        }
        return true;
    }

    /**
     * 相等行列对
     * @param grid
     * @return
     */
    public int equalPairs(int[][] grid) {
        int res = 0,n = grid.length;
        for (int row = 0;row < n;row++){
            for (int col = 0;col <n;col++){
                if (equal(row,col,n,grid)){
                    res++;
                }
            }
        }
        return res;
    }

    public boolean equal(int row,int col,int n, int[][] grid){
        for (int i=0;i<n;++i){
            if (grid[row][i] != grid[i][col]){
                return false;
            }
        }
        return true;
    }

}
