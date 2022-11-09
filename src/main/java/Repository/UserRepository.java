package Repository;

import Entity.User;
import com.google.gson.Gson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UserRepository {
    private static final String PATH = "src/main/java/Repository/Json";
    private static Logger logger = LogManager.getLogger(UserRepository.class.getName());

    private static final Gson gson = new Gson();
    private final Map<String, User> usersByEmails;
    private static UserRepository instance;

    private UserRepository() {
        usersByEmails = new HashMap<>();
    }

    public static UserRepository getInstance() {
        if(instance == null)
            instance = new UserRepository();

        return instance;
    }

    public void addNewUser(User user) {
        int userId = user.getId();
        File userJsonFile = new File(PATH + "User" + userId);
        String userJson = gson.toJson(user);
        try {
            userJsonFile.createNewFile();
            Files.write(Paths.get(PATH + "User" + userId), userJson.getBytes(StandardCharsets.UTF_8));
            logger.info("file created");
        } catch (IOException e) {
            logger.error("new user json file creation/writing failed");
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public User getUserById(int id) {
        File userJsonFile = new File(PATH + "User" + id);
        FileReader fileReader;
        logger.info("user found by id");
        if (!userJsonFile.exists()) {
            logger.error("User " + id + "does not exist");
            return null;
        }
        try {
            fileReader = new FileReader(userJsonFile);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);  //will never occur
        }
        return gson.fromJson(fileReader, User.class);
    }

    public Optional<User> getUserByEmail(String email) {
        FileReader fileReader;
        File dir = new File(PATH);
        if (dir.exists()) {
            try {
                Files.createDirectories(Path.of(PATH));
                logger.info("directory was created");
            } catch (IOException e) {
                logger.error("problem of creating the directory");
                throw new RuntimeException("problem of creating the directory");
            }
            File[] foundFiles = dir.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.startsWith("User");
                }
            });

            for (File file : foundFiles) {
                try {
                    fileReader = new FileReader(file);
                } catch (FileNotFoundException e) {
                    logger.error(e.getMessage());
                    throw new RuntimeException(e);  //will never occur
                }
                User tempUser = gson.fromJson(fileReader, User.class);
                usersByEmails.put(tempUser.getEmail(), tempUser);
            }
        } if (usersByEmails.containsKey(email)) return Optional.of(usersByEmails.get(email));
        return Optional.empty();

    }


    public void updateUsersName(String email, String newName)   {
        User tempUser = throwUserNotFoundException(getUserByEmail(email),email);
        tempUser.setName(newName);
        addNewUser(tempUser);
        logger.info(tempUser.getName());
    }

    public void updateUsersPassword(String email, String newPassword)   {
        User tempUser = throwUserNotFoundException(getUserByEmail(email),email);
        tempUser.setPassword(newPassword);
        addNewUser(tempUser);
        logger.info(tempUser.getName());

    }


    public void updateUsersEmail(String email, String newEmail)   {
        User tempUser = throwUserNotFoundException(getUserByEmail(email),email);
        tempUser.setEmail(newEmail);
        addNewUser(tempUser);
        logger.info(tempUser.getName());

    }
    private User throwUserNotFoundException(Optional<User> user,String email)
    {
        if(user.isPresent())return user.get();
        {
            logger.warn("No user with the following email address");
            throw new NullPointerException("No user with the following email address: " + email + " was found in the system.");
        }
    }
}