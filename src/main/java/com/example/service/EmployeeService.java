package com.example.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.domain.Employee;
import com.example.repository.EmployeeRepository;

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
     * 
     * @return ⼊社⽇順(降順)の従業員⼀覧の出力結果
     */
    public List<Employee> showList() {
        return employeeRepository.findAll();
    }

    /**
     * 従業員情報を取得する
     * 
     * @param id 従業員id
     * @return 従業員情報の出力結果
     */
    public Employee showDetail(Integer id) {
        return employeeRepository.load(id);
    }

    /**
     * 従業員名を曖昧検索する
     * 
     * @param searchName 検索単語
     * @return 検索結果
     */
    public List<Employee> searchName(String searchName) {
        return employeeRepository.findByName(searchName);
    }
    
    /**
     * 従業員登録
     * @param employee　登録先のデータ型
     */
    public synchronized void insert(Employee employee,MultipartFile imageFile){
        employee.setId(employeeRepository.findMaxId()+1);

        String originalName=imageFile.getOriginalFilename();
        String ext =originalName.substring(originalName.lastIndexOf("."));
        String imageName =employee.getId()+ext;
        
        Path staticPath =Paths.get("src/main/resources/static/img_employee/"+imageName);
        try {
            Files.copy(imageFile.getInputStream(),staticPath,StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        employee.setImage(imageName);
        employeeRepository.insert(employee);
    }

    /**
     * 従業員情報を更新する
     * 
     * @param employee パラメーター用従業員オブジェクト
     */
    public void update(Employee employee) {
        employeeRepository.update(employee);
    }

}
