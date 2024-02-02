package com.shinhan.sbproject.repository.composite;

import org.springframework.data.repository.CrudRepository;

import com.shinhan.sbproject.vo4.UserVO3;

public interface UserVO3Repository extends CrudRepository<UserVO3, String>{
//uservo3을 이용해서 userCellPhone3 저장
}
