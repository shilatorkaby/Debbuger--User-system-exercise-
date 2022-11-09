package Controllers;


import Services.AuthService;

public class AuthController {
    private final Validation validation;

    public static AuthController getInstance() {
        if (instance == null) instance = new AuthController();
        return instance;
    }

    private static AuthController instance;

    private AuthController() {
        validation = new Validation();
    }

    public String tryLogin(String email, String password) {
        try {
            validation.isValidEmail(email);
           // validation.isValidPassword(password);
            String token = AuthService.getInstance().login(email, password);
            System.out.println("Login succeeded.");
            return token;
        }
        catch (IllegalArgumentException exp)
        {
            ControllersUtil.printErrorToCmd("Login failed.",exp.getMessage());
            return null;
        }
    }

    public void checkToken(String email, String Token) {
        if(Token==null || Token.isEmpty())
        {
            ControllersUtil.printErrorToCmd("Login failed.","The token is null or empty.\n You must login first to get a valid token.");
            return;
        }
        AuthService.getInstance().checkToken(email, Token);
    }
}