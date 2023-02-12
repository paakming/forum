package com.wbm.forum.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author：Ming
 * @Date: 2022/11/8 22:04
 */
public class WebUtils {
    public static String renderString(HttpServletResponse response,String json) {
        try {
            response.setStatus(200);
            response.setContentType("application/json");
            response.setCharacterEncoding("utf-8");
            response.getWriter().print(json);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }
}
