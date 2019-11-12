package com.idemia.assetmanagement.controller.response;

import com.idemia.assetmanagement.model.User;
import com.idemia.assetmanagement.security.UserPrincipal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JwtAuthenticationResponse {

    private String accessToken;

    private String tokenType = "Bearer";

    private UserPrincipal user;

    public JwtAuthenticationResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
