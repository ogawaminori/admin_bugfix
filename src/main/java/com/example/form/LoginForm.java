package com.example.form;

/**
 * ログイン時に使⽤するフォーム 
 */
public class LoginForm {

    /** メールアドレス */
    private String mailAddress;

    /** パスワード */
    private String password;

    public LoginForm() {
    }

    public LoginForm(String mailAddress, String password) {
        this.mailAddress = mailAddress;
        this.password = password;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginForm [mailAddress=" + mailAddress + ", password=" + password + "]";
    }

}
