package com.shinhan.sbproject.vo4;

import java.util.Set;

import com.shinhan.sbproject.vo.MemberRole;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name="tbl_enum")
public class EnumTypeVO {

@Id
private String mid;
private String mpassword;
private String mname;


//targetClass 어디서 가져올껀지?
@ElementCollection(targetClass = MemberRole.class,
fetch = FetchType.EAGER
)
@CollectionTable(name = "tbl_enum_mroles", 
                joinColumns = @JoinColumn(name="mid2"))

@Enumerated(EnumType.STRING)//index 0(user),1(manager),2(admin)이 있으면 인덱스의 String을 그대로 저장하고 싶을때
private Set<MemberRole> mrole;
}
