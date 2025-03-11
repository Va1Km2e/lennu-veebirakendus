package com.mvaikm.lennu_veebirakendus.user;

import lombok.Data;

@Data
public class SignUpRequest {
    private final Long id;
    private final String username;
    private final String firstName;
    private final String lastName;
    private final String password;
}

