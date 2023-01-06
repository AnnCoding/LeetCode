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
