package com.mvaikm.lennu_veebirakendus.user;

import lombok.Data;

@Data
public class UserDTO {
    private int id;
    private String username;
    private String firstName;
    private String lastName;
    private String password;
}
