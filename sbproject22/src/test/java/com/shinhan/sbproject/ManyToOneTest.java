package com.shinhan.sbproject;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.querydsl.core.BooleanBuilder;
import com.shinhan.sbproject.repository.MemberRepository;
import com.shinhan.sbproject.repository.ProfileRepository;
import com.shinhan.sbproject.vo.MemberDTO;
import com.shinhan.sbproject.vo.MemberRole;
import com.shinhan.sbproject.vo.ProfileDTO;
import com.shinhan.sbproject.vo.QProfileDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class ManyToOneTest {

	@Autowired
	MemberRepository mrepo;

	@Autowired
	ProfileRepository prepo;

	// 단방향처리 : Profile을 통해서 Member가져오기 (Profile이 Member를 참조하므로)
	// (memberId를 알고 Profile가져오기 가능?)ManyToOne불가
	// pno를 가지고 Member를 가져오기 가능?Yes

	@Test
	void jpqlTest() {
		String mid = "user1";
		int count = mrepo.getProfileCountByMember(mid);
		log.info(mid + "의 프로파일 건수" + count);

		List<Object[]> result = mrepo.getProfileCountByMember();
		result.forEach(data -> {
			log.info("member의 profile 건수:" + data[0] + "------" + data[1]);
		});

		result = mrepo.getProfileCountByMember2();
		result.forEach(data -> {
			log.info("!!!!!!!!!!member의 profile 건수:" + data[0] + "------" + data[1]);
		});
	}

	// @Test
	void selectByMemeber() {
		MemberDTO member = MemberDTO.builder().mid("user1").build();
		List<ProfileDTO> plist = prepo.findByMember(member);
		plist.forEach(p -> log.info(p.toString()));

		System.out.println("-----------------");

		plist = prepo.findByCurrentYnIsTrue();
		plist.forEach(p -> log.info(p.toString()));

	}

	// @Test
	void eagerLazyTest() {
		prepo.findAll().forEach(p -> log.info(p.toString()));
	}

	// @Test
	void 동적() {
		BooleanBuilder builder = new BooleanBuilder();
		QProfileDTO profile = QProfileDTO.profileDTO;
		builder.and(profile.pno.gt(7L));
		builder.and(profile.fname.like("%파일%"));
		builder.and(profile.currentYn.eq(false));
		prepo.findAll(builder).forEach(pro -> {
			log.info(pro.toString());
		});
	}

	// @Test
	void 조인() {
		prepo.selectByjoin().forEach(arr -> {
			log.info(Arrays.toString(arr));
		});
	}

	// currentYn == true인 Profile조회하기
	// @Test
	void currentYn() {

		prepo.findByCurrentYnIsTrue().forEach(pro -> {
			log.info("Fname:" + pro.getFname());
			log.info("Mname:" + pro.getMember().getMname());
			assert pro.isCurrentYn();
			log.info("-------------------");
		});
	}

	// @Test
	void memberProfile() {
		Long pno = 10L;
		prepo.findById(pno).ifPresent(pro -> {
			log.info("Fname:" + pro.getFname());
			log.info("Mname:" + pro.getMember().getMname());
		});
		System.out.println("--------------------------");
		MemberDTO member = new MemberDTO();
		member.setMid("admin10");
		prepo.findByMember(member).forEach(pro -> {
			log.info("Fname:" + pro.getFname());
			log.info("Mname:" + pro.getMember().getMname());
		});
	}

	// 삭제
	// @Test
	void f5() {
		int selectId = 9;
//			prepo.findById(selectId).ifPresent(dept->{
//				drepo.delete(dept);
//			});
//			prepo.deleteById(7);
	}

	// @Test
	void profileSelect() {
		prepo.findAll().forEach(pro -> log.info(pro.toString()));
		log.info("건수:" + prepo.count());
	}

	// @Test
	void profileInsert() {
		// MemberDTO memberDTO = mrepo.findById("user1").orElse(null);
		// MemberDTO memberDTO = mrepo.findById("manager7").orElse(null);
		MemberDTO memberDTO = mrepo.findById("admin10").orElse(null);

		IntStream.rangeClosed(1, 5).forEach(i -> {
			ProfileDTO profile = ProfileDTO.builder().fname("어드민프로파일" + i).currentYn(i < 5 ? false : true)
					.member(memberDTO).build();
			prepo.save(profile);
		});
	}

	// @Test
	void memberSelect() {
		mrepo.findAll().forEach(m -> {
			log.info(m.toString());
		});
	}

	// @Test
	void memberInesrt() {
		// memeber 5명 insert 1~5: user권한 6~8: manager권한 9~10:admin권한

		IntStream.rangeClosed(1, 10).forEach(i -> {
			MemberDTO member = new MemberDTO();
			UUID uuid = UUID.randomUUID();
			member.setMpassword(uuid.toString().split("-")[0]);
			if (i <= 5) {
				member.setMid("user" + i);
				member.setMname("유저" + i);
				member.setMrole(MemberRole.USER);
			} else if (i >= 6 && i <= 8) {
				member.setMid("manager" + i);
				member.setMname("매니저" + i);
				member.setMrole(MemberRole.MANAGER);
			} else {

				member.setMid("admin" + i);
				member.setMname("어드민" + i);
				member.setMrole(MemberRole.ADMIN);
			}
			mrepo.save(member);

		});

	}

}
