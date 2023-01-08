package SwordOffer;

import java.util.*;

public class Solution {

    class Pair {
        char ch;
        int pos;

        public Pair(char ch, int pos) {
            this.ch = ch;
            this.pos = pos;
        }
    }

    Map<Node,Node> cacheNodeMap = new HashMap<>();

    public static void main(String[] args) {

    }

    //对称的二叉树 - 两种方法：递归 or 迭代
    public boolean isSymmetric(TreeNode root){
        return check(root,root);
    }

    public boolean check(TreeNode p,TreeNode q){
        if (p == null && q == null){
            return true;
        }

        if (p == null || q == null){
            return false;
        }

        return p.val == q.val && check(p.left,q.right) && check(p.right,q.left);
    }


    //二叉树的镜像 - 递归
    public TreeNode mirrorTree(TreeNode root){
        if (root == null){
            return null;
        }

        TreeNode left = mirrorTree(root.left);
        TreeNode right = mirrorTree(root.right);

        root.left = right;
        root.right = left;

        return root;
    }


    //树的子结构 - 递归
    public boolean isSubStructure(TreeNode A,TreeNode B){
        if (A == null || B == null){
            return false;
        }
        //先从根节点判断B是不是A的子结构，如果不是在分别从左右两个子树判断，只要有一个为true 就说明B是A的子结构
        return isSub(A, B) || isSubStructure(A.left,B) || isSubStructure(A.right,B);
    }

    public boolean isSub(TreeNode A,TreeNode B){
        //这里如果B为空 说明B已经访问完了，确定是A的子结构
        if (B == null){
            return true;
        }

        //如果B不为空A为空，或者，这两个节点值不同，说明B不是A的字结构，直接返回false
        if (A == null || A.val != B.val){
            return false;
        }

        //当前节点比较完之后还要继续判断左右子节点
        return isSub(A.left,B.left) && isSub(A.right,B.right);
    }

    //3-从上到下按照之字型打印 - 广度优先遍历 - 按照不同层级左到右或从右到左
    public List<List<Integer>> zigzagLevelOrder(TreeNode root){
        List<List<Integer>> ans = new LinkedList<List<Integer>>();
        if (root == null){
            return ans;
        }
        Queue<TreeNode> nodeQueue = new LinkedList<TreeNode>();
        nodeQueue.offer(root);

        boolean isOrderLeft = true;

        while (!nodeQueue.isEmpty()){
            Deque<Integer> levelList = new LinkedList<Integer>();//双端队列
            int size = nodeQueue.size();
            for (int i = 1;i <= size;i++){
                TreeNode curNode = nodeQueue.poll();
                if (isOrderLeft){
                    levelList.offerLast(curNode.val);
                }else {
                    levelList.offerFirst(curNode.val);
                }

                if (curNode.left != null){
                    nodeQueue.offer(curNode.left);
                }
                if (curNode.right != null){
                    nodeQueue.offer(curNode.right);
                }
            }

            ans.add(new LinkedList<Integer>(levelList));
            isOrderLeft = !isOrderLeft;
        }
        return ans;
    }

    //2-从上到下打印二叉树 - 广度优先搜索 -  按照层级打印
    public List<List<Integer>> levelOrder2(TreeNode root){
        List<List<Integer>> ret = new ArrayList<>();
        if (root == null){
            return ret;
        }

        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        queue.offer(root);

        while (!queue.isEmpty()){
            List<Integer> level = new ArrayList<>();
            int currentLevelSize = queue.size();
            for (int i = 1; i <= currentLevelSize;++i){
                TreeNode node = queue.poll();
                level.add(node.val);

                if (node.left != null){
                    queue.offer(node.left);
                }

                if(node.right != null){
                    queue.offer(node.right);
                }
            }
            ret.add(level);
        }
        return ret;
    }


    //1-从上到下打印二叉树 - 广度优先搜索BFS - 队列
    public int[] levelOrder(TreeNode root){
        if (root == null){
            return new int[0];
        }
        Queue<TreeNode> queue = new LinkedList<TreeNode>(){{
            add(root);
        }};

        ArrayList<Integer> ans = new ArrayList<>();
        while (!queue.isEmpty()){
            TreeNode node = queue.poll();
            ans.add(node.val);//写入数组

            if (node.left != null){
                queue.add(node.left);
            }

            if (node.right != null){
                queue.add(node.right);
            }
        }

        int[] res = new int[ans.size()];
        for (int i = 0;i < ans.size();i++){
            res[i] = ans.get(i);
        }

        return res;
    }

