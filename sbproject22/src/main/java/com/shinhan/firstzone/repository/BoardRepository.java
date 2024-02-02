package com.shinhan.firstzone.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.shinhan.sbproject.vo.BoardVO;
import java.util.List;
import java.util.stream.IntStream;



//1.기본CRUD작업=>CrudRepository상속:findAll.save,selectById,save, count, delete
public interface BoardRepository extends CrudRepository<BoardVO, Long>,
PagingAndSortingRepository<BoardVO, Long>,
QuerydslPredicateExecutor<BoardVO>{
	//이렇게만 하면 JPA가 구현 CLASS를 만들어준다!
	
	//2.규칙에 맞는 메서드 정의
	List<BoardVO> findByWriter(String writer); //where writer=?
	List<BoardVO> findByContent(String content);//where content=?
	List<BoardVO> findByBnoGreaterThan(Long bno);//where bno>?
	List<BoardVO> findByContentLike(String content);//where content=?
	List<BoardVO> findByContentContaining(String content);//where content like '%'||?||'%'
	List<BoardVO> findByTitleContainingAndBnoGreaterThanAndWriterLike(String title, Long bno,String writer);
	//특정writer가 작성한 board건수
int countByWriter(String writer);

	//Paging, Sort추가
    List<BoardVO>  findByBnoGreaterThan(Long bno, Pageable paging);
    Page<BoardVO>  findByBnoBetween(Long bno1,Long bno2, Pageable paging);

    //3.JPQL(JPA Query Language) : 규칙에 맞는 함수정의가 한계가 있다. 이를 해결하는 방법이다.
    @Query("select b from BoardVO b "
    		+ " where b.title like %?2% and b.writer like %?3% and b.bno > ?1 "
    		+ "order by bno desc ")
    List<BoardVO> selectByTitleAndWriter2(Long bno, String title, String writer);

    @Query(value ="select * from tbl_boards_review  b"
    		+ " where b.title like %?2% and b.writer like %?3% and b.bno> ?1 "
    		+ "order by bno desc ", nativeQuery = true)
    List<BoardVO> selectByTitleAndWriter3(Long bno, String title, String writer);

    @Query("select b from BoardVO b"
    		+ " where b.title like %:tt% and b.writer like %:ww% and b.bno> :bb "
    		+ "order by bno desc ")
    List<BoardVO> selectByTitleAndWriter4(@Param("bb") Long bno,@Param("tt") String title,@Param("ww") String writer);
    

    @Query("select b from #{#entityName} b"
    		+ " where b.title like %:tt% and b.writer like %:ww% and b.bno > :bb "
    		+ "order by bno desc ")
    List<BoardVO> selectByTitleAndWriter5(@Param("bb") Long bno,@Param("tt") String title,@Param("ww") String writer);
    
    @Query("select b.title, b.writer, b.content from #{#entityName} b"
    		+ " where b.title like %:tt% and b.writer like %:ww% and b.bno > :bb "
    		+ "order by bno desc ")
    List<Object[]> selectByTitleAndWriter6(@Param("bb") Long bno,@Param("tt") String title,@Param("ww") String writer);
    
    @Query("select board.title, board.content, board.writer, board.bno, board.regDate from #{#entityName} board where board.writer = :wr ")
    List<String[]> selectByWriter(@Param("wr") String writer);
}