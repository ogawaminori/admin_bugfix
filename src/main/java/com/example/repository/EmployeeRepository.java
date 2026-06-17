package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
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

    public List<Employee> findAll() {
        String sql = "SELECT "
                + "id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count "
                + "FROM Employees ORDER BY hire_date DESC;";
        List<Employee> employees = template.query(sql, EMPLOYEE_ROW_MAPPER);
        return employees;
    }

    public Employee load(Integer id) {
        String sql = "SELECT "
                + "id,name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count "
                + "FROM Employees WHERE id=:id";
        MapSqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
        try {
            Employee employee = template.queryForObject(sql, param, EMPLOYEE_ROW_MAPPER);
            return employee;
        } catch (DataAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void update(Employee employee) {
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(employee);
        if (employee.getId() == null) {
            String sql="INSERT INTO Employees "
                       +"(name,image,gender,hire_date,mail_address,zip_code,address,telephone,salary,characteristics,dependents_count) "
                       +"VALUES(:name,:image,:gender,:hireDate,:mailAddress,:zipCode,address:telephone,:salary,:characteristics,:dependentsCount)";           
            template.update(sql, param);
        } else {
            String sql = "UPDATE Employees SET "
                    + "name=:name,image=:image,gender=:gender,hire_date=:hireDate,mail_address=:mailAddress,"
                    + "zip_code=:zipCode,address=:address,telephone=:telephone,salary=:salary,"
                    + "characteristics=:characteristics,dependents_count=:dependentsCount "
                    + "WHERE id =:id";
            template.update(sql,param);
        }
    }
}
