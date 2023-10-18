package classicalQuestion75;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class Stack_ {

    public static void main(String[] args) {
        String ss = "*lllee***";
        removeStars(ss);

        int[] aa = {5,10,-5};
        asteroidCollision(aa);

        String string = "3[a]2[bc]";
        decodeString(string);

    }



    /**
     * 字符串解码——辅助栈法
     * 四种情况：[ ] 数字 字母
     * @param s
     * @return
     */
    public static String decodeString(String s) {
        StringBuilder res = new StringBuilder();
        int multi = 0;

        LinkedList<Integer> stack_multi = new LinkedList<>();
        LinkedList<String> stack_res = new LinkedList<>();

        for (Character c : s.toCharArray()){
            if (c == '['){
                stack_multi.addLast(multi);
                stack_res.addLast(res.toString());
                multi = 0;
                res = new StringBuilder();
            }
            else if (c == ']'){
                StringBuilder tmp = new StringBuilder();
                int cur_multi = stack_multi.removeLast();
                for (int i=0;i<cur_multi;++i){
                    tmp.append(res);
                }
                res = new StringBuilder(stack_res.removeLast() + tmp);
            }
            else if (c >= '0' && c <= '9'){
                multi = multi*10+Integer.parseInt(c+"");
            }
            else res.append(c);
        }

        return res.toString();
    }

    /**
     * 小行星碰撞——栈
     * @param asteroids
     * @return
     */
    public static int[] asteroidCollision(int[] asteroids) {
        Deque<Integer> stack = new ArrayDeque<Integer>();
        for (int aster:asteroids){
            boolean alive = true;
            while (alive && aster < 0 && !stack.isEmpty() && stack.peek() > 0){
                alive = stack.peek() < -aster;
                if (stack.peek() <= -aster){
                    stack.pop();
                }
            }

            if (alive){
                stack.push(aster);
            }
        }

        int size = stack.size();
        int[] ans = new int[size];
        for (int i=size-1;i>=0;i--){
            ans[i] = stack.pop();
        }

        return ans;
    }


    /**
     * 从字符串中移除*号
     * @param s
     * @return
     */
    public static String removeStars(String s) {

        Stack<Character> deque = new Stack<>();
        int n = s.length();
        deque.push(s.charAt(0));

        int i = 1;
        while (i<n){
            if (s.charAt(i) == '*' && !s.isEmpty()){
                if (deque.peek() == '*'){
                    deque.push(s.charAt(i));
                }else {
                    deque.pop();
                }
            }else {
                deque.push(s.charAt(i));
            }
            i++;
        }

        String str = "";
        while (!deque.isEmpty()){
            str = deque.pop() + str;
        }
        return str;
    }


}
