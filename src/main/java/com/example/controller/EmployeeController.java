package com.example.controller;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.BeanUtils;
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
import org.springframework.web.multipart.MultipartFile;

import com.example.domain.Employee;
import com.example.form.InsertEmployeeForm;
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

    @GetMapping("/toInsert")
    public String toInsert(InsertEmployeeForm form){
        return "/employee/insert";
    }
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

    /**
     * 従業員名あいまい検索
     * 
     * @param searchName 検索単語
     * @param model      従業員情報の格納されたmodelオブジェクト
     * @return employee/list画面への遷移
     */
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
     * 従業員登録
     * @param form　従業員登録フォーム
     * @param result　エラー格納
     * @param imageFile　フォームから取得する画像ファイル
     * @param employee　登録データ型
     * @return　従業員リスト
     */
    @PostMapping("/insert")
    public String insert(@Validated InsertEmployeeForm form,
            BindingResult result,
            @RequestParam("image") MultipartFile imageFile) {
        if (result.hasErrors()) {
            return "employee/insert";
        }
        if (imageFile == null || imageFile.isEmpty()) {
			result.rejectValue("image", "error.empty.image","画像ファイルを選択してください");
            return "employee/insert";
		}

        if ((!imageFile.getOriginalFilename().endsWith(".jpg")
                && !imageFile.getOriginalFilename().endsWith(".png"))
                || imageFile.getSize() >= 1024 * 1024 * 5) {
            result.rejectValue("image", "error.kind.image", "5MB以下、拡張子はjpg,pngの画像を選択してください。");
            return "employee/insert";
        }
        Employee employee =new Employee();
        BeanUtils.copyProperties(form, employee);
        employeeService.insert(employee,imageFile);
        return "redirect:/employee/showList";
    }

    /**
     * 従業員の扶養人数を更新する
     * 
     * @param form 情報更新フォームに入力された内容
     * @return /employee/showList画面への遷移
     */
    @PostMapping("/update")
    public String update(@Validated UpdateEmployeeForm form, BindingResult result, Model model) {
        if (form.getDependentsCount() == null || form.getDependentsCount().isEmpty()) {
            return "redirect:/employee/showList";
        } else if (result.hasErrors()) {
            Employee employee = employeeService.showDetail(Integer.parseInt(form.getId()));
            model.addAttribute("employee", employee);
            return "employee/detail";
        } else {
            Employee employee = employeeService.showDetail(Integer.parseInt(form.getId()));
            employee.setDependentsCount(Integer.parseInt(form.getDependentsCount()));
            employeeService.update(employee);
            return "redirect:/employee/showList";
        }
    }
}
