package com.anonymous.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class AuthOutput {

    private String accessToken;
    private String refreshToken;

}
