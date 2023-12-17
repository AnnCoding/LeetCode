package classicalQuestion75;

import java.util.*;

public class Graph {

    public static void main(String[] args) {
       List<Integer> l1 = new ArrayList<>();
       l1.add(1);
       l1.add(3);

        List<Integer> l2 = new ArrayList<>();
        l2.add(3);
        l2.add(0);
        l2.add(1);

        List<Integer> l3 = new ArrayList<>();
        l3.add(2);

        List<Integer> l4 = new ArrayList<>();
        l4.add(0);

        List<List<Integer>> rooms = new ArrayList<>();
        rooms.add(l1);
        rooms.add(l2);
        rooms.add(l3);
        rooms.add(l4);

        canVisitAllRoom(rooms);
        canvisitallrooms(rooms);



        int n = 23121;
        int[] A = {2, 4, 9};
        int result = findMaxNumber(n, A);
        System.out.println(result);


    }

    /**
     * 图——深度优先搜索
     */

    //钥匙和房间

    static boolean [] vis;
    static int num;
    public static boolean canVisitAllRoom(List<List<Integer>> rooms){
        int n = rooms.size();
        num = 0;
        vis = new boolean[n];
        dfs(rooms,0);
        return num == n;
    }

    public static void dfs(List<List<Integer>> rooms,int x){
        vis[x] = true; //除了0号房间外的其余所有房间都背锁住了
        num++;
        for (int it : rooms.get(x)){
            if (!vis[it]){
                dfs(rooms, it);
            }
        }
    }

    // 函数的入口
    int[] secondBigNum(int[] arr, int tar) {
        // tar - 1 需要找到次大值
        char[] chars = String.valueOf(tar - 1).toCharArray();
        int[] tarArr = new int[chars.length];
        for (int i = 0; i < chars.length; i++) {
            tarArr[i] = chars[i] - '0';
        }
        // 复用数组, 默认从大到小排序
        reverseSort(arr);
        return findNum(arr, tarArr);
    }

    // 比n小于等于的最大数
    int[] findNum(int[] sortArr, int[] tarArr) {
        // 返回数组
        int[] resArr = new int[tarArr.length];
        // 返回数组下标
        int[] indArr = new int[resArr.length];
        // -1代表非回溯
        Arrays.fill(indArr, -1);
        // 代表之后用最大值填补
        boolean isOK = false;
        // 当前下标
        int i = 0; // 记录当前指针
        while (i < resArr.length) {
            // 如果前面存在小于对应位的数，后面的需要填补最大值
            if (isOK) {
                resArr[i] = sortArr[0];
                i++;
            } else {
                // 第一次访问该位
                int tNum = indArr[i] == -1 ? tarArr[i] : resArr[indArr[i]] - 1;

                int i1 = secondNum(sortArr, tNum);
                if (i1 == -1) {
                    // 没有找到需要向前回溯
                    if (i == 0) {
                        // 如果已到达最前
                        isOK = true;
                        resArr[i] = 0;
                        indArr[i] = -1;
                        i++;
                    } else {
                        // 将当前为设置为未访问
                        indArr[i] = -1;
                        i--;
                    }
                } else if (sortArr[i1] == tNum) {
                    // 刚好等于向下推进
                    resArr[i] = sortArr[i1];
                    indArr[i] = i1;
                    i++;
                    // 如果没有
                } else if (sortArr[i1] < tNum) {
                    // 比当前位小, 后续设位最大
                    resArr[i] = sortArr[i1];
                    indArr[i] = i1;
                    isOK = true;
                    i++;
                }
            }
        }
        return resArr;
    }

    /*
     * 在数组中找到小于等于的数, 数组默认是从大到小排序的
     * 返回找到下标
     * 没找到返回-1
     * */
    int secondNum(int[] arr, int n) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] <= n) {
                return i;
            }
        }
        return -1;
    }

    /*
     * 对一个数组进行大到小的排序
     *
     * */
    void reverseSort(int[] arr) {
        Arrays.sort(arr);
        int i = 0;
        int j = arr.length - 1;
        while (i < j) {
            int t = arr[i];
            arr[i] = arr[j];
            arr[j] = t;
            i++;
            j--;
        }
    }

    /**
     * 图——广度优先搜索
     */

    public static boolean canvisitallrooms(List<List<Integer>> rooms){
        int n = rooms.size();
        int num = 0;

        boolean[] vis = new boolean[n];
        Queue<Integer> que = new LinkedList<Integer>();
        vis[0] = true;
        que.offer(0);
        while (!que.isEmpty()){
            int x = que.poll();
            num++;
            for (int it : rooms.get(x)){
                if (!vis[it]){
                    vis[it] = true;
                    que.offer(it);
                }
            }
        }
        return num == n;
    }

    public static int findMaxNumber(int n, int[] A) {
        char[] nCharArray = Integer.toString(n).toCharArray();
        char[] result = Arrays.copyOf(nCharArray, nCharArray.length);

        for (int i = 0; i < nCharArray.length; i++) {
            int currentDigit = Character.getNumericValue(nCharArray[i]);
            int maxDigit = -1;

            for (int digit : A) {
                if (digit < currentDigit && digit > maxDigit) {
                    maxDigit = digit;
                }
            }

            if (maxDigit != -1) {
                result[i] = Character.forDigit(maxDigit, 10);
            } else {
                // 如果无法找到小于当前位数字的元素，无法构建小于 n 的最大数
                return -1;
            }
        }

        return Integer.parseInt(new String(result));
    }
}
