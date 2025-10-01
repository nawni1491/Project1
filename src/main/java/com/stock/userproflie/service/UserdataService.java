package com.stock.userproflie.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.stock.userproflie.model.UserProfileDto;
import com.stock.userproflie.model.UserProfiledata;

public interface UserdataService {
    
    UserProfiledata getUserdataById(long id);
    
    UserProfiledata saveUserdata(UserProfileDto userProfileDto, MultipartFile imageFile) throws Exception;
    
    UserProfiledata updateUserdata(long id, UserProfileDto userProfileDto, MultipartFile imageFile) throws Exception;
    
    Page<UserProfiledata> getAllUserProfiledatas(Pageable pageable);
    
    void deleteUserdata(long id) throws Exception;

	List<UserProfiledata> getAllUserdata();
}
