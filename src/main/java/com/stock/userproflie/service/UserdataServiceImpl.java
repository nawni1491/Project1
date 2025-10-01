package com.stock.userproflie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.stock.userproflie.model.UserProfileDto;
import com.stock.userproflie.model.UserProfiledata;
import com.stock.userproflie.repository.UserProfileRepository;
import com.stock.userproflie.service.UserdataService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class UserdataServiceImpl implements UserdataService {

    @Autowired
    private UserProfileRepository userProfilerepository;

    // Folder where images will be stored
    @Value("${upload.folder:uploads/}")
    private String uploadFolder;

    // Default image path
    private static final String DEFAULT_IMAGE = "images/default_pf.jfif";

    @Override
    public UserProfiledata getUserdataById(long id) {
        return userProfilerepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public UserProfiledata saveUserdata(UserProfileDto userdatadto, MultipartFile imageFile) throws Exception {
        UserProfiledata userdata = new UserProfiledata();
        userdata.setFullname(userdatadto.getFullName());
        userdata.setEmail(userdatadto.getEmail());
        userdata.setPhone(userdatadto.getPhone());
        userdata.setGender(userdatadto.getGender());
        userdata.setAddress(userdatadto.getAddress());

        // Handle image
        if (imageFile != null && !imageFile.isEmpty()) {
            userdata.setImagePath(saveImage(imageFile));
        } else {
            userdata.setImagePath(DEFAULT_IMAGE);
        }

        return userProfilerepository.save(userdata);
    }

    @Override
    public UserProfiledata updateUserdata(long id, UserProfileDto userProfiledto, MultipartFile imageFile) throws Exception {
        UserProfiledata existingUserdata = userProfilerepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        existingUserdata.setFullname(userProfiledto.getFullName());
        existingUserdata.setEmail(userProfiledto.getEmail());
        existingUserdata.setPhone(userProfiledto.getPhone());
        existingUserdata.setGender(userProfiledto.getGender());
        existingUserdata.setAddress(userProfiledto.getAddress());

        if (imageFile != null && !imageFile.isEmpty()) {
            existingUserdata.setImagePath(saveImage(imageFile));
        } else if (existingUserdata.getImagePath() == null) {
            // If user had no image before, keep default
            existingUserdata.setImagePath(DEFAULT_IMAGE);
        }

        return userProfilerepository.save(existingUserdata);
    }

    @Override
    public Page<UserProfiledata> getAllUserProfiledatas(Pageable pageable) {
        return userProfilerepository.findAll(pageable);
    }

    @Override
    public void deleteUserdata(long id) throws Exception {
        UserProfiledata data = userProfilerepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        userProfilerepository.delete(data);
    }

    // Save image to uploads folder
    private String saveImage(MultipartFile file) throws IOException {
        Path folderPath = Paths.get(uploadFolder);
        if (!Files.exists(folderPath)) {
            Files.createDirectories(folderPath);
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = folderPath.resolve(fileName);
        file.transferTo(filePath.toFile());

        return "uploads/" + fileName; // return relative path
    }

	@Override
	public List<UserProfiledata> getAllUserdata() {
		// TODO Auto-generated method stub
		return null;
	}
}
