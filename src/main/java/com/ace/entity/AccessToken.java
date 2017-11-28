package com.ace.entity;

import lombok.Data;

/**
 * @author bamboo
 */
@Data
public class AccessToken {
    private String access_token;
    private String token_type;
    private long expires_in;
}
