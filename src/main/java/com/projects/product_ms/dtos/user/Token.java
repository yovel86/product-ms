package com.projects.product_ms.dtos.user;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
public class Token {
    private long id;
    private String value;
    private User user;
    private Date expiresAt;
    private boolean isActive;
}
