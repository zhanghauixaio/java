package com.seven.spring.bo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface TestBo {
    void download(HttpServletRequest request, HttpServletResponse response);
}
