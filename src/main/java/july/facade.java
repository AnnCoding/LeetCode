package july;

public class facade {

    public static int[] copyIntArr(int[] arr){
        if (arr.length == 0){
            return new int[0];
        }
        int[] copy = new int[arr.length];
        for (int i=0;i<arr.length;i++){
            copy[i] = arr[i];
        }
        return copy;
    }
}
