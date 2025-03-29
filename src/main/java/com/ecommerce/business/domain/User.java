package com.ecommerce.business.domain;

import com.ecommerce.database.entity.enums.Role;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class User {

    Integer id;
    String username;
    String email;
    String password;
    Role role;
}
