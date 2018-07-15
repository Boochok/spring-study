package com.epam.spring.core.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class LoggingAspect {

    @Pointcut("execution(* com.epam.spring.core.loggers.EventLogger.logEvent(..)))")
    public void allLogEventMethods() {
    }

    @Pointcut("allLogEventMethods() && within(*.*File*Logger)")
    private void logEventInsideFileLoggers() {
    }

    @Before("allLogEventMethods()")//can be @Before("execution(**.logEvent(..))") instead of pointcut but pointcut can't be reused
    public void logBefore(JoinPoint joinPoint) {
        //todo add logger
        System.out.println(String.format("BEFORE: kind - %s, signature - %s, target - %s",
                joinPoint.getKind(), joinPoint.getSignature().getName(),
                joinPoint.getTarget().getClass().getSimpleName()));
    }

    @AfterReturning(value = "allLogEventMethods()", returning = "retVal")
    public void logAfterReturning(Object retVal){
//        todo log
        System.out.println("returned value = " + retVal);
    }

    @AfterThrowing(value = "allLogEventMethods()", throwing = "ex")
    public void logAfterThrowing(Throwable ex){
//        todo log
        System.out.println(ex);
    }
}
