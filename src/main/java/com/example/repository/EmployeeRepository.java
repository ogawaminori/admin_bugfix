package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.domain.Employee;

/**
 * employees テーブルを操作するリポジトリ(Dao
 */
@Repository
public class EmployeeRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    private final RowMapper<Employee> EMPLOYEE_ROW_MAPPER = new BeanPropertyRowMapper<>(Employee.class);

    /**
     * 従業員⼀覧情報を⼊社⽇順(降順)で取得する
     * (従業員が存在しない場合はサイズ0件の従業員⼀覧を返す)
     * 
     * @return 従業員情報
     */
    public List<Employee> findAll() {
        String sql = "SELECT "
                + "id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count "
                + "FROM Employees ORDER BY hire_date,id;";
        List<Employee> employees = template.query(sql, EMPLOYEE_ROW_MAPPER);
        return employees;
    }

    /**
     * 主キーから従業員情報を取得する
     * (従業員が存在しない場合はSpringが⾃動的に例外を発⽣します)
     * 
     * @param id 従業員id
     * @return 従業員情報
     */
    public Employee load(Integer id) {
        String sql = "SELECT "
                + "id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count "
                + "FROM Employees WHERE id=:id";
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        List<Employee> employeeList = template.query(sql, param, EMPLOYEE_ROW_MAPPER);
        if (employeeList.size() == 0) {
            return null;
        }
        return employeeList.get(0);

    }

    /**
     * 従業員名曖昧検索
     * @param name　検索
     * @return　従業員名曖昧検索結果
     */
    public List<Employee> findByName(String searchName) {
        String sql = "SELECT "
                + "id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count "
                + "FROM Employees WHERE name LIKE :name ORDER BY hire_date,id";
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("name", "%"+searchName+"%");
        List<Employee> employeeNameList = template.query(sql, param, EMPLOYEE_ROW_MAPPER);
        return employeeNameList;
    }

    /**
     * 従業員情報を変更する
     * (情報がない場合新規登録)
     * 
     * @param employee 従業員情報
     */
    public void update(Employee employee) {
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(employee);
        if (employee.getId() == null) {
            String sql = "INSERT INTO Employees "
                    + "(name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count) "
                    + "VALUES(:name,:image,:gender,:hireDate,:mailAddress,:zipCode,:address,:telephone,:salary,:characteristics,:dependentsCount)";
            template.update(sql, param);
        } else {
            String sql = "UPDATE Employees SET "
                    + "name=:name,image=:image,gender=:gender,hire_date=:hireDate,mail_address=:mailAddress,"
                    + "zip_code=:zipCode,address=:address,telephone=:telephone,salary=:salary,"
                    + "characteristics=:characteristics,dependents_count=:dependentsCount "
                    + "WHERE id =:id";
            template.update(sql, param);
        }
    }
}
