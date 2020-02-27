package com.icd.wksh.models;

import java.math.BigDecimal;

public class RolePermission {
    private BigDecimal permissionId;
    private BigDecimal roleId;
    private String permissionDescription;//permission_description

    @Override
    public String toString() {
        return "RolePermission{" +
                "permissionId=" + permissionId +
                ", roleId=" + roleId +
                ", permissionDescription='" + permissionDescription + '\'' +
                '}';
    }

    public BigDecimal getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(BigDecimal permissionId) {
        this.permissionId = permissionId;
    }

    public BigDecimal getRoleId() {
        return roleId;
    }

    public void setRoleId(BigDecimal roleId) {
        this.roleId = roleId;
    }

    public String getPermissionDescription() {
        return permissionDescription;
    }

    public void setPermissionDescription(String permissionDescription) {
        this.permissionDescription = permissionDescription;
    }
}
