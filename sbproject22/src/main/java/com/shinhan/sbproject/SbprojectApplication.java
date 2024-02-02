package com.shinhan.sbproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;



/*
 * 기본패키지(기본패키지 하위는 추가코드 없음) : com.shinhan.sbproject
 * 그외의 폴더라면 @ComponentScan 추가한다
 * JpaRepository가 기본패키지 하위에 
 */
@EnableAspectJAutoProxy //
@ComponentScan(basePackages={"com.shinhan.firstzone","com.shinhan.sbproject"}) 
@SpringBootApplication
@EnableJpaRepositories(basePackages ={"com.shinhan.sbproject","com.shinhan.firstzone"})//Entity를 찾음
public class SbprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SbprojectApplication.class, args);
	}

}
