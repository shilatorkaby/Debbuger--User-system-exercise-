
import Controllers.*;

public class Main {
    public static void main(String[] args) {
        testCreateUser();

    }

    private static void testCreateUser() {
        UserController.getInstance().createNewUser("itamar@gmail.com","dsdsffdf","");
        UserController.getInstance().createNewUser("it@gmail.com","dsd","");
        UserController.getInstance().createNewUser("it@gmail.com","dsdgerggr","");
        UserController.getInstance().createNewUser("itamar@gmail.com","dsdgerggr","");
    }
}