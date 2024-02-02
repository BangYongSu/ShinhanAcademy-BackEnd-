package com.shinhan.sbproject.controller;

import org.springframework.web.bind.annotation.RestController;

import com.shinhan.sbproject.vo.CarVO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class SampleRestController {

	@GetMapping("/sample1")
	public String f1() {
		return "ok";
		}
	
	@GetMapping("/sample2")
	public CarVO f2() {
		
		
		return new CarVO(1L,"DDD",1000);
		}
	@GetMapping("/sample3")
	public List<CarVO> f3() {
		List<CarVO> carList = new ArrayList<>();
		IntStream.rangeClosed(1, 10).forEach(i->{
			CarVO c1 = CarVO.builder()
					.carNum(i*100L)
					.model("ABC"+i)
					.price(i*2000)
					.build();
			carList.add(c1);
		});
		
		return carList;
		}
}
