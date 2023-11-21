package com.mehmetozanguven.capturelongrunningmethod.capture;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

@Aspect
@Component
public class LongRunningExecutionAspect {

    private Logger logger(JoinPoint joinPoint) {
        return LoggerFactory.getLogger(joinPoint.getSignature().getDeclaringTypeName());
    }

    @Around("@annotation(LongRunningExecution)")
    public Object logMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger log = logger(joinPoint);

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = joinPoint.getTarget()
                .getClass()
                .getMethod(signature.getMethod().getName(), signature.getMethod().getParameterTypes());
        LongRunningExecution longRunningExecution = method.getAnnotation(LongRunningExecution.class);

        boolean isRequestLogging = Objects.nonNull(longRunningExecution) && longRunningExecution.request();
        boolean isResponseLogging = Objects.nonNull(longRunningExecution) && longRunningExecution.response();
        boolean isMaxExecutionTimeFilter = Objects.nonNull(longRunningExecution) && longRunningExecution.maxExecutionTime() > -1L;

        String methodPath = joinPoint.getSignature().getDeclaringTypeName() + "#" + joinPoint.getSignature().getName();

        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        final long methodExecutionTimeInMs = end - start;

        if (isMaxExecutionTimeFilter) {
            if (methodExecutionTimeInMs > longRunningExecution.maxExecutionTime()) {
                if (isRequestLogging) {
                    log.info("LongRunningExecution: {} - Request: {} - Response time {} ms - Expected Response time {} ms", methodPath, joinPoint.getArgs(), methodExecutionTimeInMs, longRunningExecution.maxExecutionTime());
                }

                if (isResponseLogging) {
                    log.info("LongRunningExecution: {} - Response: {} - Response time {} ms - Expected Response time {} ms", methodPath, result, methodExecutionTimeInMs, longRunningExecution.maxExecutionTime());
                }
            }
        } else {
            if (isRequestLogging) {
                log.info("LongRunningExecution: {} - Request: {} - Response time {} ms", methodPath, joinPoint.getArgs(), methodExecutionTimeInMs);
            }

            if (isResponseLogging) {
                log.info("LongRunningExecution: {} - Response: {} - Response time {} ms", methodPath, result, methodExecutionTimeInMs);
            }
        }
        return result;
     }
}
