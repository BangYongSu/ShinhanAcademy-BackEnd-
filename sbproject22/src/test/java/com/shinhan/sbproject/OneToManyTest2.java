package com.shinhan.sbproject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sbproject.repository.DeptRepository2;
import com.shinhan.sbproject.repository.EmpRepository2;
import com.shinhan.sbproject.vo2.DeptDTO;
import com.shinhan.sbproject.vo2.EmpDTO;
import com.shinhan.sbproject.vo5.EmpRepository3;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class OneToManyTest2 {

	@Autowired
	EmpRepository2 emprepo;
	@Autowired
	DeptRepository2 deptrepo;
	
	@Autowired
	EmpRepository3 erepo;
	
	
	//@Test
	void empUpdate() {
		//40번 직원은 100부서로 배치
		//50번 직원은 신규부서 200번에 배치
		EmpDTO emp40 = emprepo.findById(40L).orElse(null);
		EmpDTO emp50 = emprepo.findById(50L).orElse(null);
		deptrepo.findById(100L).ifPresent(dept->{
			if(emp40!=null)  dept.getEmplist().add(emp40);
			deptrepo.save(dept);
			
		//deptrepo.save(null);
		});
		List<EmpDTO> elist = new ArrayList<>();
		elist.add(emp50);
		DeptDTO dept = DeptDTO.builder()
				.deptId(200L)
				.deptName("TF팀")
				.emplist(elist)
				.build();
		deptrepo.save(dept);
	}
	
	@Test
	void selectEmp() {
		emprepo.findAll().forEach(emp->log.info(emp.toString()));
	}
	
	//@Test
	void insertEmp() {
		EmpDTO emp1 = EmpDTO.builder().empId(40L).empName("경력사원").build();
		EmpDTO emp2 = EmpDTO.builder().empId(50L).empName("김부장").build();
		emprepo.save(emp1); emprepo.save(emp2);
	}
	
	//@Test
	void selectDept() {
		deptrepo.findAll().forEach(
				d->{log.info(d.toString());
				d.getEmplist().forEach(emp->{
					log.info("직원정보:"+emp);
				});
				
				});
	}
	
	
	@Test
	void insertDeptEmp() {
		List<EmpDTO> elist = new ArrayList<>();
		IntStream.rangeClosed(1, 3).forEach(i->{
			EmpDTO emp = EmpDTO.builder()
			.empId(i*10L)
			.empName("우수사원"+i)
			.build();
			elist.add(emp);
		});
		DeptDTO dept = DeptDTO.builder()
				.deptId(100L)
				.deptName("개발부")
				.emplist(elist)
				.build();
		deptrepo.save(dept);
	}
}
