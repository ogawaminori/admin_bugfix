package com.example.controller;

import java.util.List;
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

    @Autowired
    private MessageSource messageSource;

    /**
     * ⼊社⽇順(降順)の従業員⼀覧を出⼒する
     * 
     * @param model ⼊社⽇順(降順)の従業員⼀覧情報が格納されたModelオブジェクト
     * @return employee/list.html画面への遷移
     */
    @GetMapping("/showList")
    public String showList(Model model) {
        List<Employee> employeeList = employeeService.showList();
        model.addAttribute("employeeList", employeeList);
        return "employee/list";
    }

    /**
     * 従業員情報を検索する
     * 
     * @param id    従業員id
     * @param model 従業員情報の格納されたmodelオブジェクト
     * @param form  formに入力された検索情報
     * @return employee/detail.html画面への遷移
     */
    @GetMapping("/showDetail")
    public String showDetail(@RequestParam("id") String id, Model model, UpdateEmployeeForm form) {
        Employee employee = employeeService.showDetail(Integer.parseInt(id));
        model.addAttribute("employee", employee);
        return "employee/detail";
    }

    @GetMapping("/searchName")
    public String searchName(@RequestParam("searchName") String searchName, Model model) {
        List<Employee> employeeSearchList = employeeService.searchName(searchName);
        if (searchName.isEmpty()) {
            employeeSearchList = employeeService.showList();
        } else if (employeeSearchList.isEmpty()) {
            String emptyMessage = messageSource.getMessage("empty.name", null, Locale.getDefault());
            model.addAttribute("emptyMessage", emptyMessage);
            employeeSearchList = employeeService.showList();
        }
        model.addAttribute("employeeSearchList", employeeSearchList);
        return "employee/list";
    }

    /**
     * 従業員の扶養人数を更新する
     * 
     * @param form 情報更新フォームに入力された内容
     * @return /employee/showList画面への遷移
     */
    @PostMapping("/update")
    public String update(@Validated UpdateEmployeeForm form, BindingResult result,Model model) {
        if (form.getDependentsCount() == null || form.getDependentsCount().isEmpty()) {
            return "redirect:/employee/showList";
        } else if (result.hasErrors()) {
            Employee employee =employeeService.showDetail(Integer.parseInt(form.getId()));
            model.addAttribute("employee",employee);
            return "employee/detail";
        } else {
            Employee employee = employeeService.showDetail(Integer.parseInt(form.getId()));
            employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));
            employeeService.update(employee);
            return "redirect:/employee/showList";
        }
    }
}
