package com.moveo.epicure.entity;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<PermittedMethod> methods;

    public Permit(String userType) {
        this.userType = userType;
    }

}
