package Controllers;

import Services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class UserController {
    private static UserController instance;
    private static Logger logger = LogManager.getLogger(UserController.class.getName());

    Validation validation;

    public static UserController getInstance() {
        if (instance == null) instance = new UserController();
        logger.debug("returning instance succeed");
        return instance;
    }

    private UserController() {
        validation = new Validation();
        logger.debug("validation was occurred");
    }

    public void createNewUser(String email, String password, String userName) {
        try {
            validation.isValidEmail(email);
            UserService.getInstance().createUser(userName, email, password);
            logger.debug("create user failed");

        } catch (RuntimeException exp)
        {
            logger.error("User cannot be created.");
        }
    }
    public void modifyPassword(String email,String Token,String newPassword)
    {
        try {
            AuthController.getInstance().checkToken(email, Token);
            UserService.getInstance().changePassword(email, newPassword);
            System.out.println("The password has been successfully changed.");
        }
        catch (IllegalArgumentException exp)
        {
            logger.fatal("Failed to change password.");
        }
    }
    public void modifyUserName(String email,String Token,String newUserName)
    {
        AuthController.getInstance().checkToken(email, Token);
        logger.error("token doesn't exists");
        UserService.getInstance().changeName(email,newUserName);
        logger.info("Your name has been successfully changed.");
    }
    public void modifyEmail(String email,String Token,String newEmail)
    {
        try {
            validation.isValidEmail(newEmail);
            AuthController.getInstance().checkToken(email, Token);
            UserService.getInstance().changeEmail(email, newEmail);
            logger.info("Your email has been successfully changed.");
        }
        catch (IllegalArgumentException | NullPointerException exp)
        {
            logger.error("Failed to change email.");
        }
    }

}