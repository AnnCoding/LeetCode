package classicalQuestion75;


import java.util.Stack;

public class List_ {

    /**
     * 删除中间节点-【快慢指针】
     * @param head
     * @return
     */
    public ListNode deleteMiddle(ListNode head) {

        if (head == null || head.next == null) {
            return null;
        }

        ListNode fast = head,slow = head, pre = head;
        while (fast != null && fast.next != null){
            pre = slow;
            slow = slow.next;
            fast = fast.next.next;
        }

        pre.next = slow.next;
        return head;
    }

    /**
     * 奇偶链表-
     * 【记住偶链表头；
     * 分为 奇-指针 和 偶-指针；
     * 相互往后面连接
     * 最后 奇数指针链上偶数链表头】
     * @param head
     * @return
     */
    public ListNode oddEvenList(ListNode head) {
        if (head == null){
            return head;
        }

        ListNode evenHead = head.next; //偶位置的链表头
        ListNode odd = head,even = evenHead;//奇偶位置指针

        while(even != null && even.next != null){
            odd.next = even.next;
            odd = odd.next;

            even.next = odd.next;
            even = even.next;
        }

        odd.next = evenHead;
        return head;
    }

    /**
     * 链表反转 -【】
     * @param head
     * @return
     */
    public ListNode reverseList(ListNode head) {
        ListNode cur = head,pre = null;
        while(cur != null){
            ListNode tmp = cur.next; //暂存下后继节点
            cur.next = pre;         //修改next 引用指向
            pre = cur;              //pre 暂存 cur
            cur = tmp;              //cur访问下一个节点
        }
        return pre;
    }

    /**
     * 【链表最大栾生和】- 快慢指针 + 反转链表
     * n-1-i 是栾生下标 = 对半折过来
     *
     * 借助栈
     */
    public int pairSum(ListNode head) {
        int result = 0;
        Stack<Integer> stack = new Stack<>();
        //快慢指针
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null){
            stack.push(slow.val);
            slow = slow.next;
            fast = fast.next.next;
        }

        //出栈更新最大的栾生值
        while (slow != null){
            int left = stack.pop();
            int right = slow.val;

            result = Math.max(result,left+right);
            //慢指针一直往后移
            slow = slow.next;
        }

        return result;
    }

}

