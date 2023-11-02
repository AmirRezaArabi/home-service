package com.home.service.homeservice.service;


import com.home.service.homeservice.domain.Customer;
import com.home.service.homeservice.domain.Expert;
import com.home.service.homeservice.utility.CustomerFilter;
import com.home.service.homeservice.utility.ExpertFilter;

import java.util.List;
import java.util.Optional;

public interface ExpertService {

    Expert saveOrUpdate (Expert expert);

    String delete (Expert expert);
    void deleteById (Long id);
    Optional<Expert> findById (Long id);
    Optional<Expert> findByUserName (String userName);

    Optional<Expert> changPassword(String oldPassword, String userName, String newPassword );
    List<Expert> findAll();

    Expert signIn(String userName,String password) ;
    Expert signUp(Expert expert);

    List<Expert> searchFromExpert(ExpertFilter expertFilter);

}
