package com.shinhan.sbproject.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString //(exclude = {"pno"}) ~ 를 제외한다.
@EqualsAndHashCode(of = {"pno","fname"} )//of ~에게만 줬으면 좋겠어
@Builder
@AllArgsConstructor
@NoArgsConstructor 
@Entity
@Table(name="tbl_profile")
public class ProfileDTO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long pno;
	private String fname;
	private boolean currentYn;
	//false:0  true:1
	
	//RDB와 유사, 참조하는쪽에 칼럼생성
	//EAGER: 즉시(default)
	//LAZY:게으름
	@ManyToOne(fetch = FetchType.EAGER)
	private MemberDTO member; //member_mid칼럼이 DB생성된다. 
	
	//직원, 부서 
	//직원이 부서를 참조한다. 
	//직원테이블에 부서의 키를 FK로 생성한다. 
	
	//ProfileDTO가 MemberDTO를 참조한다.
	//tbl_profile테이블에 tbl_members테이블의 키값 mid가 FK로 생성한다. 
}