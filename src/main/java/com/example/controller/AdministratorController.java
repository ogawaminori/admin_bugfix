package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Administrator;
import com.example.form.InsertAdministratorForm;
import com.example.form.LoginForm;
import com.example.service.AdministratorService;

/**
 * 管理者用Controller
 */
@Controller
@RequestMapping("/")
public class AdministratorController {

    @Autowired
    private AdministratorService administratorService;

    /**
     * ログイン画⾯を表⽰する処理を記述する 
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
     * 管理者情報を登録する
     * 
     * @param form 管理者登録フォーム
     * @return ログイン画面("/")にリダイレクト
     */
    @PostMapping("/insert")
    public String insert(InsertAdministratorForm form) {
        Administrator administrator = new Administrator();
        administrator.setName(form.getName());
        administrator.setMailAddress(form.getMailAddress());
        administrator.setPassword(form.getPassword());
        administratorService.insert(administrator);
        return "redirect:/";
    }

}
