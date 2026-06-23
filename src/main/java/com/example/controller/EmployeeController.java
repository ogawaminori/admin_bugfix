package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.domain.Employee;
import com.example.form.UpdateEmployeeForm;
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

    /**
     * 従業員情報を検索する
     * @param id 従業員id
     * @param model　従業員情報の格納されたmodelオブジェクト
     * @param form　formに入力された検索情報
     * @return　employee/detail.html画面への遷移
     */
    @GetMapping("/showDetail")
    public String showDetail(@RequestParam("id") String id,Model model,UpdateEmployeeForm form){
        Employee employee=employeeService.showDetail(Integer.parseInt(id));
        model.addAttribute("employee", employee);
        return "employee/detail";
    }

    /**
     * 従業員の扶養人数を更新する
     * @param form　情報更新フォームに入力された内容
     * @return　/employee/showList画面への遷移
     */
    @PostMapping("/update")
    public String update(UpdateEmployeeForm form){
        Employee employee=employeeService.showDetail(Integer.parseInt(form.getId()));
        employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));
        employeeService.update(employee);
        return "redirect:/employee/showList";
    }
   
}
