package com.example.request;

public class SignupRequest {
    private String email;
    private String password;
    private String confirmPassword;
    private String firstName;
    private String lastName;

    static String EMAIL_REGEXP = "^[0-9a-zA-Z_-]+[@][0-9a-zA-Z_-]+[.][0-9a-zA-Z._-]+$";

    public SignupRequest() {}

    public SignupRequest(String email, String firstName, String lastName, String password, String confirmPassword) {
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.confirmPassword = confirmPassword;
    }

    public boolean isPasswordValid() {
        return !getPassword().isBlank() 
            && getPassword().length() >= 6
            && getPassword().compareTo(getConfirmPassword()) == 0;
    }

    public boolean isFirstNameValid() {
        return !getFirstName().isBlank();
    }

    public boolean isLastNameValid() {
        return !getLastName().isBlank();
    }

    public boolean isEmailValid() {
        return getEmail().matches(EMAIL_REGEXP);
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setConfirmPassword(String confirm) {
        this.confirmPassword = confirm;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }
}
