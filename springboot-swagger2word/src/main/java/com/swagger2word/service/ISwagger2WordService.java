package com.swagger2word.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

public interface ISwagger2WordService {
    Map<String,Object> tableList(String swaggerUrl);

    Map<String, Object> tableListFromString(String jsonStr);

    Map<String, Object> tableList(MultipartFile jsonFile);
}
