package com.example.controller;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Administrator;
import com.example.form.InsertAdministratorForm;
import com.example.form.LoginForm;
import com.example.service.AdministratorService;

import jakarta.servlet.http.HttpSession;

/**
 * 管理者用Controller
 */
@Controller
@RequestMapping("/")
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    @Autowired
    private MessageSource messageSource;

    /**
     * ログイン画⾯に遷移する 
     * @param form ログインフォーム
     * @return　login.html画面への遷移
     */
    @GetMapping("/")
    public String toLogin(LoginForm form){
        return "administrator/login";
    }

    /**
     * 管理者登録画面に遷移する
     * 
     * @param form 管理者登録フォーム
     * @return insert.html画面への遷移
     */
    @GetMapping("/toInsert")
    public String toInsert(InsertAdministratorForm form) {
        return "administrator/insert";
    }

    /**
     * ログアウトする
     * 
     * @param form    ログイン情報
     * @param session セッション情報
     * @return ログイン画面にリダイレクトする
     */
    @GetMapping("/logout")
    public String logout(LoginForm form, HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    /**
     * 管理者ログイン処理
     * 
     * @param form  ログインフォーム
     * @param model 管理者情報の格納されたModelオブジェクト
     * @return employee/showList.html画面への遷移
     */
    @PostMapping("/login")
    public String login(LoginForm form, Model model, HttpSession session) {
        Administrator administrator = administratorService.login(form.getMailAddress(), form.getPassword());
        if (administrator == null) {
            String errorMessage = messageSource.getMessage("error.login.mailPass", null, Locale.getDefault());
            model.addAttribute("error", errorMessage);
            return "administrator/login";
        } else {
            session.setAttribute("administratorName", administrator);
            return "redirect:/employee/showList";
        }
    }

    /**
     * 管理者情報を登録する
     * 
     * @param form 管理者登録フォーム
     * @return ログイン画面("/")にリダイレクト
     */
    @PostMapping("/insert")
    public String insert(@Validated InsertAdministratorForm form, BindingResult result) {
        // バリデーションチェック
        if (result.hasErrors()) {
            form.setPassword("");
            form.setRepassword("");
            return "administrator/insert";
        }
        // メール重複確認
        if (administratorService.isMailCheck(form.getMailAddress()) == true) {
            result.rejectValue("mailAddress", "error.isMailCheck.mail", "このメールアドレスは登録出来ません");
            form.setPassword("");
            form.setRepassword("");
            return "administrator/insert";
        }
        // 確認用パスワードとの照合
        if (!form.getPassword().equals(form.getRepassword())) {
            result.rejectValue("repassword", "error.isPasswordCheck.password", "パスワードが一致しません");
            form.setPassword("");
            form.setRepassword("");
            return "administrator/insert";
        }

        // 正常系処理
        Administrator administrator = new Administrator();
        administrator.setName(form.getName());
        administrator.setMailAddress(form.getMailAddress());
        administrator.setPassword(form.getPassword());
        administratorService.insert(administrator);
        return "redirect:/";
    }

}
