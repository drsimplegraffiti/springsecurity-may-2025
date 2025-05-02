package com.drsimple.jwtsecurity.externals;


import lombok.Data;

@Data
public class PostResponse {
    public int id;
    public int userId;
    public String title;
    public String body;

    @Override
    public String toString() {
        return "PostResponse{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
