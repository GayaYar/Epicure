package com.moveo.epicure.entity;

import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.usertype.UserType;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Permit {
    @Id
    @NotNull
    @Enumerated(value = EnumType.STRING)
    private PermittedType userType;
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<PermittedMethod> methods;

    public Permit(PermittedType userType) {
        this.userType = userType;
    }

}
