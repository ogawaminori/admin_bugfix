package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Administrator;
import com.example.repository.AdministratorRepository;

/**
 * 管理者情報を登録する業務処理を⾏うメソッドを作成する 
 */
@Service
@Transactional
public class AdministratorService {
    
    @Autowired
    private AdministratorRepository administratorRepository;
    
    /**
     * 管理者登録
     * @param administrator
     */
    public void insert(Administrator administrator){
        administratorRepository.insert(administrator);
    }

    /**
     * 管理者ログイン処理
     * @param mailAddress　管理者登録済みメールアドレス
     * @param password　　　管理者用登録済みパスワード
     * @return　メールアドレス・パスワードが一致した管理者情報
     */
    public Administrator login(String mailAddress,String password){
        return administratorRepository.findByMailAddressAndPassword(mailAddress, password);
    }

    /**
     * メールアドレス重複チェック
     * @param mailAddress　管理者メールアドレス
     * @return 管理者情報が存在(!= null)すればtrueを返す
     */
    public boolean isMailCheck(String mailAddress){
        Administrator administrator=administratorRepository.findByMailAddress(mailAddress);
        return administrator != null;
    }
}
