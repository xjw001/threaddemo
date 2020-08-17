package redis;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * 布隆过滤器
 */
public class BloomFilter {
    private static final int DEFAULT_SIZE = 2 << 24;
    private static final int[] seeds = new int[]{5,7,11,13,31,37,61};
    private BitSet bits = new BitSet(DEFAULT_SIZE);
    private SimpleHash[] func = new SimpleHash[seeds.length];

    public BloomFilter(){
        for (int i = 0; i < seeds.length; i++) {
            func[i] = new SimpleHash(DEFAULT_SIZE,seeds[i]);
        }
    }

    public void add(String value){
        for(SimpleHash f : func){
            bits.set(f.hash(value),true);
        }
    }

    public boolean contains(String value){
        if(value == null){
            return false;
        }
        boolean ret = true;
        for (SimpleHash f:func) {
            ret = ret && bits.get(f.hash(value));
        }
        return ret;
    }


    public static class SimpleHash{
        private int cap;
        private int seed;
        public SimpleHash(int cap,int seed){
            this.cap = cap;
            this.seed = seed;
        }
        public int hash(String value){
            int result = 0;
            int len = value.length();
            for (int i = 0; i < len; i++) {
                result = seed * result + value.charAt(i);
                System.out.print("value:"+value.charAt(i));
                System.out.print(" result:"+result);
                System.out.print(" result:"+ Integer.toString((cap - 1) & result));
                System.out.println();
            }
            return  (cap - 1) & result;
        }
    }

    public static void main(String[] args) {
        SimpleHash simpleHash = new SimpleHash(DEFAULT_SIZE,5);
        BloomFilter bf = new BloomFilter();
        List<String> strs = new ArrayList<String>();
        strs.add("123456");
        strs.add("hello world");
        strs.add("transDocId");
        strs.add("123456");
        strs.add("transDocId");
        strs.add("hello word");
        strs.add("test");
        for (int i = 0; i < strs.size(); i++) {
            String s = strs.get(i);
            boolean b1 = bf.contains(s);
            if(b1){
                System.out.println(i+","+s);
            }else {
                bf.add(s);
            }
        }
    }
}
