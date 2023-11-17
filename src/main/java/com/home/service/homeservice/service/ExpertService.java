package com.home.service.homeservice.service;


import com.home.service.homeservice.domain.Expert;
import com.home.service.homeservice.filter.ExpertFilter;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ExpertService {

    Expert saveOrUpdate (Expert expert);

    String delete (Expert expert);
    void deleteById (Long id);
    Expert findById (Long id);
    Expert findByUserName (String userName);


    Expert changPassword(String oldPassword, String userName, String newPassword );
    List<Expert> findAll();

    Expert signIn(String userName,String password) ;
    Expert signUpWithPicture(Expert expert, MultipartFile multipartFile) throws IOException;
    Expert signUp(Expert expert) ;

    List<Expert> searchFromExpert(ExpertFilter expertFilter);

    Expert findByEmailAddress(String emailAddress);

    MultipartFile getExpertImage() throws IOException;


}
