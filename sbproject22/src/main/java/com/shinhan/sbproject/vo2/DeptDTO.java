package com.shinhan.sbproject.vo2;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name= "tbl_dept")
public class DeptDTO {

	@Id
	Long deptId;
	String deptName;
	
	//하나의 부서에 직원이 여러명
	//CASCADE 나의 동작에 대해서 내자식도 영향을 줌
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinColumn(name = "dept_id")
	List<EmpDTO> emplist;
	
}
