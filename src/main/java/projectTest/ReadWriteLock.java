package projectTest;

import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * 总结
 *
 * 读写锁特点特点：读锁是共享锁，写锁是排他锁，读锁和写锁不能同时存在
 * 插队策略：为了防止线程饥饿，读锁不能插队
 * 升级策略：只能降级，不能升级
 * ReentrantReadWriteLock适合于读多写少的场合，可以提高并发效率，而ReentrantLock适合普通场合
 *
 *
 *
 * 读锁和写锁的占用（重入）次数都是共用state字段，高位记录读锁，地位记录写锁，所以读锁和写锁的最大占用次数为2^16
 * 读锁和写锁都是可重入的
 * 读锁是共享锁，允许多个线程获取
 * 写锁是排他锁，只允许一个线程获取
 * 一个线程获取了读锁，在非公平锁的情况下，其他等待获取读锁的线程都可以尝试获取读锁，在公平锁的情况下，按照AQS同步队列的顺利来获取，如果队列前面有一个等待写锁的线程在排队，则后面所有等待获取读锁的线程都将无法获取读锁
 * 获取读锁的线程，不能再去申请获取写锁
 * 一个获取了写锁的线程，在持有锁的时候可以去申请获取读锁，在释放写锁以后，还会继续持有读锁，这就是所谓的锁降级
 * 读锁无法升级为写锁，原因是获取读锁的线程可能是多个，而写锁是独占的，不能多个线程持有，也就是说不支持锁升级
 *
 *
 * 详解连接：https://www.cnblogs.com/pinxiong/p/13307774.html
 *
 */
public class ReadWriteLock {

    public static void main(String[] args) {

    }

    private static ReentrantReadWriteLock reentrantLock = new ReentrantReadWriteLock();
    private static ReentrantReadWriteLock.ReadLock readLock = reentrantLock.readLock();
    private static ReentrantReadWriteLock.WriteLock writeLock = reentrantLock.writeLock();

    public static void read(){
        //开始尝试获取读锁

        readLock.lock(); //独享锁——也就是在写操作的时候这把锁是只有写不能读
        try {
            //3
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            readLock.unlock();
            //释放锁
        }
    }

    public static void write(){
        //尝试获取写锁

        writeLock.lock(); //共享锁——其他读操作都是可以获得这个读锁的，但是写的操作来的话就不可以了
        try {
            //
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            writeLock.unlock();
            //释放锁
        }
    }
}
