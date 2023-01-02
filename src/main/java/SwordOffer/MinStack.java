package SwordOffer;

import java.util.Deque;
import java.util.LinkedList;
import java.util.Stack;

public class MinStack {

    Deque<Integer> A;//原本的栈
    Deque<Integer> B;//存最小的栈

    public MinStack() {
        A = new LinkedList<>();
        B = new LinkedList<>();
        B.push(Integer.MAX_VALUE);
    }

    public void push(int x){
        A.push(x);
        B.push(Math.min(B.peek(),x));
    }

    public void pop(){
        A.pop();
        B.pop();
    }

    public int top(){
        return A.peek();
    }

    public int getMin(){
        return B.peek();
    }
}
