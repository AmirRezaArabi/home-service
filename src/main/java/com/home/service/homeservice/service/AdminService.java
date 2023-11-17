package com.home.service.homeservice.service;

import com.home.service.homeservice.domain.Admin;

public interface AdminService {
    Admin saveOrUpdate (Admin admin);

    String delete (Admin admin);
    
    void deleteById (Long id);
    
    Admin findByUserName (String userName);

    Admin signIn(String userName,String password) ;
    
    Admin signUp(Admin admin);

}
