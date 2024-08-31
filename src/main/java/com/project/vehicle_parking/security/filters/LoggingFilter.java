package com.project.vehicle_parking.security.filters;

import com.project.vehicle_parking.commons.JSON;
import com.project.vehicle_parking.commons.RequestDataProvider;
import com.project.vehicle_parking.commons.jwt.JWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class LoggingFilter implements Filter {

    RequestDataProvider requestDataProvider;

    public LoggingFilter(RequestDataProvider requestDataProvider) {
        this.requestDataProvider = requestDataProvider;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        long requestTime = System.currentTimeMillis();
        MDC.put("request_unique_id", "[" + requestDataProvider.getRequestHash() + "]");
        chain.doFilter(request, response);
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        if (httpServletRequest.getRequestURI().equalsIgnoreCase("/api/health/status")) {
            return;
        }
        Map<String, Object> data = new HashMap<>(
                Map.of(
                        "url", httpServletRequest.getRequestURI(),
                        "method", httpServletRequest.getMethod(),
                        "status", httpServletResponse.getStatus(),
                        "responseTime", System.currentTimeMillis() - requestTime,
                        "requestHash", requestDataProvider.getRequestHash(),
                        "clientIP", requestDataProvider.getClientIP(),
                        "clientAppPlatform", requestDataProvider.getAppPlatform(),
                        "clientAppVersion", requestDataProvider.getAppVersion(),
                        "clientDevice", requestDataProvider.getDevice()
                )
        );
        String token = httpServletRequest.getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
            token = httpServletRequest.getParameter("_authorization");
        }
        if (StringUtils.isNotBlank(token) && !((HttpServletRequest) request).getRequestURI().startsWith("/api/external/")) {
            String subject = JWT.decodeWithoutSecret(token).getSubject();
            if (StringUtils.isNotBlank(subject)) {
                data.put("authorizedSubject", subject);
            }
        }
        log.info("http request trace {}", JSON.objectToString(
                data
        ));
    }
}
