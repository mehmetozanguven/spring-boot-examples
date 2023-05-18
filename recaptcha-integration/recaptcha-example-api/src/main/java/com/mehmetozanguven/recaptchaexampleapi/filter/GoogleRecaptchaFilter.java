package com.mehmetozanguven.recaptchaexampleapi.filter;

import com.mehmetozanguven.recaptchaexampleapi.service.GoogleRecaptchaRequest;
import com.mehmetozanguven.recaptchaexampleapi.service.GoogleRecaptchaResponse;
import com.mehmetozanguven.recaptchaexampleapi.service.GoogleRecaptchaService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

/**
 * GoogleRecaptchaFilter class is responsible for filtering requests,
 * extracting the reCAPTCHA response token from the request header,
 * verifying the reCAPTCHA response using the googleRecaptchaService,
 * and either allowing the request to proceed
 * or generating an error response based on the verification result.
 */
@RequiredArgsConstructor
@Slf4j
public class GoogleRecaptchaFilter extends OncePerRequestFilter {
        private final GoogleRecaptchaService googleRecaptchaService;
        private final HandlerExceptionResolver resolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String googleResponse = request.getHeader("google-recaptcha-token");
            if (StringUtils.isBlank(googleResponse)) {
                log.warn("Google response is null for the request :: {}", request.getRequestURL());
            }
            GoogleRecaptchaRequest googleRecaptchaRequest = new GoogleRecaptchaRequest();
            googleRecaptchaRequest.setResponse(googleResponse);
            googleRecaptchaRequest.setRemoteip(request.getRemoteAddr());

            GoogleRecaptchaResponse googleRecaptchaResponse = googleRecaptchaService.verifyRecaptcha(googleRecaptchaRequest);
            if (!googleRecaptchaResponse.isSuccess()) {
                log.error("Google response is not success :: {}", googleRecaptchaResponse);
                throw new RuntimeException("We have detected unusual activities from your browser. Please login again after a few minutes later");
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            resolver.resolveException(request, response, null, ex);
        }
    }

    /**
     * shouldNotFilter checks if the request URL contains the string "login"
     * and returns **false** if it does.
     * This means that the filter will be run for requests related to the login process.
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return !request.getRequestURL().toString().contains("login");
    }
}
