package api.v1.models;

import jakarta.validation.constraints.NotBlank;

public class User {
    @NotBlank(message = "Login cannot be blank")
    private String login;
    @NotBlank(message = "Login cannot be blank")
    private String password;

    public User(String login, String password){
        this.login = login;
        this.password = password;
    }
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
