package SwordOffer;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class Solution {

    Map<Node,Node> cacheNodeMap = new HashMap<>();

    public static void main(String[] args) {

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
