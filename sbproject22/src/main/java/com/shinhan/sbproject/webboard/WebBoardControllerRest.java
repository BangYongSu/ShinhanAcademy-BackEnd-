package com.shinhan.sbproject.webboard;

import java.security.Principal;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.querydsl.core.types.Predicate;
import com.shinhan.sbproject.security.MemberService;
import com.shinhan.sbproject.vo.MemberDTO;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/rest/webboard")
@Tag(name="웹보드", description = "여기에서는 WebBoard CRUD가능")
//@CrossOrigin(origins = "http:..localhost:3000")
public class WebBoardControllerRest {

	final WebBoardRepository boardrepo;

	final MemberService mservice;
	@DeleteMapping("/delete.do/{bno}")
	public String f6(@PathVariable("bno") Long bno) {
		boardrepo.deleteById(bno);
		return "삭제성공";
	}
	
//	@GetMapping("/insert.do")
//	public void f4() {
//		
//		
//	}
	
	@PostMapping("/insert.do")
    public Integer f5(@RequestBody WebBoard board) {
        log.info(board.toString());
        WebBoard newboard = boardrepo.save(board);
        
        return newboard.getBno()!=null?0:-1;
    }
	
	@PutMapping("/update.do")
    public Integer f3( @RequestBody WebBoard board, PageVO page) {
        log.info(board.toString());
        WebBoard updateBoard = boardrepo.save(board);
        
        return updateBoard==null?-1:0;
    }
	
    @GetMapping("/detail.do/{bno}")
    public WebBoard f2(@PathVariable("bno") Long bno, PageVO page
                ) {
        return boardrepo.findById(bno).orElse(null);
    }
	
	@GetMapping("/list.do")
	public List<WebBoard> f1(@ModelAttribute("pageVO") PageVO page) {
		page.setSize(10);
		Predicate predicate = boardrepo.markePredicate(page.getType(), page.getKeyword());
		Pageable paging = page.makePageable(0, "bno");
		Page<WebBoard> result = boardrepo.findAll(predicate, paging);
		PageMarker<WebBoard> pageMaker = new PageMarker<>(result, 5, page.getSize());
				return result.getContent();
	}
	
	
}
