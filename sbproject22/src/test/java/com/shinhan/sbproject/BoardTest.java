package com.shinhan.sbproject;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.BooleanBuilder;
import com.shinhan.firstzone.repository.BoardRepository;
import com.shinhan.sbproject.vo.BoardVO;
import com.shinhan.sbproject.vo.QBoardVO;

import lombok.extern.slf4j.Slf4j;
@Slf4j  //로거 남기기
@SpringBootTest  //Test 환경
//@RequiredArgsConstructor
public class BoardTest {
	
	@Autowired
	BoardRepository brepo;
	//이게 마음에 안들면?
	
	//final BoardRepository brepo;
	@Test
	void f20() {
		BooleanBuilder builder = new BooleanBuilder();
		QBoardVO board = QBoardVO.boardVO;
		
		Long bno = 5L;
		String writer ="user3";
		String content = "%억%";
		if(bno!=null)
			builder.and(board.bno.gt(bno));
		if(writer!=null)
			builder.and(board.writer.eq(writer));
		if(content!=null)
			builder.and(board.content.like(content));
//		builder.and(board.bno.gt(5L));
//		builder.and(board.writer.eq("user3"));
//		builder.and(board.content.like("%억%"));
		log.info(builder.toString());
		//boardVO.bno > 5 && boardVO.writer = user3 && boardVO.content like %억난%
		//동적SQL만들기
		List<BoardVO> blist= (List<BoardVO>) brepo.findAll(builder);
		blist.forEach(b->log.info(b.toString()));
	}
	
	
	//@Test
	void f19() {
		brepo.selectByWriter("user3").forEach(sarr->{
			log.info("title:" + sarr[0]);
			log.info("contnet:" + sarr[1]);
			log.info("writer:" + sarr[2]);
			log.info("bno:" + sarr[3]);
			log.info("regDate:" + sarr[4]);
			log.info("--------------------");
		});
	}
	//@Test
	void f18() {
		//List<BoardVO> blist = brepo.selectByTitleAndWriter2(5L, "java", "user3");
		//List<BoardVO> blist = brepo.selectByTitleAndWriter5(5L, "java", "user3");
		List<BoardVO> blist = brepo.selectByTitleAndWriter5(5L, "java", "user3");
		//List<Object[]> blist = brepo.selectByTitleAndWriter6(5L, "java", "user3");
		//blist.forEach(arr->log.info(Arrays.toString(arr)));
		blist.forEach(b->log.info(b.toString()));
	}
	
	//@Test
	void f17() {
		//Pageable paging = PageRequest.of(1, 5);
		//Pageable paging = PageRequest.of(1, 5, Sort.by(Sort.Direction.DESC,"writer","title"));
		Pageable paging = PageRequest.of(1, 5, Sort.by("writer").ascending());
		
		
		//Page<BoardVO> result = brepo.findAll(paging);
		Page<BoardVO> result = brepo.findByBnoBetween(10L,100L,paging);
		
		log.info("페이지사이즈 getSize: "+result.getSize());
		log.info("현재페이지 getNumber: "+result.getNumber());
		log.info("getNumberOfElements: "+result.getNumberOfElements());
		log.info("건수 getTotalElements: "+result.getTotalElements());
		log.info("페이지건수 getTotalPages: "+result.getTotalPages());
		log.info("내용 getContent: "+result.getContent());
		log.info("getPageable: "+result.getPageable());
		log.info("getSort: "+result.getSort());
		
		result.getContent().forEach(b -> log.info(b.toString()));
	}
	//@Test
	void f16() {
		Pageable paging = PageRequest.of(1, 6); //page, pagesize
		brepo.findByBnoGreaterThan(5L, paging).forEach(b->log.info(b.toString()));
	}
	
	//@Test
	void f15() {
		String writer = "user3";
		int cnt = brepo.countByWriter(writer);
		log.info("user3이 작성한 board건수:" + cnt);
		brepo.findByWriter(writer).forEach(b->{
			log.info(b.toString());
		});
	}
	
	//@Test
	void f11() {
		List<BoardVO> blist = brepo.findByTitleContainingAndBnoGreaterThanAndWriterLike("a", 70L,"%2");
	blist.forEach(b->{
		log.info("findByTitleContainingAndBnoGreaterThanAndWriterLike조건조회"+b.toString());
	});	
	}
	//@Test
	void f10() {
		List<BoardVO> blist = brepo.findByContentContaining("억");
		blist.forEach(b->{
			log.info("ContentContaining조건조회"+b.toString());
		});
	}
	
	//@Test
	void f9() {
		List<BoardVO> blist = brepo.findByContentLike("%억%");
		blist.forEach(b->{
			log.info("ContentLike"+b.toString());
		});
		
			
	}
	
	//@Test
	void f8() {
		brepo.findByBnoGreaterThan(50L).forEach(b->{
			log.info("bno조건조회"+b.toString());
		});
	}
	
	//@Test
	void f7() {
		List<BoardVO> blist = brepo.findByWriter("user3");
		List<BoardVO> blist2 = brepo.findByContent("기억안나요");
		blist.forEach(board->{
			log.info(board.toString());
		});
		blist2.forEach(board->{
			log.info(board.toString());
		});
	}
	//@Test
	void f6() {
		log.info("Board건수"+brepo.count());
		}
	//@Test
	void f5() {
		//객체지우기
		Long searchId = 19L;
		brepo.findById(searchId).ifPresent(b->{
			brepo.delete(b);
		});
		//iD로 지우기
		brepo.deleteById(18L);
	}
	//@Test
	void f4() {
		Long searchId = 10L;
		brepo.findById(searchId).ifPresent(b->{
			b.setTitle("화요일...");
			b.setContent("집가고싶어요...!");
			b.setWriter("용수");
			
			BoardVO update_board = brepo.save(b);
			log.info("원본:"+ b);
			log.info("수정본 :"+update_board);
		});
	}
	//@Test
	void f3() {
		Long searchId = 20L;
		//하나의 id를 찾을때
		//있거나 없거나
	brepo.findById(searchId).ifPresentOrElse(b->{
		log.info("조회한 정보:"+b);
	}, ()->{log.info("존재하지않음");});
	}
	
	//@Test
	void f2() {
		brepo.findAll().forEach(board->{
			log.info(board.toString());
		});	
		}
	
	//@Test
	void f1() {
		IntStream.rangeClosed(21, 40).forEach(i ->{
			BoardVO board = BoardVO.builder()
					.title("Java "+i)
					.content("기억안나다.....")
					.writer("user"+i%5)
					.build();
			BoardVO new_board = brepo.save(board);
			//update는 new_board와 board가 다름
			log.info("생성된board"+board);
			log.info("입력된Board"+new_board);
			//insert할때 save는 그냥 그 board받아서 하면됨 둘다 내용같음
			log.info(board.equals(new_board)?"내용같음":"내용다름");
		});
	}
	
}
