package com.example.journeyGenie.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class FeignClientInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }

        HttpServletRequest request =
                (HttpServletRequest) requestAttributes.resolveReference(RequestAttributes.REFERENCE_REQUEST);

        if (request == null) {
            return;
        }

        // Forward cookies
        String cookie = request.getHeader("Cookie");
        if (cookie != null) {
            requestTemplate.header("Cookie", cookie);
        }

        // Forward Authorization if present
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            requestTemplate.header("Authorization", authHeader);
        }
    }
}
