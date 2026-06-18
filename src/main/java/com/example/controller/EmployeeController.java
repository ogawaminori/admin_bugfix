package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Employee;
import com.example.service.EmployeeService;

/**
 * 従業員用Controllerクラス
 */
@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * ⼊社⽇順(降順)の従業員⼀覧を出⼒する
     * @param model　⼊社⽇順(降順)の従業員⼀覧情報が格納されたModelオブジェクト
     * @return　employee/list.html画面への遷移
    */
    @GetMapping("/showList")
    public String showList(Model model){
        List<Employee> employeeLists=employeeService.showList();
        model.addAttribute("employeeList",employeeLists);
        return "employee/list";
    }

    
}
