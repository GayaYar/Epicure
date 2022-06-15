package com.moveo.epicure.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.moveo.epicure.exception.ProcessException;
import com.moveo.epicure.util.ObjectMapperSingleton;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permit {
    @Id
    @NotNull
    private String userType;
    @NotNull
    private String permissions;

    public Permit(String userType) {
        this.userType = userType;
    }

    public void setPermissionList(List<String> permissionList) {
        try {
            permissions = ObjectMapperSingleton.get().writeValueAsString(permissionList);
        } catch (JsonProcessingException e) {
            throw new ProcessException();
        }
    }

    public List<String> getPermissionList() {
        return ObjectMapperSingleton.get().readValue(permissions, new TypeReference<List<String>>() {});
    }

}
