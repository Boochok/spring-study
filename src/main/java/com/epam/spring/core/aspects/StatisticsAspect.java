package com.epam.spring.core.aspects;

import com.epam.spring.core.beans.Event;
import com.epam.spring.core.loggers.ConsoleEventLogger;
import com.epam.spring.core.loggers.FileEventLogger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class StatisticsAspect {

    private Map<Class<?>, Integer> counter = new HashMap<>();
    private final static int MAX_CONSOLE_CALL_NUMBER = 2;
    @Autowired
    private FileEventLogger fileEventLogger;

    @Pointcut("execution(* com.epam.spring.core.loggers.EventLogger.logEvent(..))")
    private void allLogEvent() {
    }

    @AfterReturning("allLogEvent()")
    public void countLogEventCall(JoinPoint joinPoint) {
        Class<?> loggerName = joinPoint.getTarget().getClass();
        counter.compute(loggerName, (logger, prev) -> prev != null ? prev + 1 : 1);
    }

    @Around("com.epam.spring.core.aspects.LoggingAspect.consoleLogEvent() && args(evt)")
    public void aroundAdviceMethod(ProceedingJoinPoint jp, Object evt) throws Throwable {
        Integer integer = counter.get(ConsoleEventLogger.class);
        //todo worked incorrect
        if (integer != null && integer < MAX_CONSOLE_CALL_NUMBER)
            jp.proceed(new Object[]{evt});
        else
            fileEventLogger.logEvent((Event) evt);

    }

    public Map<Class<?>, Integer> getCounter() {
        return counter;
    }
}
