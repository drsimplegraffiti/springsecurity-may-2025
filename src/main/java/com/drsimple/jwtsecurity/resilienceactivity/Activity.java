package com.drsimple.jwtsecurity.resilienceactivity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Activity {
    private int userId;
    private int id;
    private String title;
    private String body;
}
