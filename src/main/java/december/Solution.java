package december;

import com.sun.org.apache.bcel.internal.generic.NEW;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author chenjiena
 * @version 1.0
 * @created 2021/12/5.
 */
public class Solution {

    public static void main(String[] args) {
        int nums[] = {2,1,3,0};
        int target = 5;

        ListNode a1 = new ListNode(6);
        ListNode a2 = new ListNode(2,a1);
        ListNode a3 = new ListNode(1,a2);
        ListNode a4 = new ListNode(7,a3);
        ListNode a5 = new ListNode(4,a4);
        ListNode a6 = new ListNode(3,a5);
        ListNode a7 = new ListNode(1,a6);

        deleteMiddle(a3);


//        System.out.println(search(nums,target));
//        System.out.println(findEvenNumbers(nums));

    }

     public static class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

    public static ListNode deleteMiddle(ListNode head) {
        ListNode newHead = new ListNode();
        newHead.next = head;


        ListNode h = newHead;      //走一步
        ListNode q = newHead;    //走两步

        while( q.next != null && q.next.next != null){
            h = h.next;
            q = q.next.next;
        }

        h.next = h.next.next;
        return newHead.next;
    }




    public static int[] findEvenNumbers(int[] digits) {


        Set<Integer> result = new HashSet<>();
        for (int i = 0;i < digits.length;i++){
            if (digits[i] % 2 == 0){
                //是偶数则可以做尾数
                int[] tmpDigits = copyArray(digits,i);
                getResult(tmpDigits,digits[i],result);
            }
        }

        return result.stream().sorted().mapToInt(s->s).toArray();

    }

    public static int[] copyArray(int[] beforeDigits,int index){
        int[] newArray = new int[beforeDigits.length-1];// 新数组，长度为原始数组减去 1

        for(int i=0;i<newArray.length; i++) {
            // 判断元素是否越界
            if (index < 0 || index >= beforeDigits.length) {
                throw new RuntimeException("元素越界... ");
            }
            //
            if(i<index) {
                newArray[i] = beforeDigits[i];
            }
            else {
                newArray[i] = beforeDigits[i+1];
            }
        }

        return newArray;
    }

    public static Set<Integer> getResult(int[] beforeDigits, int tail,Set<Integer> result ){

        for (int i = 0;i<beforeDigits.length;i++){
            if (beforeDigits[i] == 0){
                continue;
            }
            for (int j= 0;j<beforeDigits.length;j++){
                if (j == i){
                    continue;
                }
                Integer tmp = beforeDigits[i]*100 + beforeDigits[j] * 10 + tail;
                result.add(tmp);
            }
        }
        return result;
    }



    public static int search(int[] nums, int target) {
        if(target < nums[0] || target > nums[nums.length - 1]){
            return -1;
        }

        int left = 0;
        int right = nums.length - 1;

        while(left <= right){
            int mid = (left+right) / 2;
            if(target == nums[mid]){
                return mid;
            }else if(target > nums[mid]){
                left =  mid + 1;
            }else if(target < nums[mid]){
                right = mid -1;
            }
        }

        return -1;

    }
}




