package com.epam.training.ticketservice.core.user.model;

import com.epam.training.ticketservice.core.user.persistence.User;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
@Builder
public class UserDto{
   private String username;
   private String password;
   private User.Role role;
    public UserDto(String username, String password, User.Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }
}

