package july;

/**
 * @author chenjiena
 * @version 1.0
 * @created 2020/2/27.
 */
public class Father {

    private String name;
    private Integer sex;
    private Integer old;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getOld() {
        return old;
    }

    public void setOld(Integer old) {
        this.old = old;
    }

}


class Singleton {
    // 使用 volatile 关键字确保多线程下的可见性
    private static volatile Singleton instance;

    // 私有构造方法，防止外部直接实例化
    private Singleton() {
    }

    // 获取单例实例的方法
    public static Singleton getInstance() {
        // 第一次检查
        if (instance == null) {
            // 加锁，保证只有一个线程可以进入创建实例的代码块
            synchronized (Singleton.class) {
                // 第二次检查，因为可能在第一个线程获得锁后，另一个线程已经创建了实例
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    // 其他业务方法
}
