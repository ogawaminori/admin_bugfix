package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.domain.Administrator;

/**
 * administerators テーブルを操作するリポジトリ(Dao)
 */
@Repository
public class AdministratorRepository {

    @Autowired
    private NamedParameterJdbcTemplate template;

    private final RowMapper<Administrator> ADMIN_ROW_MAPPER = (rs, rowNum) -> {
        Administrator administrator = new Administrator();
        administrator.setId(rs.getInt("id"));
        administrator.setName(rs.getString("name"));
        administrator.setMailAddress(rs.getString("mail_address"));
        administrator.setPassword(rs.getString("password"));
        return administrator;
    };

    /**
     * 管理者情報を挿⼊する
     * 
     * @param administrator 管理者情報
     */
    public void insert(Administrator administrator) {
        String sql = "INSERT INTO administrators (name,mail_address,password) VALUES (:name,:mailAddress,:password);";
        BeanPropertySqlParameterSource param = new BeanPropertySqlParameterSource(administrator);
        template.update(sql, param);
    }

    /**
     * メールアドレスとパスワードから管理者情報を取得する(ログイン用)
     * (1件も存在しない場合はnullを返す)
     * 
     * @param mailAddress 管理者メールアドレス
     * @param password    管理者パスワード
     * @return 特定の管理者情報
     */
    public Administrator findByMailAddressAndPassword(String mailAddress, String password) {
        String sql = "SELECT id, name, mail_address, password FROM Administrators WHERE mail_address=:mailAddress AND password=:password;";
        MapSqlParameterSource param = new MapSqlParameterSource()
                .addValue("mailAddress", mailAddress)
                .addValue("password", password);
        List<Administrator> administratorList = template.query(sql, param, ADMIN_ROW_MAPPER);
        if (administratorList.size() == 0) {
            return null;
        }
        return administratorList.get(0);
    }


    /**
     * メールアドレスから管理者情報を取得する(重複チェック用)
     * @param mailAddress　管理者メールアドレス
     * @return　特定のメールアドレスの一致した管理者情報
     */
    public Administrator findByMailAddress(String mailAddress){
        String sql="SELECT id,name,mail_address,password FROM Administrators WHERE mail_address=:mailAddress";
        MapSqlParameterSource param =new MapSqlParameterSource().addValue("mailAddress", mailAddress);
        List<Administrator> administrators = template.query(sql, param, ADMIN_ROW_MAPPER);
        if (administrators.size() == 0) {
            return null;
        }
        return administrators.get(0);
    }

}
