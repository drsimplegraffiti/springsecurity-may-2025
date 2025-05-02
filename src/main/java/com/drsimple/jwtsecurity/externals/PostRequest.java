package com.drsimple.jwtsecurity.externals;

import lombok.Data;

@Data
public class PostRequest {
    public int userId;
    public String title;
    public String body;
}
