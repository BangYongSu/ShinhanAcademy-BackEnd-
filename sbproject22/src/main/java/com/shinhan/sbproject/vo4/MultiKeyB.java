package com.shinhan.sbproject.vo4;

import java.io.Serializable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Embeddable //복합키 사용가능함 (가능하게 해주는 어노테이션)
public class MultiKeyB implements Serializable{
private Integer id1;
private Integer id2;
}
