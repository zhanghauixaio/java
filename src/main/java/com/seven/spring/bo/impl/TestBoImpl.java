package com.seven.spring.bo.impl;

import com.seven.spring.bo.TestBo;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Service
public class TestBoImpl implements TestBo {
    @Override
    public void download(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Expose-Headers", "X-myHeader");
        response.setHeader("X-myHeader", "limao.zhang");
        response.setContentType(MediaType.MULTIPART_FORM_DATA_VALUE);
        response.setHeader("Content-Disposition", "inline");
        // response.addHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()));

    }
}
