package MyThread2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class AtomicArrayTest {

    public static void main(String[] args) {
        demo(
                ()->new int[10],
                (array)->array.length,
                (array,index)->array[index]++,
                (array)-> System.out.println(Arrays.toString(array))
        );
        demo(
                ()->new AtomicIntegerArray(10),
                (array)->array.length(),
                (array,index)->array.getAndIncrement(index),
                array-> System.out.println(array)
        );
    }
    private static <T> void demo(
            Supplier<T> arraySupplier,
            Function<T,Integer> lengthFun,
            BiConsumer<T,Integer> putComsumer,
            Consumer<T> printConsumer){
        List<Thread> list = new ArrayList<>();
        T array = arraySupplier.get();
        int length = lengthFun.apply(array);
        for (int i = 0; i < length; i++) {
            list.add(new Thread(()->{
                for (int j = 0; j < 10000; j++) {
                    putComsumer.accept(array,j%length);
                }
            }));
        }
        list.forEach(t->t.start());
        list.forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        printConsumer.accept(array);
    }
}
