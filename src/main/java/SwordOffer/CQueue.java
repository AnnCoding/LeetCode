package SwordOffer;

import java.util.Deque;
import java.util.Stack;

public class CQueue {

    private Deque<Integer> inStack;
    private Deque<Integer> outStack;

    public CQueue(Deque<Integer> inStack, Deque<Integer> outStack) {
        this.inStack = inStack;
        this.outStack = outStack;
    }

    public void appendTail(int value){
        inStack.push(value);
    }

    public int deleteHead(){
        if (outStack.isEmpty()){
            if (inStack.isEmpty()) {
                return -1;
            }
            in2out();
        }
        return outStack.pop();
    }

    private void in2out(){
        while (!inStack.isEmpty()){
            outStack.push(inStack.pop());
        }
    }


}
