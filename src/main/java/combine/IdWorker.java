package combine;

/**
 * 总共64位
 * 1最高以为不用 表示符号，固定0正数
 * 2后41位时间戳--》毫秒级别
 * 3后10位机器id+工作id --》1024个节点
 * 4最后12位序列号 --》4095个序列
 *
 */
public class IdWorker {
    //工作id，部署机器的时候自定义
    private long workerId;
    //数据id,部署机器的时候自定义
    private long datacenterId;
    //12为序列号
    private long sequence;

    //初始化时间戳2010-05-10
    private long twepoch = 1288834974657L;

    private long workerIdBits = 5L;

    private long datacenterIdBits = 5L;

    //最大值31
    private long maxWorkerId = -1L ^ (-1L << workerIdBits);

    //最大值31
    private long maxDatacenterId = -1L ^ (-1L << workerIdBits);

    //序列号id长度
    private long sequenceBits = 12L;
    //序列号最大值
    private long sequenceMask = -1L ^ (-1L << sequenceBits);

    //工作id需要左移的位数，12位
    private long workerIdShift = sequenceBits;
    //数据id需要左移位数 12+5=17位
    private long datacenterIdShift = sequenceBits + workerIdBits;
    //时间戳需要左移位数 12+5+5=22位
    private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;

    //上次时间戳，初始值为负数
    private long lastTimestamp = -1L;

     public IdWorker(long workerId,long datacenterId,long sequence){
        if(workerId > maxWorkerId || workerId < 0){
            throw  new IllegalArgumentException("workerId非法");
        }
        if(datacenterId > maxDatacenterId || datacenterId <0){
            throw new IllegalArgumentException("datacenterId非法");
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
        this.sequence = sequence;
    }

    public synchronized long nextId(){
        long timestamp = timeGen();
        if(timestamp < lastTimestamp){
            throw new RuntimeException("获取时间戳异常");
        }

        if(lastTimestamp == timestamp){
            sequence = (sequence +1 ) & sequenceMask;
        }else{
            sequence = 0;
        }
        //将上次的时间戳值刷新
        lastTimestamp = timestamp;

        /**
         * 返回结果：
         * (timestamp - twepoch) << timestampLeftShift) 表示将时间戳减去初始时间戳，再左移相应位数
         * (datacenterId << datacenterIdShift) 表示将数据id左移相应位数
         * (workerId << workerIdShift) 表示将工作id左移相应位数
         * | 是按位或运算符，例如：x | y，只有当x，y都为0的时候结果才为0，其它情况结果都为1。
         * 因为个部分只有相应位上的值有意义，其它位上都是0，所以将各部分的值进行 | 运算就能得到最终拼接好的id
         */
        System.out.println((timestamp - twepoch) << timestampLeftShift);
        System.out.println(datacenterId << datacenterIdShift);
        System.out.println(workerId << workerIdShift);
        System.out.println(sequence);
        return ((timestamp - twepoch) << timestampLeftShift) |
                (datacenterId << datacenterIdShift) |
                (workerId << workerIdShift) |
                sequence;
    }

    //获取时间戳，并与上次时间戳比较
    private long tilNextMillis(long lastTimestamp) {
        long timestamp = timeGen();
        while (timestamp <= lastTimestamp) {
            timestamp = timeGen();
        }
        return timestamp;
    }

    private long timeGen(){
        return System.currentTimeMillis();
    }

    public static void main(String[] args) {
//        IdWorker worker = new IdWorker(1,1,1);
//        for (int i=0;i<2;i++){
//            System.out.println(worker.nextId());
//        }
        System.out.println(1<< 10);
    }
}
