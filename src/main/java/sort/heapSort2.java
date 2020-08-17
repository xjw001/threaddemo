package sort;

import java.util.Arrays;

public class heapSort2 {
    public static void main(String[] args) {
      //  int[] arr={5,8,30,44,7,14,88,3,1};
        int[] tArr = {11,8,18,5,77,45,3,25};
        int[] result = heapSort2.sort(tArr);
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i]+" ");
        }
    }

    public static int[] sort(int[] arr){
        int[] result = Arrays.copyOf(arr,arr.length);
        bulidMaxHeap(result);
        for (int i = arr.length -1;i > 0;i--){
            swap(result,0,i);
            heapify(result,0,i);
        }
        return result;
    }

    public static void bulidMaxHeap(int[] arr){
        int len = arr.length;
        for(int i= (int)Math.floor(len/2);i>=0;i--){
            heapify(arr,i,len);
        }
    }

    public static void heapify(int[] arr,int idx,int len){
        //左节点
        int left = 2*idx + 1;
        //右节点
        int right = 2*idx + 2;
        int largest = idx;
        if(left < len && arr[left] > arr[largest]){
            largest = left;
        }
        if(right < len && arr[right] > arr[largest]){
            largest = right;
        }

        if(largest != idx){
            swap(arr,idx,largest);
            heapify(arr,largest,len);
        }
    }

    public static void swap(int[] arr, int i,int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