    //第一次只出现一次的字符
    public char firstUniqChar(String s){
        Map<Character,Integer> position = new HashMap<>();
        Queue<Pair> queue = new LinkedList<>();

        int n = s.length();
        for (int i = 0;i<n;++i){
            char ch = s.charAt(i);
            if (!position.containsKey(ch)){
                position.put(ch,i);
                queue.offer(new Pair(ch,i));
            }else {
                position.put(ch,-1);
                while (!queue.isEmpty() && position.get(queue.peek().ch) == -1) {
                    queue.poll();
                }
            }
        }

        return queue.isEmpty() ? ' ':queue.poll().ch;
    }

    //旋转数组的最小数字
    public int minArray(int[] numbers){
        int low = 0;
        int high = numbers.length - 1;
        while (low < high){
            int pivot = low + (high - low)/2;
            if (numbers[pivot] < numbers[high]){
                high = pivot;
            }else if (numbers[pivot] > numbers[high]){
                low = pivot + 1;
            }else {
                high -= 1;
            }
        }

        return numbers[low];
    }

    //寻找0～n-1 中缺失的数字
    public int missingNumber(int[] nums){
        Set<Integer> set = new HashSet<>();
        int n = nums.length + 1;
        for (int i=0;i<nums.length;i++){
            set.add(nums[i]);
        }

        int missing = -1;
        for (int i=0;i<= nums.length;i++){
            if (!set.contains(i)){
                missing = i;
                break;
            }
        }

        return missing;
    }

    //在排序数组中查找数字1
    public int search(int[] nums,int target){
        int leftIdx = binarySearch(nums,target,true);
        int rightIdx = binarySearch(nums,target,false) - 1;

        if (leftIdx <= rightIdx && rightIdx < nums.length && nums[leftIdx] == target && nums[rightIdx] == target){
            return rightIdx - leftIdx + 1;
        }
        return 0;
    }

    public int binarySearch(int[] nums,int target,boolean lower){
        int left = 0;
        int right = nums.length-1;
        int ans = nums.length;

        while (left <= right){
            int mid = (left + right) / 2;
            if (nums[mid] > target || (lower && nums[mid] >= target)){
                right = mid - 1;
                ans = mid;
            }else {
                left = mid + 1;
            }
        }

        return ans;
    }

    //数组中重复的数字
    public int findRepeatNumber(int[] nums){
        if (nums == null){
            return 0;
        }
        int r;
        Set<Integer> s = new HashSet<>();
        int size = 0;
        for (int i=0;i<nums.length;i++){
            if (!s.add(nums[i])){
                return nums[i];
            }
        }
        return 0;
    }

    //左旋转字符串
    public String reverseLeftWords(String s, int n) {
        return s.substring(n,s.length()) + s.substring(0,n);
    }

    //替换空格
    private String replaceSpace(String s){
        int length = s.length();
        char[] array = new char[length * 3];
        int size = 0;

        for (int i = 0;i<length;i++){
            char c = s.charAt(i);
            if (c == ' '){
                array[size++] = '%';
                array[size++] = '2';
                array[size++] = '0';
            }else {
                array[size++] = c;
            }
        }

        String newStr = new String(array,0,size);
        return newStr;
    }

    //从尾到头打印链表 - 用栈
    private static int[] reversePrint(ListNode head){
        Stack<ListNode> stack = new Stack<>();
        ListNode temp = head;
        while (temp != null){
            stack.push(temp);
            temp = temp.next;
        }

        int size = stack.size();
        int[] print = new int[size];

        for (int i = 0;i<size;i++){
            print[i] = stack.pop().val;
        }

        return print;
    }

    //反转链表 - 两个指针
    public ListNode reverseList(ListNode head){
        ListNode prev = null;
        ListNode curr = head;
        while (curr != null){
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }
        return prev;
    }

    //复制随机链表 - 回溯和哈希
    public Node copyRandomList(Node head){
        if (head == null){
            return null;
        }

        if (!cacheNodeMap.containsKey(head)){
            Node headNew = new Node(head.val);
            cacheNodeMap.put(head,headNew);
            headNew.next = copyRandomList(head.next);
            headNew.random = copyRandomList(head.random);
        }

        return cacheNodeMap.get(head);
    }

}
