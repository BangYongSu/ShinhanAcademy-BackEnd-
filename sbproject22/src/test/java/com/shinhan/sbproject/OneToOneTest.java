package com.shinhan.sbproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sbproject.repository.composite.UserCellPhoneRepository;
import com.shinhan.sbproject.repository.composite.UserCellPhoneVO2Repository;
import com.shinhan.sbproject.repository.composite.UserVO3Repository;
import com.shinhan.sbproject.repository.composite.UserVORepository;
import com.shinhan.sbproject.vo4.UserCellPhoneVO;
import com.shinhan.sbproject.vo4.UserCellPhoneVO2;
import com.shinhan.sbproject.vo4.UserCellPhoneVO3;
import com.shinhan.sbproject.vo4.UserVO;
import com.shinhan.sbproject.vo4.UserVO2;
import com.shinhan.sbproject.vo4.UserVO3;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class OneToOneTest {

	
	@Autowired
	UserCellPhoneRepository prepo;
	
	@Autowired
	UserVORepository urepo;
	
	@Autowired
	UserCellPhoneVO2Repository p2repo;
	@Autowired
	UserVO3Repository urepo3;
	
	@Test
	void f5() {
		UserCellPhoneVO3 p =UserCellPhoneVO3.builder()
				.phoneNumber("78789")
				.model("아이폰")
				.build();
		UserVO3 user = UserVO3.builder()
				.userid("second")
				.username("신촌")
				.phone(p)
				.build();
		p.setUser2(user);
		urepo3.save(user);
	}
	
	//@Test
	void f4() {
		p2repo.findAll().forEach(p->{
			log.info(p.toString());
		});
	}
	
	//@Test
	void f3() {
		UserVO2 user = UserVO2.builder()
				.userid("jj")
				.username("찐")
				.build();
		
		UserCellPhoneVO2 phone = UserCellPhoneVO2.builder()
				.phoneNumber("010-1234-5678")
				.model("갤럭시21")
				.user(user)
				.build();
		p2repo.save(phone);
		
	}
	//@Test
	void f2() {
		urepo.findById("zzilre").ifPresent(u->{
			log.info(u.toString());
		});;
	}
	
	//@Test
	void f1() {
		
		UserCellPhoneVO phone = UserCellPhoneVO.builder()
				.phoneNumber("010-1234-5678")
				.model("갤럭시21")
				.build();
		UserCellPhoneVO new_phone = prepo.save(phone);
		
		UserVO user = UserVO.builder()
				.userid("zzilre")
				.username("진례")
				.phone(new_phone)
				.build();
		urepo.save(user);
	}
	
	
}
