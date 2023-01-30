package SwordOffer;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

public class FindAndBacktracking {

    public static void main(String[] args) {

    }





    List<List<Integer>> ret = new LinkedList<List<Integer>>();
    Deque<Integer> path = new LinkedList<Integer>();
    public List<List<Integer>> pathSum(TreeNode root, int target) {
        //广度优先算法
        dfs(root, target);
        return ret;
    }

    public void dfs(TreeNode root, int target) {
        if (root == null) {
            return;
        }
        path.offerLast(root.val);
        target -= root.val;
        if (root.left == null && root.right == null && target == 0) {
            ret.add(new LinkedList<Integer>(path));
        }
        dfs(root.left, target);
        dfs(root.right, target);
        path.pollLast();
    }


    //机器人运动的范围
    public int movingCount(int m, int n, int k) {
        //标记当前格子是否被访问过
        boolean[][] visited = new boolean[m][n];
        return dfs(visited,m,n,k,0,0);

    }

    int dfs(boolean[][] visited, int m, int n, int k, int i, int j) {
        //递归终止条件
        if (i >= m || j >= n || bitSum(i) + bitSum(j) > k || visited[i][j])
            return 0;

        visited[i][j] = true;

        return 1 + dfs(visited, m, n, k, i + 1, j) + dfs(visited, m, n, k, i, j + 1);
    }

    int bitSum(int x) {
        int sum = 0;
        while (x != 0) {
            sum += x % 10;
            x /= 10;
        }
        return sum;
    }


    //矩阵中的路径
    public boolean exit(char[][] board, String word) {
        char[] words = word.toCharArray();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (dfs(board, words, i, j, 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    boolean dfs(char[][] board, char[] word, int i, int j, int index) {
        //边界的判断，如果越界直接返回false。index 表示的是查找到字符串word的第几个字符，
        //如果这个字符不等于board[i][j],说明验证这个坐标路径是走不通的，直接返回false

        if (i >= board.length || i < 0 || j >= board[0].length || j < 0 || board[i][j] != word[index]) {
            return false;
        }

        //如果word的每个字符都查完了，直接返回true
        if (index == word.length - 1) {
            return true;
        }

        //防止分支污染，把board 数组复制一份
        char[][] newArray = copyArray(board);
        //把newArray[i][j]置为特殊符号，表示已经使用过了（注意：word中不能包含'.'）
        newArray[i][j] = '.';
        //从当前上下左右的四个方向来查找
        boolean res = dfs(newArray, word, i + 1, j, index + 1) || dfs(newArray, word, i - 1, j, index + 1) || dfs(newArray, word, i, j + 1, index + 1) || dfs(newArray, word, i, j - 1, index + 1);
        return res;
    }

    //复制一份新的数组
    char[][] copyArray(char[][] word) {
        char[][] newArray = new char[word.length][word[0].length];
        for (int i = 0; i < word.length; i++) {
            for (int j = 0; j < word[0].length; j++) {
                newArray[i][j] = word[i][j];
            }
        }

        return newArray;
    }
}
