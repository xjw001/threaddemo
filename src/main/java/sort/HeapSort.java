package sort;

import java.util.Arrays;

/**
 * 堆排序
 *
 */
public class HeapSort {

    public static void main(String[] args) throws Exception {
        int[] tArr = {11,8,18,5,77,45,3,25};
        int[] result = new HeapSort().sort(tArr);
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i]+" ");
        }
    }
    public int[] sort(int[] sourceArray) throws  Exception{
        int[] arr = Arrays.copyOf(sourceArray,sourceArray.length);
        int length = arr.length;
        bulidMaxHeap(arr,length);
        for(int i = length -1;i>0;i--){
            swap(arr,0,i);
            heapify(arr,0,i);
        }
        return arr;
    }

    /**
     * 构建最大堆（从数组中间的位置倒序构建堆）
     * @param arr
     * @param len
     */
    private void bulidMaxHeap(int[] arr,int len){
        for(int i = (int)Math.floor(len/2);i>=0;i--){
            heapify(arr,i,len);
        }
    }

    /**
     *
     * @param arr
     * @param i
     * @param len
     */
    private void heapify(int[] arr, int i,int len){
        //找出 arr[i]、arr[left]、arr[right]之间的最大值
        int left = 2*i + 1;
        int right = 2*i + 2;
        int largest = i;
        if(left < len && arr[left] > arr[largest]){ //防止出界
            largest = left;
        }
        if(right < len && arr[right] > arr[largest]){ //防止出界
            largest = right;
        }
        if(largest != i){ //如果最大值不是当前i节点就交换i和largest的位置
            swap(arr,i,largest);
            heapify(arr,largest,len);
        }
    }

    /**
     * 交换i和j位置的数据
     * @param arr
     * @param i
     * @param j
     */
    private void swap(int[] arr,int i,int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
