package Services;


import Entity.User;

import java.util.Optional;

public class ServicesUtil {
    public static User isUserExists(Optional<User> user) {
        return user.orElseThrow(() -> {
            throw new IllegalArgumentException("There is no user with the email you type");
        });
    }
}