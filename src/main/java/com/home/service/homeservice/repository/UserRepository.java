package com.home.service.homeservice.repository;

import com.home.service.homeservice.domain.Admin;
import com.home.service.homeservice.domain.Customer;
import com.home.service.homeservice.domain.Expert;
import com.home.service.homeservice.domain.base.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Transactional
@RequiredArgsConstructor
public class UserRepository {

    private final AdminRepository adminRepository;
    private final CustomerRepository customerRepository;
    private final ExpertRepository expertRepository;

    public User findByUsername(String username) {
        Optional<Admin> admin = adminRepository.findByUsername(username);
        if (admin.isPresent()) return admin.get();
        Optional<Customer> customer = customerRepository.findByUsername(username);
        if (customer.isPresent()) return customer.get();
        Optional<Expert> expert = expertRepository.findByUsername(username);
        if (expert.isPresent()) return expert.get();
        throw new UsernameNotFoundException("user name not found");
    }
}
