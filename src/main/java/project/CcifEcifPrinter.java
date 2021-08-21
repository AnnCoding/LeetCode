package project;

/**
 * @author chenjiena
 * @version 1.0
 * @created 2021/5/24.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class CcifEcifPrinter {



    public  static List<List<String>> classify(Map<String, List<String>> ccifMap) {
        // ecif 对应 ccif集合 的映射
        Map<String, List<String>> ecifMap = new HashMap<>();
        for (Map.Entry<String, List<String>> entry : ccifMap.entrySet()) {
            for (String value : entry.getValue()) {
                List<String> list = ecifMap.getOrDefault(value, new ArrayList<>());
                list.add(entry.getKey());
                ecifMap.put(value, list);
            }
        }

        List<List<String>> ans = new ArrayList<List<String>>();
        // 访问集合，只要ccif或者ecif访问过，就可以不再访问
        Set<String> ccifVisitSet = new HashSet<>();
        Set<String> ecifVisitSet = new HashSet<>();
        // 根据 ccif 广度遍历对应的 ecif
        // 再根据 ecif 对应ccif下的所有ecif放入队列中
        // 只要队列为空，相同的类别完成遍历
        for (String ccif : ccifMap.keySet()) {
            // 避免tag重复遍历
            if (!ccifVisitSet.contains(ccif)) {
                // 初始化队列，第一个ccif下的所有ecif都设置访问，放入结果及中
                ccifVisitSet.add(ccif);
                List<String> list = ccifMap.get(ccif);
                Queue<String> queue = new LinkedList<>();
                List<String> numList = new ArrayList<>();

                for (String i : list) {
                    queue.offer(i);
                    ecifVisitSet.add(i);
                    numList.add(i);
                }
                // 队列不为空，继续将ecif对应的ccif进行遍历
                // 然后ccif下的所有ecif继续放入队列中
                while (!queue.isEmpty()) {
                    String num = queue.poll();
                    List<String> ccifs = ecifMap.get(num);
                    for (String temCcif : ccifs) {
                        // 防止tag重复遍历
                        if (!ccifVisitSet.contains(temCcif)) {
                            ccifVisitSet.add(temCcif);
                            List<String> tagValues = ccifMap.get(temCcif);
                            for (String tagValue : tagValues) {
                                // 防止value重复遍历
                                if (!ecifVisitSet.contains(tagValue)) {
                                    queue.add(tagValue);
                                    ecifVisitSet.add(tagValue);
                                    numList.add(tagValue);
                                }
                            }
                        }
                    }
                }

                ans.add(numList);
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        Map<String, List<String>> map = new HashMap<String, List<String>>();
        List<String> list1 = new ArrayList<>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("4");
        List<String> list2 = new ArrayList<>();
        list2.add("3");
        list2.add("9");
        list2.add("4");
        list2.add("1");
        List<String> list3 = new ArrayList<>();
        list3.add("5");
        list3.add("6");
        list3.add("7");
        list3.add("8");
        List<String> list4 = new ArrayList<>();
        list4.add("4");
        list4.add("10");
        list4.add("11");
        list4.add("12");
        
        map.put("A", list1);
        map.put("B", list2);
        map.put("c", list3);
        map.put("d", list4);
        List<List<String>> classify = classify(map);

        for (List<String> list : classify) {
            for (String i : list) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
    }



}
