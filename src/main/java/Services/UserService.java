package Services;

import Repository.UserRepository;
import Entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserService {

    private static UserService instance;
    private static Logger logger = LogManager.getLogger(UserService.class.getName());


    public static UserService getInstance() {
        if (instance == null) instance = new UserService();
        logger.debug("instance found");
        return instance;
    }

    private UserService() {
    }

    public void validUniqueEmail(String email) {
        if (UserRepository.getInstance().getUserByEmail(email).isPresent()) {
            logger.error("A user with this email already exists.");
            throw new IllegalArgumentException("A user with this email already exists. Choose another email.");
        }
    }

    public User createUser(String name, String email, String password) {
        isEmailFree(email);
        User u = new User(email, name, password);
        logger.debug("user is created");
        UserRepository.getInstance().addNewUser(u);
        logger.info("User is created!");
        return u;
    }

    private void isEmailFree(String email) {
        if (UserRepository.getInstance().getUserByEmail(email).isPresent()) {
            logger.error("There is another user with the email");
            throw new IllegalArgumentException("There is another user with the email you type. please try another.");
        }    }

    public void changePassword(String email, String password) {
        UserRepository.getInstance().updateUsersPassword(email,password);
        logger.debug("password was changed");
    }

    public void changeName(String email, String name) {
        UserRepository.getInstance().updateUsersName(email, name);
        logger.debug("name was changed");

    }
    public void changeEmail(String email,String newEmail)
    {
        if(UserRepository.getInstance().getUserByEmail(newEmail).isPresent()) throw  new IllegalArgumentException(String.format("The email address:%s is already in use.\nPlease trt another.",newEmail));
        UserRepository.getInstance().updateUsersEmail(email, newEmail);
        logger.debug("email was changed");

    }
}