package com.smen.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KeycloakLoginDTO {
    @NonNull
    private String username;
    @NonNull
    private String password;
}
