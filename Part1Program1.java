import java.math.BigDecimal;

public class Part1Program1 {

    public static void main(String[] args) {
        part_1_print();
        badPractice();
        correctnessBug();
        multithreadedBug();
        performanceBug();
        securityBug();
        dodgyBug();
    }

    public static void part_1_print() {
        System.out.println("hello world");
    }

    public static void badPractice() {
        BigDecimal a = new BigDecimal("1");
        BigDecimal b = new BigDecimal("2");
        a.add(b); // RV_RETURN_VALUE_IGNORED
    }

    public static void correctnessBug() {
        String s = null;
        if (Math.random() > 0.5) {
            s = "hello";
        }
        System.out.println(s.length()); // NP_NULL_ON_SOME_PATH
    }

    private int x;

    public void setX(int x) {
        this.x = x; // IS2_INCONSISTENT_SYNC
    }

    public synchronized int getX() {
        return x;
    }

    public static void multithreadedBug() {
        Part1Program1 p = new Part1Program1();
        p.setX(5);
    }

    public static void performanceBug() {
        String s = new String("hello"); // DM_STRING_CTOR
    }

    public static void securityBug() {
        String name = "user";
        String query = "SELECT * FROM users WHERE name = '" + name + "'"; // SQL_NONCONSTANT_STRING_PASSED_TO_EXECUTE
        System.out.println(query);
    }

    public static void dodgyBug() {
        int x = 1;
        x = 2; // DLS_DEAD_LOCAL_STORE
        System.out.println(x);
    }

}
