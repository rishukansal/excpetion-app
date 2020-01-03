package com.practice.micro;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.context.annotation.Configuration;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Configuration
@Slf4j
public class LoggingAspect {
	
	
	 @Before("execution(* com.practice.micro.controller.*.*(..)) || " +
	 "execution(* com.practice.micro.repo.*.*(..)) || " +
	 "execution(* com.practice.micro.service.*.*(..))" )
	public void logArguments(JoinPoint joinPoint) {
		Object[] args = joinPoint.getArgs();
		String className = joinPoint.getSignature().getDeclaringTypeName();
		String methodName = joinPoint.getSignature().getName();
		log.info(" {}.{}() args {}",className,methodName,args);

	}
	
	@AfterReturning(
			pointcut="execution(*  com.practice.micro.controller.*.*(..)) || "
					+ "execution(* com.practice.micro.repo.*.*(..)) || "
					+ "execution(* com.practice.micro.service.*.*(..)) ", 
			returning="retVal")
	public void logReturnVal(JoinPoint joinPoint, Object retVal) {
		String className = joinPoint.getSignature().getDeclaringTypeName();
		String methodName = joinPoint.getSignature().getName();
		log.info(" {}.{}() return val {}",className,methodName,retVal);
	}
}
