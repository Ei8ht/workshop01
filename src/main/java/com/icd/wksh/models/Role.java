package com.icd.wksh.models;

import java.math.BigDecimal;

public class Role {
    private BigDecimal roleId;
    private String roleDesc;

    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", roleDesc='" + roleDesc + '\'' +
                '}';
    }

    public BigDecimal getRoleId() {
        return roleId;
    }

    public void setRoleId(BigDecimal roleId) {
        this.roleId = roleId;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }
}
