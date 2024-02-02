package com.shinhan.sbproject.vo3;

import java.sql.Timestamp;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude="board2") //댓글에서 board로 다시 가지 않도록 (무한roop 돌지않도록)
@Builder
@Entity
@Table(name="tbl_freereplies")
public class FreeBoardReply {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long rno;
	private String reply;
	private String replyer;
	
	@CreationTimestamp
	private Timestamp regdate;
	@UpdateTimestamp
	private Timestamp updatedate;
	
	@JsonIgnore//무한loop되지않도록  FreeBoard-> FreeBoardReply -> 다시 FreeBoard로 가기는 막아야한다.
		//자바객체가 Browser로 내려갈때 JSON data로 변경되어 내려간다 (댓글까지 데려가서 문제..) com.fasterxml.jackson.databine 오류때문에 추가 
	@ManyToOne //FK: board2_bno
	FreeBoard board2;
	
}
