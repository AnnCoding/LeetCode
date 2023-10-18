package classicalQuestion75;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

public class Queue_ {

    public static void main(String[] args) {
        RecentCounter CC = new RecentCounter();
        System.out.println(CC.ping(1));
        System.out.println(CC.ping(100));
        System.out.println(CC.ping(3001));
        System.out.println(CC.ping(3002));


        String ss = "RDDRRDD";
        String res = predictPartyVictory(ss);
        System.out.println(res);
    }


    /**
     * Dota2 参议院
     *
     * 两个阵营：radiant 和 dire
     * 基于轮为过程的投票进行，每轮中可以使用两项中的一项：
     * （1）禁止一名参议院的权利
     * （2）宣布胜利-都是同一个阵营的
     * @param senate
     * @return
     */
    public static String predictPartyVictory(String senate) {
        Queue<Character> queue = new LinkedList<>();
        int len = senate.length();

        int radiant = 0;
        int dire = 0;
        //杀死r阵营的次数
        int killR = 0;
        //杀死d阵营的次数
        int killD = 0;

        for (int i = 0; i < len;++i){
            char c = senate.charAt(i);
            if (c == 'R'){
                radiant++;
            }else {
                dire++;
            }
            queue.add(c);
        }

        //都不同时为0的时候才继续-【基于轮投票】
        while (radiant != 0 && dire != 0){
            char c = queue.remove();
            if (c == 'R'){
                if (killR == 0){
                    queue.add('R');
                    killD++;
                }else {
                    killR--;
                    radiant--;
                }
            }

            if (c == 'D'){
                if (killD == 0){
                    queue.add('D');
                    killR++;
                }else {
                    killD--;
                    dire--;
                }
            }
        }
        return radiant != 0? "Radiant" :"Dire";
    }


}




/**
 * 写一个 RecentCounter 类来计算特定时间范围内最近的请求。
 *
 * 请你实现 RecentCounter 类：
 *
 * RecentCounter() 初始化计数器，请求数为 0 。
 * int ping(int t) 在时间 t 添加一个新请求，其中 t 表示以毫秒为单位的某个时间，
 * 并返回过去 3000 毫秒内发生的所有请求数（包括新请求）。
 *
 * 确切地说，返回在 [t-3000, t] 内发生的请求数。
 * 保证 每次对 ping 的调用都使用比之前更大的 t 值。
 */


class RecentCounter {

    Queue<Integer> queue;
    public RecentCounter() {
        queue = new ArrayDeque<Integer>();
    }

    public int ping(int t) {
        queue.offer(t);
        while (queue.peek() < t-3000){
            queue.poll();
        }
        return queue.size();
    }
}
