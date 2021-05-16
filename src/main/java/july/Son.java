package july;

/**
 * @author chenjiena
 * @version 1.0
 * @created 2020/2/27.
 */
public class Son extends Father {

    private String school;

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }
}
