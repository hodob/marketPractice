package com.hodob.marketPractice.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieService {

    public static Cookie createCookie(String cookieName, String value){
        Cookie token = new Cookie(cookieName,value);
        token.setHttpOnly(true);
        token.setMaxAge((int) 2 * 360 * 1000);
        token.setPath("/");
        return token;
    }
    public static Cookie deleteCookie(String cookieName){
        Cookie token = new Cookie(cookieName,"");
        token.setHttpOnly(true);
        token.setMaxAge(0);
        token.setPath("/");
        return token;
    }

    public static Cookie getCookie(HttpServletRequest req, String cookieName){
        final Cookie[] cookies = req.getCookies();
        if(cookies==null) return null;
        for(Cookie cookie : cookies){
            if(cookie.getName().equals(cookieName))
                return cookie;
        }
        return null;
    }
        // 로그아웃시 해당 cookie를 삭제하도록 하자
    public static void resetToken(HttpServletResponse response){
        Cookie c = deleteCookie("myJwtToken");
        response.addCookie(c);
    }

}