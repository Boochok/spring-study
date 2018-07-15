package com.epam.spring.core.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class StatisticsAspect {

    @Pointcut("execution(**.logEvent(..))")
    private void allLogEvent(){}

    @After("allLogEvent()")
    public void countLogEventCall(JoinPoint joinPoint){
        int fileLogCounter = 0;
        int cacheFileLogCounter = 0;
        int consoleLogCounter = 0;
        int combineLogCounter = 0;
        joinPoint.getKind();
    }
}
