package combine;

public class CompteTest {
    public static void main(String[] args) throws InterruptedException {
        long result = 0L;
        long startTime = System.currentTimeMillis();
        for (long i = 1L; i < 10000000000L; i++) {
            result +=i;
        }
        long endTime = System.currentTimeMillis();
        System.out.println("result:"+result);
        System.out.println("执行时间：" + (endTime - startTime));
    }
}
