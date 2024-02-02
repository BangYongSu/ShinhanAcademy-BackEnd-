package com.shinhan.sbproject.webboard;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@ToString(exclude = "replies")  
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tbl_webboards")
public class WebBoard {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)//oracle:sequence, mysql:identity
	private Long bno;
    @NonNull //WebBoard가 build될때 반드시 setting해야한다. 
    @Column(nullable = false)  //DB칼럼이 not null
	private String title;
	private String writer;
	@Column(length = 100)
	private String content;	

	@CreationTimestamp
	private Timestamp regdate;//yyyy-MM-dd hh:mm:ss
	
	@UpdateTimestamp  //생성시 생성일자, 수정시 변경된다. 
	private Timestamp updatedate;
//Board1건에 여러개의 댓글이 있다. Board갯수만큼 댓글을 조회한다(N+1문제)
 //N+1문제해결방법1 
	//@BatchSize(size = 100)  //Board1건마다 검색하는 걸 Board개수만큼 한번에 조회시켜줌
	@JsonIgnore
	@OneToMany(mappedBy = "board", 
			cascade = CascadeType.ALL,
			fetch = FetchType.EAGER ) //replies를 사용하지 않으면 LAZY, 사용하면 EAGER
	List<WebReply> replies;
	//@OneToMany와 @ManyToMany는 기본이 지연 로딩(LAZY)이다.
	//@ManyToOne이 EAGER임. 양방향이므로 reply에서 board정보필요하므로 N번 호출됨 
	//그러므로 OneToMany에서 지연로딩으로 변경하여도  N번 쿼리 호출된다. 
	//해결방안은 BatchSize조정 @BatchSize(size = 100)
 
}
