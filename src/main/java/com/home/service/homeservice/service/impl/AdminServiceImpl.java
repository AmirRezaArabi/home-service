package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.Admin;
import com.home.service.homeservice.domain.enums.Role;
import com.home.service.homeservice.exception.UserNameOrPasswordDosNotExistException;
import com.home.service.homeservice.repository.AdminRepository;
import com.home.service.homeservice.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final AdminRepository adminRepository;

    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public Admin saveOrUpdate(Admin admin) {

        return adminRepository.save(admin);
    }

    @Override
    public String delete(Admin admin) {
         adminRepository.delete(admin);
         return admin.getUsername();
    }

    @Override
    public void deleteById(Long id) {
        adminRepository.deleteById(id);
    }

    @Override
    public Admin findByUserName(String userName) {
        return adminRepository.findByUsername(userName).orElseThrow(()->new UsernameNotFoundException("the username not found"));
    }

    @Override
    public Admin signIn(String userName, String password) {
        Admin admin = findByUserName(userName);
        if (!admin.getPassword().equals(password))
            throw new UserNameOrPasswordDosNotExistException("password not correct");
        return admin;
    }

    @Override
    public Admin signUp(Admin admin) {
        admin.setPassword(passwordEncoder.encode(admin.getPassword()));
        admin.setRole(Role.ROLE_ADMIN);
        return adminRepository.save(admin);
    }
}
