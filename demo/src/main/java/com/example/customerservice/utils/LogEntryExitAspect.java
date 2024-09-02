package com.example.customerservice.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LogEntryExitAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogEntryExitAspect.class);

    @Around("execution(* com.example.customerservice.demo..*(..))") 
    public Object logEntryExit(ProceedingJoinPoint joinPoint) throws Throwable {
        // Log method entry
        logger.info("Entering method: {} with arguments: {}", joinPoint.getSignature(), joinPoint.getArgs());

        try {
            // Proceed with method execution
            Object result = joinPoint.proceed();

            // Log method exit
            logger.info("Exiting method: {} with result: {}", joinPoint.getSignature(), result);

            return result;
        } catch (Throwable throwable) {
            // Log exception if method throws one
            logger.error("Exception in method: {} with message: {}", joinPoint.getSignature(), throwable.getMessage());
            throw throwable;
        }
    }
}

