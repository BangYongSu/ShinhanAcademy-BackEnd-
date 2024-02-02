package com.shinhan.sbproject.advice;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
//순서가 낮으면 먼저 실행
@Order(1)

public class StopWatchAdvice {
	
						//(..) = 상관없다라는 뜻
	@Pointcut("execution(* f1(..))")
	public void mypointcut1() {
		//로직없음.. 함수형태일때만 가능한 어노테이션
	}
	
	@Pointcut("execution(* select*())")
	public void mypointcut2() {
		//로직없음.. 함수형태일때만 가능한 어노테이션
	}
	
	@Around("mypointcut1()")
	public Object invoke(ProceedingJoinPoint jp) throws Throwable {
		String targetMethodName = jp.getSignature().toShortString();
		
		//보조업무
		System.out.println("****** StopWatch" + targetMethodName + "메서드 호출 전");
		StopWatch watch = new StopWatch("계산시간");
		watch.start();
		//주업무를 수행한다. 
		Object object = jp.proceed();
		//보조업무
		//4.AfterC
		System.out.println("*****" + targetMethodName + "메서드 호출 후");
		watch.stop();
		System.out.println("주업무 수행 시간:" + watch.getTotalTimeMillis());
		System.out.println(watch.prettyPrint());
		return object;
	}
}
