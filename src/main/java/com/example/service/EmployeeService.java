package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Employee;
import com.example.repository.EmployeeRepository;

import jakarta.servlet.http.HttpSession;

/**
 * 従業員用Serviceクラス
 */
@Service
@Transactional
public class EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    /**
     * ⼊社⽇順(降順)の従業員⼀覧を出⼒する
     * @return ⼊社⽇順(降順)の従業員⼀覧の出力結果
     */
    public List<Employee> showList(){
        return employeeRepository.findAll();        
    }

    /**
     * 従業員情報を取得する
     * @param id 従業員id
     * @return　従業員情報の出力結果
     */
    public Employee showDetail(Integer id){
        return employeeRepository.load(id);
    }

    /**
     * 従業員情報を更新する
     * @param employee パラメーター用従業員オブジェクト
     */
    public void update (Employee employee){
        employeeRepository.update(employee);
    }
    
}
