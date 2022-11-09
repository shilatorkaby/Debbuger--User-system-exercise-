package Client;

import Controllers.AuthController;
import Controllers.UserController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class Client {
    private static String token;
    private static String email;
    private static Logger logger = LogManager.getLogger(Client.class.getName());


    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        UserActions action;
        while(true)
        {
            System.out.println("please choose one of the following options:");
            int i=0;
            for (UserActions act:UserActions.values())
            {
                System.out.printf("%d: %s\n",++i,act);
            }
            String line = scanner.nextLine().trim();
            action= UserActions.values()[Integer.valueOf(line)-1];
            switch (action) {
                case REGISTER:
                    handelRegister(scanner);
                    break;
                case LOGIN:
                    handleLogin(scanner);
                    break;
                case UPDATE_NAME:
                    handleUpdateName(scanner);
                    break;
                case UPDATE_PASSWORD:
                    handleUpdatePassword(scanner);
                    break;
                case UPDATE_EMAIL:
                    handleUpdateEmail(scanner);
                    break;
                case EXIT:
                    return;
            }
        }

    }

    private static void handleUpdatePassword(Scanner scanner) {
        System.out.println("enter your new password:");
        String newPassword=scanner.nextLine();
        UserController.getInstance().modifyPassword(email,token,newPassword);
        logger.debug("Password update worked!");
    }
    private static void handleUpdateEmail(Scanner scanner)
    {
        System.out.println("enter your new email:");
        String newEmail=scanner.nextLine();
        UserController.getInstance().modifyEmail(email,token,newEmail);
        logger.debug("Email update worked!");

    }

    private static void handleUpdateName(Scanner scanner) {
        System.out.println("enter your new name:");
        String newName=scanner.nextLine();
        UserController.getInstance().modifyUserName(email,token,newName);
        logger.debug("User updated worked!");

    }

    private static void handleLogin(Scanner scanner) {
        System.out.println("enter your email:");
        String email= scanner.nextLine();
        System.out.println("enter your password:");
        String password= scanner.nextLine();
        token=AuthController.getInstance().tryLogin(email,password);
        logger.debug("User login worked!");
        Client.email=email;
    }

    private static void handelRegister(Scanner scanner) {
        System.out.println("Start Registration:");
        System.out.println("enter your name:");
        String name= scanner.nextLine();
        System.out.println("enter a valid email:");
        String email= scanner.nextLine();
        System.out.println("enter a password:");
        String password= scanner.nextLine();
        UserController.getInstance().createNewUser(email,password,name);
        logger.debug("User created worked!");

    }
}