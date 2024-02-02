package com.shinhan.sbproject;

import static org.mockito.ArgumentMatchers.intThat;

import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import lombok.extern.slf4j.Slf4j;
@Slf4j
@SpringBootTest
public class DeptTest {
	
//	@Autowired
//	DeptRepository3 drepo;
//	
	
	//특정 managerId가 관리하는 부서들의 부서이름뒤에 "OK"라는 문자를 추가(수정)
	//@Test
//	void f9 () {
//		drepo.findByManagerId(7).forEach(dept->{
//			String dname = dept.getDepartmentName()+"OK";
//			dept.setDepartmentName(dname);
//			drepo.save(dept);
//		});
//	}
	
	//입력
	//@Test
//	void f1() {
//		
//		for(int i=100; i<200; i+=10) {
//			Dept2VO dept = Dept2VO.builder()
//					.departmentName("개발부"+i)
//					.locationId(i)
//					.managerId(i/20)
//					.build();
//		drepo.save(dept);
//		}
		/*
		 * IntStream.rangeClosed(1, 10).forEach(i ->{ DeptVO dept = DeptVO.builder()
		 * .departmentName("방용수"+i) .locationId((1700+100*i)) .managerId(i*100)
		 * .build(); drepo.save(dept); log.info("인포닷"+dept);
		 * 
		 * });
		 */
	}
	//한건조회
	//@Test
//	void f2() {
//		//세가지 방식
//		//1
//		Dept2VO dept1 = drepo.findById(5).orElse(null);
//		log.info(dept1.toString());
//		//2
//		Dept2VO dept2 = drepo.findById(8).get();
//		log.info(dept2.toString());
//		//3
//		int selectId = 9;
//		drepo.findById(selectId).ifPresentOrElse(dept->{
//			log.info("찾았다"+dept);},()->{log.info("못찾았다!")
//		;});
//	}
	//모두 조회
//	@Test
//	void f3() {
//		drepo.findAll().forEach(dept->{
//			log.info("전체말하는것이냐"+dept);
//		});
//		
//	}
	//수정
	//@Test
//	void f4() {
//		int selectId = 4;
//		drepo.findById(selectId).ifPresent(dept->{
//			log.info(dept.toString());
//			dept.setDepartmentName("내가 누구?");
//			dept.setLocationId(1222);
//			dept.setManagerId(1234);
//			drepo.save(dept);
//			log.info("수정됐음:"+dept);
//		});
//	}
//	//삭제
//	//@Test
//	void f5() {
//		int selectId = 9;
//		drepo.findById(selectId).ifPresent(dept->{
//			drepo.delete(dept);
//		});
//		drepo.deleteById(7);
//	}

