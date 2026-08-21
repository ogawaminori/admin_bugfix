package com.example.form;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class InsertEmployeeForm {

    /** 名前 */
    @NotBlank
    private String name;

    /** 画像 */
    private MultipartFile image;

    /** 性別 */
    @NotBlank
    private String gender;

    /** 入社日 */
    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date hireDate;

    /** メールアドレス */
    @NotBlank
    @Email
    private String mailAddress;

    /** 郵便番号 */
    @NotBlank
    @Pattern(regexp = "^[0-9]*$")
    private String zipCode;

    /** 住所 */
    @NotBlank
    private String address;

    /** 電話番号 */
    @NotBlank
    @Pattern(regexp = "^[0-9]*$")
    private String telephone;

    /** 給料 */
    @NotNull
    @Max(value = 500000)
    @Min(value = 100000)
    private Integer salary;

    /** 特性 */
    @Size(max = 300)
    private String characteristics;

    /** 扶養人数 */
    @NotNull
    private Integer dependentsCount;

    public InsertEmployeeForm() {
    }

    public InsertEmployeeForm(@NotBlank String name, MultipartFile image, @NotBlank String gender,
            @NotNull Date hireDate, @NotBlank @Email String mailAddress,
            @NotBlank @Pattern(regexp = "^[0-9]*$") String zipCode, @NotBlank String address,
            @NotBlank @Pattern(regexp = "^[0-9]*$") String telephone, @NotNull @Max(500000) @Min(100000) Integer salary,
            @Size(max = 300) String characteristics, @NotNull Integer dependentsCount) {
        this.name = name;
        this.image = image;
        this.gender = gender;
        this.hireDate = hireDate;
        this.mailAddress = mailAddress;
        this.zipCode = zipCode;
        this.address = address;
        this.telephone = telephone;
        this.salary = salary;
        this.characteristics = characteristics;
        this.dependentsCount = dependentsCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public String getMailAddress() {
        return mailAddress;
    }

    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public Integer getDependentsCount() {
        return dependentsCount;
    }

    public void setDependentsCount(Integer dependentsCount) {
        this.dependentsCount = dependentsCount;
    }

    @Override
    public String toString() {
        return "InsertEmployeeForm [name=" + name + ", image=" + image + ", gender=" + gender + ", hireDate=" + hireDate
                + ", mailAddress=" + mailAddress + ", zipCode=" + zipCode + ", address=" + address + ", telephone="
                + telephone + ", salary=" + salary + ", characteristics=" + characteristics + ", dependentsCount="
                + dependentsCount + "]";
    }

    
}
