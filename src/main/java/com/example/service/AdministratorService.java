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
    
    public void insert(Administrator administrator){
        administratorRepository.insert(administrator);
    }
}
