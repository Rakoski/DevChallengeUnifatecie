package com.example.unifateciedev.api.utils;

import com.example.unifateciedev.api.CursoController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class InterceptadorIdUsuario implements HandlerInterceptor {

    @Autowired
    private CursoController cursoController;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String email = request.getParameter("email");
        if (email != null) {
            Long id_usuario = cursoController.pegueUserIdPeloEmail(email);
            cursoController.setIdUsuario(id_usuario);
        }
        return true;
    }

}
