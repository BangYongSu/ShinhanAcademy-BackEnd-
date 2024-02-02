package com.shinhan.sbproject.vo4;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


//주
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "tbl_user2")
public class UserVO2 {

	@Id
	@Column(name = "user_id")
	String userid;
	String username;

//대상테이블에서 참조하기  

}
