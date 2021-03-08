package com.halo.mms.serv.core;

import cn.hutool.core.io.IoUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.halo.mms.common.halo.Gakki;
import com.halo.mms.common.plus.model.UserContactInfoDO;
import com.halo.mms.serv.exception.BadRequestException;
import com.halo.mms.serv.service.api.UserAuthService;
import com.halo.mms.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
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
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@Order(0)
public class AuthFilter extends OncePerRequestFilter {

    private static final List<String> NO_AUTH_URL = Arrays.asList(
        "/api/mms/api/user/auth",
        "/api/mms/health",
        "/api/mms/test",
        "/api/mms/data",
        "/test",
        "/favicon.ico"
    );

    @Autowired
    private UserAuthService userAuthService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("{}", request.getRequestURI());
        boolean needAuth = needAuth(request.getRequestURI());
        MmsServletRequestWrapper mmsServletRequestWrapper = putToken(request, !needAuth);

        try {
            filterChain.doFilter(mmsServletRequestWrapper, response);
        } catch (Exception e) {
            log.error("AuthFilter-accept-error", e);
            throw e;
        } finally {
            SelContextHolder.clearContext();
        }
    }

    private boolean needAuth(String uri) {
        for (String url : NO_AUTH_URL) {
            if (uri.startsWith(url)) {
                return false;
            }
        }
        return true;
    }

    private MmsServletRequestWrapper putToken(HttpServletRequest request, boolean isSoft) throws IOException {
        String token = request.getHeader("mms-auth-token");
        String body = IoUtil.read(request.getInputStream(), Charset.defaultCharset());

        if (isSoft && StringUtils.isEmpty(token)) {
            return new MmsServletRequestWrapper(request, body);
        }
        Gakki.expectTrue(StringUtils.isNotEmpty(token), new BadRequestException(400, "缺失token"));

        UserContactInfoDO userContactInfoDO = userAuthService.getUidByToken(token);
        Assert.notNull(userContactInfoDO, "invalid token");

        SelContextHolder.getContext().setUserInfo(userContactInfoDO);

        Map<String, Object> map = JsonUtil.fromJson(body, new TypeReference<Map<String, Object>>() {});
        map.put("uid", userContactInfoDO.getUuid());
        body = JsonUtil.toJson(map);
        MmsServletRequestWrapper mmsServletRequestWrapper = new MmsServletRequestWrapper(request, body);
        return mmsServletRequestWrapper;
    }
}
