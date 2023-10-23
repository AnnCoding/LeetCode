package classicalQuestion75;

import java.util.ArrayList;
import java.util.List;

public class Tree_ {


    /**
     * 二叉树的最大深度
     * @param root
     * @return
     */
    public int maxDepth(TreeNode root) {
        if (root == null){
            return 0;
        }else {
            int leftHeight = maxDepth(root.left);
            int rightHeight = maxDepth(root.right);
            return Math.max(leftHeight,rightHeight) + 1;
        }
    }

    /**
     * 叶子节点相似的树
     * @param root1
     * @param root2
     * @return
     */
    public boolean leafSimilar(TreeNode root1, TreeNode root2) {
        //递归实现DFS深度优先遍历二叉树，获得叶子节点的值
        List<Integer> leafValueList1 = new ArrayList<>();
        getLeafNodeValueList(root1,leafValueList1);

        List<Integer> leafValueList2 = new ArrayList<>();
        getLeafNodeValueList(root2,leafValueList2);

        //比较叶子节点
        if (leafValueList1.size() != leafValueList2.size()){
            return false;
        }

        for (int i = 0;i<leafValueList1.size();++i){
            if (!leafValueList1.get(i).equals(leafValueList2.get(i))){
                return false;
            }
        }

        return true;
    }

    private void getLeafNodeValueList(TreeNode node, List<Integer> leafValueList){
        if (node == null){
            return;
        }

        if (node.left == null && node.right == null){
            leafValueList.add(node.val);
            return;
        }

        if(node.left != null){
            getLeafNodeValueList(node.left,leafValueList);
        }

        if (node.right != null){
            getLeafNodeValueList(node.right,leafValueList);
        }
    }

    int res = 0;
    public int goodNodes(TreeNode root) {
        goodNodes(root,root.val);
        return res;
    }

    public void goodNodes(TreeNode root,int max){
        if (root == null){
            return;
        }

        if (root.val >= max){
            res++;
        }

        goodNodes(root.left,Math.max(root.val, max));
        goodNodes(root.right,Math.max(root.val, max));
    }


}
