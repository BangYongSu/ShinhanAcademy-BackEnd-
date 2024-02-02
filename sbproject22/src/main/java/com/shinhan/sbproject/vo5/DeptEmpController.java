package com.shinhan.sbproject.vo5;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sbproject.security.MemberService;
import com.shinhan.sbproject.webboard.PageVO;
import com.shinhan.sbproject.webboard.WebBoard;
import com.shinhan.sbproject.webboard.WebBoardRepository;

import lombok.RequiredArgsConstructor;



@RestController
@RequiredArgsConstructor
@RequestMapping("/emp/dept")
public class DeptEmpController {

	
	@Autowired
	EmpRepository3 emprepo;
	
	@Autowired
	DeptRepository3 deptrepo;
	
	@DeleteMapping("/delete.do/{employeeId}")
	public String f6(@PathVariable("employeeId") Integer employeeId) {
		emprepo.deleteById(employeeId);
		return "삭제성공";
	}
	
	@GetMapping("/deptemp")
	public String f1(Model model) {
	
		return "dept";
	}
	
	@GetMapping("/list.do")
	public List<EmpVO3> f2() {
		
		return (List<EmpVO3>) emprepo.findAll();
	}
	@PostMapping("/insert.do")
    public Integer f5(@RequestBody EmpVO3 emp) {
        
        EmpVO3 newemp = emprepo.save(emp);
        
        return newemp.getEmployeeId()!=null?0:-1;
    }
	 @GetMapping("/detail.do/{employeeId}")
	    public EmpVO3 f2(@PathVariable("employeeId") Integer employeeId, PageVO page
	                ) {
	        return emprepo.findById(employeeId).orElse(null);
	    }
	 
		@PutMapping("/update.do")
	    public Integer f3( @RequestBody EmpVO3 emp, PageVO page) {
	        
			EmpVO3 updateEmp = emprepo.save(emp);
	        
	        return updateEmp==null?-1:0;
	    }
}
