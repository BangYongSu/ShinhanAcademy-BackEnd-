package com.shinhan.sbproject.vo4;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_user3")
public class UserVO3 {

	@Id
	@Column(name = "user_id")
	String userid;

	String username;

//양방향...주테이블의 키를 식별자사용하는 경우 
	@OneToOne(mappedBy = "user2", cascade = CascadeType.ALL)
	UserCellPhoneVO3 phone;

}
