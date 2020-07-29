package com.example.restful.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class HttpResponse {
    private int code;
    private String content;

    @Override
    public String toString(){
        return new StringBuilder("[ code = ").append(code)
                .append(" , content = ").append(content).append(" ]").toString();
    }
}
