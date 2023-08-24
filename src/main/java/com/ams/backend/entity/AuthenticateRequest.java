package com.ams.backend.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class AuthenticateRequest {
    String mail;
    String password;
}
