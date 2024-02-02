package com.shinhan.sbproject.advice;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


//보조 업무
//<context:component-scan base-package="com.shinhan" />
@Component
//<aop:aspectj-autoproxy/>
//aspect : pointcut과 advise를 합쳐 놓은 코드(보조업무)
@Aspect
@Order(2)

public class LoggingAdvice {
	
	//1.around
	//2.before
	//3.주업무
	//4.보조업무
	//5.after returning
	//6.after
	//7.around
	
	//@Pointcut("execution (* select*(..))")
	@Pointcut("within(com.shinhan.sbproject.webboard.WebBoardController)")
	//메소드 이름은 상관없음, 메소드 형태를 만들고 pointcut을 사용
	public void aaa(){}
	
	@AfterReturning("aaa()")
	public void f3(JoinPoint jp){
		System.out.println("afterReturning :" + jp.getSignature().getName());
	}
	
	@AfterThrowing("aaa()")
	public void f4(JoinPoint jp){
		System.out.println("afterThrowing :" + jp.getSignature().getName());
	}	
	
	@Before("aaa()")
	public void f1(JoinPoint jp) {
		System.out.println("before :" + jp.getSignature().getName());
	}
	
	@After("aaa()")
	public void f2(JoinPoint jp) {
		System.out.println("after :" + jp.getSignature().getName());
	}
	
	@Around("aaa()")
	public Object aroundMethod2(ProceedingJoinPoint jp) throws Throwable {
		//보조업무
		System.out.println("============ LoggingAdvice 보조업무 ============");
		System.out.println(jp.getSignature().getName()+"메소드 호출 전");
		
		//주업무
		System.out.println("============ 주업무 수행시작 ============");
		Object obj = jp.proceed();
		System.out.println("============ 주업무 수행완료 ============");
		
		//보조업무
		System.out.println(jp.getSignature().getName()+"메소드 호출 후");
		System.out.println("==============================================");
		return obj;
	}
	
}
