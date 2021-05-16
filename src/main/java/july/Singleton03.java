package july;

/**
 * @author chenjiena
 * @version 1.0
 * @created 2020/2/12.
 */
public class Singleton03 {

    private static Singleton03 singleton03;

    private Singleton03(){}

    public static Singleton03 getInstance(){
        if (singleton03 == null){
            singleton03 = new Singleton03();
        }
        return singleton03;
    }
}
