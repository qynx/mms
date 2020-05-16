package com.halo.mms.serv.core;

import com.halo.mms.serv.service.api.UserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
@Order(0)
public class AuthFilter extends OncePerRequestFilter {

    @Autowired
    private UserAuthService userAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("{}", request.getRequestURI());
        if (request.getRequestURI().equals("/api/mms/user/auth")) {
            filterChain.doFilter(request, response);
            return;
        }

        // String remoteAddr = ServletUtil.getClientIP(request);
        String token = request.getHeader("mms-auth-token");

        Assert.hasText(token, "Bad Request");

        String uid = userAuthService.getUidByToken(token);
        Assert.notNull(uid, "invalid token");

        SelContextHolder.getContext().setUserUuid(uid);

        try  {
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.error("AuthFilter-accept-error", e);
            throw e;
        } finally {
            log.info("do clear context");
            SelContextHolder.clearContext();
        }


    }
}
