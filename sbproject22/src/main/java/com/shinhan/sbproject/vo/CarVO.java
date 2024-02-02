package com.shinhan.sbproject.vo;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name ="tbl_car")
@Getter@Setter@NoArgsConstructor
@AllArgsConstructor@ToString@Builder
public class CarVO {
@Id
@GeneratedValue(strategy = GenerationType.AUTO)
 Long carNum;
 String model;
 int price;
}
