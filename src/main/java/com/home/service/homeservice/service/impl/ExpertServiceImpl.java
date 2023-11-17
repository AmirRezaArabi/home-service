package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.Expert;
import com.home.service.homeservice.domain.Wallet;
import com.home.service.homeservice.domain.enums.EXPERT_STATUS;
import com.home.service.homeservice.domain.enums.Role;
import com.home.service.homeservice.exception.IdIsNotExist;
import com.home.service.homeservice.exception.TheEmailNotFoundException;
import com.home.service.homeservice.exception.TheInputInformationIsNotValidException;
import com.home.service.homeservice.exception.UserNameOrPasswordDosNotExistException;
import com.home.service.homeservice.filter.ExpertFilter;
import com.home.service.homeservice.repository.ExpertRepository;
import com.home.service.homeservice.service.ExpertService;
import com.home.service.homeservice.service.WalletService;
import com.home.service.homeservice.utility.ExpertSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageWriteParam;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ExpertServiceImpl implements ExpertService {
    private final BCryptPasswordEncoder passwordEncoder;

    private final ExpertRepository expertRepository;
    private final WalletService walletService;

    @Override
    public Expert saveOrUpdate(Expert expert) {
        return expertRepository.save(expert);
    }

    @Override
    public String delete(Expert expert) {
        expertRepository.delete(expert);
        return expert.getUsername();
    }

    @Override
    public void deleteById(Long id) {
        expertRepository.deleteById(id);
    }

    @Override
    public Expert findById(Long id) {
        return expertRepository.findById(id).orElseThrow(() -> new IdIsNotExist("the id not found"));
    }

    @Override
    public Expert findByUserName(String userName) {
        return expertRepository.findByUsername(userName).orElseThrow(() -> new UsernameNotFoundException("the username not found "));
    }

    @Override
    public Expert changPassword(String oldPassword, String userName, String newPassword) {
        Expert expert = findByUserName(userName);
        if (!expert.getPassword().equals(oldPassword)) throw new TheInputInformationIsNotValidException("The Input Information Is Not Valid ");
        expert.setPassword(newPassword);
        return expertRepository.save(expert);
    }

    @Override
    public List<Expert> findAll() {
        return expertRepository.findAll();
    }

    @Override
    public Expert signIn(String userName, String password) {
        Optional<Expert> byUserName = expertRepository.findByUsername(userName);
        if (byUserName.isEmpty() || !byUserName.get().getPassword().equals(password))
            throw new UserNameOrPasswordDosNotExistException("UserName Or Password Dos Not Exist");
        return byUserName.get();
    }

    @Override
    public Expert signUpWithPicture(Expert expert, MultipartFile multipartFile) throws IOException {
        Wallet build = Wallet.builder().user(expert).Balance(0L).build();
        expert.setPassword(passwordEncoder.encode(expert.getPassword()));
        expert.setRole(Role.ROLE_EXPERT);
        expert.setWallet(build);
        expert.setRegisterDate(LocalDate.now());
        expert.setExpert_status(EXPERT_STATUS.NEW);
        expert.setProfilePicture(multipartFile.getBytes());
        expert.setIsEnabled(false);
        walletService.saveOrUpdate(build);
        return expertRepository.save(expert);
    }
    @Override
    public Expert signUp(Expert expert) {
        Wallet build = Wallet.builder().user(expert).Balance(0L).build();
        expert.setPassword(passwordEncoder.encode(expert.getPassword()));
        expert.setRole(Role.ROLE_EXPERT);
        expert.setWallet(build);
        expert.setRegisterDate(LocalDate.now());
        expert.setExpert_status(EXPERT_STATUS.NEW);
        expert.setIsEnabled(false);
        walletService.saveOrUpdate(build);
        return expertRepository.save(expert);
    }

    @Override
    public List<Expert> searchFromExpert(ExpertFilter expertFilter) {
        Specification<Expert> spec = ExpertSpec.filterBy(expertFilter);
        return expertRepository.findAll(spec);
    }

    @Override
    public Expert findByEmailAddress(String emailAddress) {
        return expertRepository.findByEmailAddress(emailAddress).orElseThrow(()->new TheEmailNotFoundException("email address not found"));
    }

    @Override
    public MultipartFile getExpertImage() throws IOException {
        Expert expert = expertRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));
        //todo : image
        return null;
    }
}
