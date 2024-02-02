package com.shinhan.sbproject;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;

import com.querydsl.core.BooleanBuilder;
import com.shinhan.firstzone.repository.BoardRepository;
import com.shinhan.sbproject.repository.FreeBoardReplyRepository;
import com.shinhan.sbproject.repository.FreeBoardRepository;
import com.shinhan.sbproject.vo3.FreeBoard;
import com.shinhan.sbproject.vo3.FreeBoardReply;
import com.shinhan.sbproject.vo3.QFreeBoard;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
class TwoWayTest {

	
	@Autowired
	FreeBoardRepository boardrepo;
	
	@Autowired
	FreeBoardReplyRepository replyRepo;
	
	@Test
	void f5() {
		String title = "연습";
		Long bno= 10L;
		BooleanBuilder builder = new BooleanBuilder();
		QFreeBoard board = QFreeBoard.freeBoard;
		
		if(title!=null) builder.and(board.title.like("%"+title+"%")); //and title like '%'?1'%'
		builder.and(board.bno.lt(bno));
		boardrepo.findAll(builder).forEach(b->log.info(b.toString()));
		log.info("-------------------------------------");
		boardrepo.findAll(builder,Sort.by("bno").descending()).forEach(b->log.info(b.toString()));
		log.info("-------------------------------------");
		Pageable page = PageRequest.of(0,  5, Direction.DESC,"bno");
		Page<FreeBoard> result = boardrepo.findAll(builder,page);
		List<FreeBoard> blist = result.getContent();
		log.info("건수"+result.getTotalElements());
		log.info("페이지수:"+ result.getTotalPages());
		blist.forEach(b->log.info(b.toString()));
	}
	
	
	//@Test
	void f4() {
		String title = "연습";
		boardrepo.selectByTitle(title).forEach(b->log.info(b.toString()));
		log.info("-----------------------------");
		boardrepo.selectByTitle2(title).forEach(b->log.info(b.toString()));
		log.info("-----------------------------");
		boardrepo.selectByTitle3(title).forEach(b->log.info(b.toString()));
		log.info("-----------------------------");
	}
	
	
	//@Test
	void test1() {
		Pageable paging = PageRequest.of(0, 4);
		boardrepo.findByBnoGreaterThan(6L, paging).forEach(b->{
			log.info(b.toString());
		});
		
	}
	//@Test
	void test1teacher() {
		Sort sort = Sort.by(Direction.ASC, "bno");
		Pageable page = PageRequest.of(0, 9, sort);
		List<FreeBoard> blist = boardrepo.findByBnoGreaterThan(10L, page);
		for(FreeBoard board: blist) {
			log.info("bno :" + board.getBno());
			log.info("title :" + board.getTitle());
			log.info("Writer :" + board.getWriter());
			log.info("Content :" + board.getContent());
			log.info("Replies :" + board.getReplies().size());
			log.info("--------------------------------------");
			
		}
	}
	//@Test
	void test2() {
		Pageable paging = PageRequest.of(0, 4);
		boardrepo.findByBnoBetween(3L, 17L, paging).forEach(b->{
			log.info(b.toString());
		});
	}
	//@Test
	void test2Teacher2() {
		Sort sort = Sort.by(Direction.ASC, "bno");
		Pageable page = PageRequest.of(0, 9, sort);
		List<FreeBoard> blist = boardrepo.findByBnoBetween(3L, 17L, page);
		for(FreeBoard board: blist) {
			log.info("bno :" + board.getBno());
			log.info("title :" + board.getTitle());
			log.info("Writer :" + board.getWriter());
			log.info("Content :" + board.getContent());
			log.info("Replies :" + board.getReplies().size());
			log.info("--------------------------------------");
			
		}
	}
	@Test
	void test3() {
		Pageable paging = PageRequest.of(0, 4);
		boardrepo.findByWriter("user3", paging).forEach(b->{
			log.info(b.toString());
		});
	}
	//@Test
	void test2Teacher3() {
		Sort sort = Sort.by(Direction.ASC, "bno");
		Pageable page = PageRequest.of(0, 9, sort);
		Page<FreeBoard> blist = boardrepo.findByWriter("user4", page);
		
		log.info("페이지수:"+ blist.getTotalPages());
		log.info("건수:"+blist.getTotalElements());
		List<FreeBoard> bblist = blist.getContent();
		for(FreeBoard board: bblist) {
			log.info("bno :" + board.getBno());
			log.info("title :" + board.getTitle());
			log.info("Writer :" + board.getWriter());
			log.info("Content :" + board.getContent());
			log.info("Replies :" + board.getReplies().size());
			log.info("--------------------------------------");
			
		}
	}
	
	//특정board 댓글가져오기
	//@Test
	void replySelectBoard() {
		FreeBoard board = FreeBoard.builder()
				.bno(20L)
				.title("AA")
				.build();
		List<FreeBoardReply> replyList = replyRepo.findByBoard2(board);
		replyList.forEach(reply->{
			log.info("rno:" + reply.getRno());
			log.info("reply:" + reply.getReply());
			log.info("replyer:" + reply.getReplyer());
			log.info("Regdate:" + reply.getRegdate());
			log.info("Updatedate:" + reply.getUpdatedate());
			log.info("------------------------------------");
		});
	}
	
	
	//모든댓글가져오기
	//@Test
	void replySelect() {
		replyRepo.findAll(Sort.by(Direction.DESC, "rno")).forEach(reply->{
			log.info("작성자:"+reply.getReplyer());
			log.info("내용:"+reply.getReply());
			log.info("board내용:"+reply.getBoard2().getContent());
			log.info("--------------");
		});
	}
	
	
	//Board의 댓글의 개수출력
	//@Transactional //지연로딩일때 연관관계table fetch하려면 반드시 추가한다.
	//@Test
	void getReplyCount() {
		boardrepo.findAll().forEach(board->{
			log.info(board.getBno()+"--->"+board.getReplies().size());
			
		});
	}
	
	
	//특정board의 댓글입력 5L, 10L, 15L
	//@Test
		void replyInsert2() {
			List<Long> blist = Arrays.asList(5L, 10L, 15L);
			
			boardrepo.findAllById(blist).forEach(board->{
				IntStream.range(1, 6).forEach(i->{
					FreeBoardReply reply = FreeBoardReply.builder()
							.reply("퍼스트존..."+board.getBno())
							.replyer("작성자"+i)
							.board2(board)
							.build();
					replyRepo.save(reply);
				});
			});
		}
	
	//@Test
	void replyInsert() {
		FreeBoard board =  boardrepo.findById(20L).orElse(null);
		IntStream.range(1, 6).forEach(i->{
			FreeBoardReply reply = FreeBoardReply.builder()
					.reply("나도나도")
					.replyer("작성자"+i)
					.board2(board)
					.build();
			replyRepo.save(reply);
		});
	}
	
	
	//@Test
	void boardSelect() {
		boardrepo.findAll(Sort.by(Direction.DESC,"bno")).forEach(f->log.info(f.toString()));
	}
	
	//@Test
	void boardInsert() {
		//20건의 board작성
		IntStream.range(1, 21).forEach(i->{
			FreeBoard board = FreeBoard.builder()
					.title("양방향연습"+i)
					.writer("user"+i%5)
					.content("아~카스처럼...맛있어")
					.build();
			boardrepo.save(board);
		});
		
	}
	
}
