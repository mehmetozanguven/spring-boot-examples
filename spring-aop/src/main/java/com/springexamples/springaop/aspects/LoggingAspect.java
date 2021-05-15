package com.springexamples.springaop.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggingAspect.class);

//    @Around("execution(String homeController())")
//    private Object loggingAroundAspectForHomeController(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
//        LOGGER.info("aspect AROUND advice for signature name: {}", proceedingJoinPoint.getSignature().getName());
//        try{
//            return proceedingJoinPoint.proceed();
//        }catch (Exception e){
//            return "homeNotFound";
//        }
//    }
}


//    @Before("execution(String homeController())")
//    private void loggingBeforeAspectForHomeController(){
//        LOGGER.info("aspect BEFORE advice");
//    }


//    @After("execution(String homeController())")
//    private void loggingAfterAspectForHomeController(){
//        LOGGER.info("aspect AFTER advice");
//    }
//
//    @AfterReturning("execution(String homeController())")
//    private void loggingAfterReturningAspectForHomeController(){
//        LOGGER.info("aspect AFTER-RETURNING if calling method was finished successfully");
//    }
//
//    @AfterThrowing(pointcut = "execution(String homeController())", throwing = "exception")
//    private void homeExceptionAdvice(Throwable exception){
//        LOGGER.error("aspect AFTER-THROWING: ", exception);
//    }
