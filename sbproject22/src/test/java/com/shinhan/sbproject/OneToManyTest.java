package com.shinhan.sbproject;

import java.util.ArrayList;
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
import org.springframework.test.annotation.Commit;

import com.shinhan.sbproject.repository.PDSBoardRepository;
import com.shinhan.sbproject.repository.PDSFileRepository;
import com.shinhan.sbproject.vo2.PDSBoard;
import com.shinhan.sbproject.vo2.PDSFile;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Commit
@SpringBootTest
@Slf4j
public class OneToManyTest {

	@Autowired
	PDSBoardRepository brepo;

	@Autowired
	PDSFileRepository frepo;

	@Test
	void fileSelectAll() {
								//fno로 descending
		frepo.findAll(Sort.by(Direction.DESC, "fno")).forEach(f->log.info(f.toString()));;
		System.out.println("---------------------------------------------");
		Pageable paging = PageRequest.of(0, 3);
		frepo.findAll(paging).forEach(p->{
			log.info(p.toString());
		});
		paging = PageRequest.of(1, 3);
		frepo.findAll(paging).forEach(p->{
			log.info(p.toString());
		});
		paging = PageRequest.of(0, 3);
		Page<PDSFile> result = frepo.findAll(paging);
		int cnt = result.getTotalPages();
		for(int i=0; i<cnt; i++) {
			paging = PageRequest.of(i, 3);
			frepo.findAll(paging).forEach(f->log.info(f.toString()));
			log.info("-------11-------------");
		}
		
		
	}
	
	
	//@Test
	void getFileByBoard() {
		brepo.findById(1L).ifPresent(b->{
			for(PDSFile f:b.getFiles2()) {
				log.info(f.toString());
			}
		});
	}
	
	
	//@Test
	void fileDelete() {
		frepo.deleteById(1L);
	}
	
	
	
	//@Test
	void searchFile() {
	 List<PDSBoard>	blist = (List<PDSBoard>) brepo.findAll();
	 for(PDSBoard board:blist) {
		 List<PDSFile> flist = board.getFiles2();
		 flist.forEach(f->{
			 if(f.getFno()==3L) {
				 f.setPdsfilename("찾기어려웠음.jpg");
			brepo.save(board);
			 }
		 });
	 }
	}
	
	
	// 첨부파일 수정
	@Transactional // 이 클래스가 Test이기때문에 DB에 반영되지는 않는다(즉, Rollback된다), 반영하려면 class level에 commit 해줘야함
	// @Test
	void updateFile2() {
		int result = brepo.updatePDSFile(2L, "이미지성형하기.jpg");
		log.info("결과: " + result);
	}

	// 첨부파일 수정
	// @Test
	void updateFile1() {
		PDSFile file = frepo.findById(1L).orElse(null);
		if (file == null)
			return;
		file.setPdsfilename("파일이름수정");
		frepo.save(file);
	}

	// 3.Board별 File의 count얻기(File ->Board)불가
	// @Test
	void getFilesCount() {
		frepo.getFilesCount().forEach(arr -> {
			log.info(Arrays.toString(arr));
		});
	}

	// 2.Board별 File의 count얻기(File ->Board)불가
	// @Test
	void fileCount2() {
		frepo.getFileCountByBoard().forEach(arr -> {
			log.info(Arrays.toString(arr));
		});
	}

	// 1.Board별 File의 count얻기 (Board ->File)
	// @Test
	void fileCount() {
		brepo.findAll().forEach(b -> {
			log.info(b.getPname() + "--->" + b.getFiles2().size());
		});
	}

	// @Test
	void fileUpdate2() {
		Long pid = 2L;
		PDSBoard board = brepo.findById(pid).orElse(null);
		if (board == null)
			return;
		List<PDSFile> flist = board.getFiles2();
		frepo.findByPdsfilenameContaining("eye").forEach(f -> {
			flist.add(f);
		});
		brepo.save(board);
	}

	// Board를 통해서 File을 저장했다면 PDSFile테이블의 pdsno가 pid로 들어간다.
	// File만 저장했다면 pdsno가 null이다.
	// @Test
	void fileUpdate() {
		// 11번 첨부파일이 2번 board에 추가하고자한다.
		Long fno = 11L;
		Long pid = 2L;
		frepo.findById(fno).ifPresent(f -> {
			brepo.findById(pid).ifPresent(b -> {
				b.getFiles2().add(f);
				brepo.save(b);
			});
		});
	}

	// @Test
	void fileSave() {

		IntStream.rangeClosed(1, 10).forEach(i -> {
			PDSFile file = PDSFile.builder().pdsfilename("eye-" + i).build();
			frepo.save(file);

		});
	}

	// @Test
	void selectByBoard() {
		Long pid = 2L;

		brepo.findById(pid).ifPresent(board -> {
			log.info("pname:" + board.getPname());
			log.info("pname:" + board.getPwriter());
			log.info("pname:" + board.getFiles2());
			log.info("pname:" + board.getFiles2().size());
			log.info("***************************");
		});
	}

	// @Test
	void fileSelect() {
		frepo.findAll().forEach(b -> log.info(b.toString()));
	}

	// @Test
	void boardSelect() {
		brepo.findAll().forEach(b -> log.info(b.toString()));
	}

	// @Test
	void insert() {
		// Board(1), File(n)
		List<PDSFile> flist = new ArrayList<>();
		IntStream.rangeClosed(1, 5).forEach(i -> {
			PDSFile file = PDSFile.builder().pdsfilename("face-" + i).build();
			flist.add(file);
		});
		PDSBoard board = PDSBoard.builder().pname("눈이옵니다").pwriter("지현").files2(flist).build();
		brepo.save(board);

	}
}
