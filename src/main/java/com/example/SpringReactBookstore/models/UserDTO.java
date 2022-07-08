package com.example.SpringReactBookstore.models;

import lombok.*;
import org.springframework.lang.NonNull;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private UUID id;

    @NonNull
    private String name;

    @NonNull
    private String email;

    @NonNull
    private String password;
}
