package com.springexamples.springaop.aspects;

import com.springexamples.springaop.annotations.SimpleAnnotation;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Aspect
@Component
public class SimpleAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(SimpleAspect.class);

//    @Around("@within(simpleAnnotation)")
//    public Object simpleAnnotationForClassLevel(ProceedingJoinPoint proceedingJoinPoint, SimpleAnnotation simpleAnnotation) throws Throwable {
//        return simpleAnnotation(proceedingJoinPoint, simpleAnnotation);
//    }
//
//    @Around(value = "@annotation(simpleAnnotation)", argNames = "simpleAnnotation")
//    public Object simpleAnnotation(ProceedingJoinPoint proceedingJoinPoint, SimpleAnnotation simpleAnnotation) throws Throwable{
//        LOGGER.info("Simple annotation value: {}, ASPECT-LOG {}", simpleAnnotation.isAllowed(),proceedingJoinPoint.getSignature().getName());
//        return proceedingJoinPoint.proceed();
//    }

    @Around(value = "@within(simpleAnnotation) || @annotation(simpleAnnotation) || @within(simpleAnnotation)", argNames = "simpleAnnotation")
    public Object simpleAnnotation(ProceedingJoinPoint proceedingJoinPoint, SimpleAnnotation simpleAnnotation) throws Throwable{
        LOGGER.info("Simple annotation value: {}, ASPECT-LOG {}", simpleAnnotation.isAllowed(),proceedingJoinPoint.getSignature().getName());
        return proceedingJoinPoint.proceed();
    }
}
