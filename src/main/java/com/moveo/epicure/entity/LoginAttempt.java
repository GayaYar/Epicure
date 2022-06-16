package com.moveo.epicure.entity;

import java.time.LocalDateTime;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class LoginAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotNull
    private String mail;
    @NotNull
    private LocalDateTime time;

    public LoginAttempt(String mail, LocalDateTime time) {
        this.mail = mail;
        this.time = time;
    }
}
