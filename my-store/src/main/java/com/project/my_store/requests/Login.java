package com.project.my_store.requests;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Login {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
