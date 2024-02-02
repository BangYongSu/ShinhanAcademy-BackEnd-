package com.shinhan.sbproject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.shinhan.sbproject.vo3.FreeBoard;

public interface FreeBoardRepository 
extends CrudRepository<FreeBoard, Long>,
PagingAndSortingRepository<FreeBoard, Long>,QuerydslPredicateExecutor<FreeBoard>{

	List<FreeBoard> findByBnoGreaterThan(Long bno,Pageable page);
	List<FreeBoard> findByBnoBetween(Long bno1,Long bno2,Pageable page);
	//List<FreeBoard> findByWriter(String writer,Pageable page);
	Page<FreeBoard> findByWriter(String writer2,Pageable page);
	
	
	
	//Title이 특정문자를 포함하는 board를 얻기, sort, 특정칼람만 select
	//(규칙에 맞는 함수가 안될때)
	@Query("select free.bno, free.title, free.writer from FreeBoard free where free.title like %?1% order by free.bno")
	List<Object[]> selectByTitle(String title);
	
	@Query("select free.bno, free.title, free.writer from #{#entityName} free where free.title like %:tt% order by free.bno")
	List<Object[]> selectByTitle2(@Param("tt") String title);
	
	@Query(value="select free.bno, free.title, free.writer from tbl_freeboards free "
			+ "where free.title like CONCAT('%', :tt, '%') order by free.bno desc", nativeQuery = true)
	List<Object[]> selectByTitle3(@Param("tt") String title);
	
	
}