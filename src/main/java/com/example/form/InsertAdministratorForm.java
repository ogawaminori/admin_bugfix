package com.example.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * リクエストパラメータを受け取るFormクラス
 */
public class InsertAdministratorForm {

    /** 名前 */
    @NotBlank(message = "{error.notblank.name}")
    private String name;

    /** メールアドレス */
    @NotBlank(message = "{error.notblank.mail}")
    @Email(message = "{error.email.mail}")
    private String mailAddress;

    /** パスワード */
    @NotBlank(message = "{error.notblank.password}")
    @Size(min = 8, max = 16, message = "{error.size.password}")
    private String password;

    /** 確認用パスワード */
    @NotBlank(message = "{error.notblank.password")
    private String repassword;

    public InsertAdministratorForm() {
    }

    public InsertAdministratorForm(@NotBlank(message = "{error.notblank.name}") String name,
            @NotBlank(message = "{error.notblank.mail}") @Email(message = "{error.email.mail}") String mailAddress,
            @NotBlank(message = "{error.notblank.password}") @Size(min = 8, max = 16, message = "{error.size.password}") String password,
            @NotBlank(message = "{error.notblank.password") String repassword) {
        this.name = name;
        this.mailAddress = mailAddress;
        this.password = password;
        this.repassword = repassword;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    @Override
    public String toString() {
        return "InsertAdministratorForm [name=" + name + ", mailAddress=" + mailAddress + ", password=" + password
                + ", repassword=" + repassword + "]";
    }

}
