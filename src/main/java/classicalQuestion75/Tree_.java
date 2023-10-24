package classicalQuestion75;

import java.util.*;

public class Tree_ {


    public static void main(String[] args) {
        TreeNode head = new TreeNode(10);
        TreeNode head1 = new TreeNode(5);
        TreeNode head2 = new TreeNode(-3);
        TreeNode head3 = new TreeNode(3);
        TreeNode head4 = new TreeNode(2);
        TreeNode head5 = new TreeNode(11);
        TreeNode head6 = new TreeNode(3);
        TreeNode head7 = new TreeNode(-2);
        TreeNode head8 = new TreeNode(1);

        head.left = head1;
        head.right = head2;
        head1.left = head3;
        head1.right = head4;
        head2.left = head5;
        head2.right = head5;
        head3.left= head6;
        head3.right = head7;
        head4.left = head8;

        pathSum(head,8);
        rightSideView(head);
        maxLevelSum(head);
    }


    /**
     * 二叉树的最大深度
     * @param root
     * @return
     */
    public static int maxDepth(TreeNode root) {
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
    public static boolean leafSimilar(TreeNode root1, TreeNode root2) {
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

    private static void getLeafNodeValueList(TreeNode node, List<Integer> leafValueList){
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

    /**
     * 路径总和-【前缀和】
     * @param root
     * @param targetSum
     * @return
     */
    public static int pathSum(TreeNode root, int targetSum) {
        // key是前缀和, value是大小为key的前缀和出现的次数
        Map<Long, Integer> prefixSumCount = new HashMap<>();
        // 前缀和为0的一条路径
        prefixSumCount.put(0L, 1);
        // 前缀和的递归回溯思路
        return recursionPathSum(root, prefixSumCount, targetSum,0L);
    }

    public static int recursionPathSum(TreeNode node, Map<Long,Integer> prefixSumCount, int target, long currSum){

        // 1.递归终止条件
        if (node == null) {
            return 0;
        }
        // 2.本层要做的事情
        int res = 0;
        // 当前路径上的和
        currSum += node.val;

        //---核心代码
        // 看看root到当前节点这条路上是否存在节点前缀和加target为currSum的路径
        // 当前节点->root节点反推，有且仅有一条路径，如果此前有和为currSum-target,而当前的和又为currSum,两者的差就肯定为target了
        // currSum-target相当于找路径的起点，起点的sum+target=currSum，当前点到起点的距离就是target
        res += prefixSumCount.getOrDefault(currSum - target, 0);//todo 这个很重要
        // 更新路径上当前节点前缀和的个数
        prefixSumCount.put(currSum, prefixSumCount.getOrDefault(currSum, 0) + 1);
        //---核心代码

        // 3.进入下一层
        res += recursionPathSum(node.left, prefixSumCount, target, currSum);
        res += recursionPathSum(node.right, prefixSumCount, target, currSum);

        // 4.回到本层，恢复状态，去除当前节点的前缀和数量
        prefixSumCount.put(currSum, prefixSumCount.get(currSum) - 1);
        return res;
    }


    /**
     * 二叉树中的最长交错路径-【无法理解背一下好了】
     * @param root
     * @return
     */
    int ans;
    public int longestZigZag(TreeNode root) {
        ans = 0;
        f(root,0,0);
        return ans - 1;
    }

    public void f (TreeNode root,int l,int r){
        if (root == null){
            ans = Math.max(ans,Math.max(l,r));
            return;
        }

        f(root.left,r+1,0);
        f(root.right,0,l+1);
    }


    /**
     * 二叉树中最近的公共祖先
     * @param root
     * @param p
     * @param q
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        return travelTree(root, p, q);
    }

    public TreeNode travelTree(TreeNode root, TreeNode p, TreeNode q){
        if(root == null || root == p ||root == q){
            return root;
        }

        TreeNode left = travelTree(root.left,p,q);
        TreeNode right = travelTree(root.right,p,q);

        if(left != null && right != null){
            return root;
        }
        if (left == null){
            return right;
        }

        return left;
    }

    /**
     * 二叉树的右视图 - 【广度优先搜索】
     * 对二叉树进行层次遍历，每层最右边的节点一定是最后后遍历到的
     * @param root
     * @return
     */
    public static List<Integer> rightSideView(TreeNode root) {
        Map<Integer,Integer> rightmostValueAtDepth = new HashMap<>();
        int max_depth = -1;

        Queue<TreeNode> nodeQueue = new LinkedList<TreeNode>();
        Queue<Integer> depthQueue = new LinkedList<Integer>();

        nodeQueue.add(root);
        depthQueue.add(0);

        while (!nodeQueue.isEmpty()){
            TreeNode node = nodeQueue.remove();
            int depth = depthQueue.remove();

            if (node != null){
                //维护二叉树的最大深度
                max_depth = Math.max(max_depth,depth);

                //由于每一层最后一个访问到的节点才是我们想要的答案，因此不断更新对应深度的信息即可
                rightmostValueAtDepth.put(depth,node.val);

                nodeQueue.add(node.left);
                nodeQueue.add(node.right);

                depthQueue.add(depth+1);
                depthQueue.add(depth+1);
            }
        }

        List<Integer> rightView = new ArrayList<Integer>();
        for (int i = 0;i <= max_depth;i++){
            rightView.add(rightmostValueAtDepth.get(i));
        }

        return rightView;
    }


    /**
     * 最大层内元素和-【广度优先搜索算法】
     * @param root
     * @return
     */
    public static int maxLevelSum(TreeNode root) {
        int ans = 1, maxSum = root.val;
        List<TreeNode> q = new ArrayList<>();

        q.add(root);
        for (int level = 1;!q.isEmpty();++level){
            List<TreeNode> nq = new ArrayList<>(); //存放每层的节点数
            int sum = 0;                           //存放每层节点和

            for (TreeNode node:q){                 //存放上一层的节点
                sum += node.val;                   //把上一层的节点值都加起来
                //左右两边存放节点数！
                if (node.left != null){
                    nq.add(node.left);
                }
                if(node.right != null){
                    nq.add(node.right);
                }
            }

            if (sum > maxSum){
                maxSum = sum;
                ans = level;
            }
            q = nq;
        }
        return ans;
    }

    /**
     * 二叉搜索数中的搜索-【递归】
     * 二叉搜索树的性质：
     * （1）左子树所有节点的元素值小于根节点元素值
     * （2）右子树所有节点的元素值大于根节点元素值
     *
     * root为空则返回空节点
     * val = root.val 则返回root
     * val < root.val 递归左子树
     * val > root.val 递归右子树
     *
     * @param root
     * @param val
     * @return
     */
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null){
            return null;
        }

        if(val == root.val){
            return root;
        }

        return searchBST(val< root.val ? root.left:root.right,val);
    }


    /**
     * 删除二叉搜索树中的节点
     * @param root
     * @param key
     * @return
     */
    public TreeNode deleteNode(TreeNode root, int key) {

        // 如果节点为空，直接返回
        if(root == null){
            return null;
        }
        // 如果要删除的节点 key 比 root 小 -> 往左边找
        if(root.val > key){
            root.left = deleteNode(root.left, key);
        }
        // 如果要删除的节点 key 比 root 大 -> 往右边找
        else if(root.val < key){
            root.right = deleteNode(root.right, key);
        }
        // 如果找到了要删除的节点 key
        else{
            // 情况 1：如果 key 是叶子节点，直接删除
            if(root.left == null && root.right == null){
                root = null;
                return root;
            }
            // 情况 2：如果 key 只有左子树
            else if(root.left != null && root.right == null){
                return root.left;
            }
            // 情况 2：如果 key 只有右子树
            else if(root.left == null && root.right != null){
                return root.right;
            }
            // 情况 3：如果 key 左右子树都有，此处将 key 位置的值替换成右子树的最小值
            else{
                TreeNode node = root.right;
                while(node.left != null){
                    node = node.left;
                }
                root.val = node.val;
                // 删除右子树的最小值
                root.right = deleteNode(root.right, node.val);
            }
        }
        return root;
    }
}
