package com.shinhan.sbproject.webboard;

import java.security.Principal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.querydsl.core.types.Predicate;
import com.shinhan.sbproject.security.MemberService;
import com.shinhan.sbproject.vo.MemberDTO;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/webboard")
@Tag(name="웹보드", description = "여기에서는 WebBoard CRUD가능")
public class WebBoardController {

	final WebBoardRepository boardrepo;

	final MemberService mservice;
	@GetMapping("/delete.do")
	public String f5(Long bno, RedirectAttributes attr) {
		boardrepo.deleteById(bno);
		attr.addFlashAttribute("message","삭제성공");
		return "redirect:list.do";
	}
	
	@GetMapping("/insert.do")
	public void f4() {
		
		
	}
	
	@PostMapping("/insert.do")
	public String f4(WebBoard board, RedirectAttributes attr) {
		log.info(board.toString());
			WebBoard newboard = boardrepo.save(board);
		attr.addFlashAttribute("message", newboard!=null?"입력성공":"입력실패");
		return "redirect:list.do";
	}
	@PostMapping("/update.do")
	public String f3 (WebBoard board,RedirectAttributes attr, PageVO page ) {
		log.info(board.toString());
		
		attr.addAttribute("page",page.getPage());
		attr.addAttribute("size",page.getSize());
		attr.addAttribute("keyword",page.getKeyword());
		attr.addAttribute("type",page.getType());
		boardrepo.save(board);
		return "redirect:list.do";
	}
	
	@GetMapping("/detail.do")
	public void f2(Model model, Long bno, @ModelAttribute("pageVO") PageVO page) {
 //page에서 사용하기 위함
		model.addAttribute("board",boardrepo.findById(bno).orElse(null));
	}
	
	@GetMapping("/list.do")
	public void f1(Principal principal, Authentication auth,HttpSession session,Model model, @ModelAttribute("pageVO") PageVO page) {
		
		//로그인한 맴버의 정보를 알아내기
		//1. Principal 이용
		log.info("첫번째 방법 :"+principal.toString());
		//2.Authentication 이용
		log.info("두번째 방법 :"+auth.getPrincipal());
		
		//3.SecurityContextHolder 이용
		log.info("세번째 방법"+SecurityContextHolder.getContext().getAuthentication().getPrincipal());
		
		String mid = principal.getName();
		UserDetails user = mservice.loadUserByUsername(mid);
		log.info("4번째 이름"+user);
		
		
	 //page에서 사용하기 위함
		
		page.setSize(10);
		Predicate predicate = boardrepo.markePredicate(page.getType(), page.getKeyword());
		Pageable paging = page.makePageable(0, "bno");
		Page<WebBoard> result = boardrepo.findAll(predicate, paging);
		PageMarker<WebBoard> pageMaker = new PageMarker<>(result, 5, page.getSize());
				model.addAttribute("blist",pageMaker);
				//paging, predicate, sort추가함
				page.setType("title");
	}
	
	
}
