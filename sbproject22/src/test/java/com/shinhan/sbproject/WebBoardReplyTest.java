package com.shinhan.sbproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.shinhan.sbproject.webboard.WebBoard;
import com.shinhan.sbproject.webboard.WebBoardRepository;
import com.shinhan.sbproject.webboard.WebReply;
import com.shinhan.sbproject.webboard.WebReplyRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootTest
public class WebBoardReplyTest {

	@Autowired
	WebBoardRepository boardrepo;
	
	@Autowired
	WebReplyRepository replyrepo;
	
	
	@Test
	void f4() {
		//111,112,113 게시글의 댓글을 3개씩 입력
		List<Long> blist = Arrays.asList(111L,112L,113L);
		
		boardrepo.findAllById(blist).forEach(board->{
			
			IntStream.rangeClosed(1, 3).forEach(j->{
				WebReply reply = WebReply.builder()
						.replyText("알림"+"--"+j)
						.replyer("승광" + board.getBno())
						.board(board) //어떤 게시글의 댓글인지 반드시 넣어야한다
						.build();
				replyrepo.save(reply);
				
			});
			
		});
	}
	
	@Test
	void f3() {
		//105,106,107 게시글의 댓글을 3개씩 입력
		List<Long> blist = Arrays.asList(105L,106L,107L);
		
		boardrepo.findAllById(blist).forEach(board->{
			List<WebReply> rlist = new ArrayList<>();
			IntStream.rangeClosed(1, 3).forEach(j->{
				WebReply reply = WebReply.builder()
						.replyText("알림"+"--"+j)
						.replyer("승광" + board.getBno())
						.board(board) //어떤 게시글의 댓글인지 반드시 넣어야한다
						.build();
				rlist.add(reply);
			});
			board.setReplies(rlist);
			boardrepo.save(board);
		});
	}
	
	//@Test
	void f1() {
		//Board100개 insert
		//Reply 1,10,20 board댓글 5개입력
		IntStream.rangeClosed(1, 100).forEach(i->{
		WebBoard board = WebBoard.builder()
				.title("불금"+i)
				.content("열심히 놀아야지요"+i/10)
				.writer("user"+i%5)
				.build();
		if(i==1||i==10||i==20) {
			List<WebReply> replies = new ArrayList<>();
			IntStream.rangeClosed(1, 5).forEach(j->{
				WebReply reply = WebReply.builder()
						.replyText("정처기안하냐?"+i+"--"+j)
						.replyer("쩡요" + j)
						.board(board) //어떤 게시글의 댓글인지 반드시 넣어야한다
						.build();
				replies.add(reply);
			});
			board.setReplies(replies);
		}
	
		
		boardrepo.save(board);
		});
		
	}
}
