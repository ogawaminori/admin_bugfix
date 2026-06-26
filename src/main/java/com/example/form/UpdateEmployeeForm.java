package com.example.form;

import jakarta.validation.constraints.Pattern;

/**
 * 従業員情報更新時に使⽤するフォーム
 */
public class UpdateEmployeeForm {
    /** 従業員ID */
    private String id;
    /** 扶養人数 */
    @Pattern(regexp = "^[0-9]*$", message = "{error.pattern.dependentsCount}")
    private String dependentsCount;

    public UpdateEmployeeForm() {
    }

    public UpdateEmployeeForm(String id,
            @Pattern(regexp = "^[0-9]*$", message = "error.pattern.dependentsCount") String dependentsCount) {
        this.id = id;
        this.dependentsCount = dependentsCount;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDependentsCount() {
        return dependentsCount;
    }

    public void setDependentsCount(String dependentsCount) {
        this.dependentsCount = dependentsCount;
    }

    @Override
    public String toString() {
        return "UpdateEmployeeForm [id=" + id + ", dependentsCount=" + dependentsCount + "]";
    }

}
