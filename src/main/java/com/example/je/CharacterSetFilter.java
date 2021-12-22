package com.example.je;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;


public class CharacterSetFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain next) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
        response.setCharacterEncoding("UTF-8");
        next.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
