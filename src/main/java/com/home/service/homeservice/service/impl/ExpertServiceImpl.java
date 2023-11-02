package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.Customer;
import com.home.service.homeservice.domain.Expert;
import com.home.service.homeservice.domain.Wallet;
import com.home.service.homeservice.domain.enums.EXPERT_STATUS;
import com.home.service.homeservice.exception.TheInputInformationIsNotValidException;
import com.home.service.homeservice.exception.UserNameOrPasswordDosNotExistException;
import com.home.service.homeservice.service.WalletService;
import com.home.service.homeservice.utility.CustomerSpec;
import com.home.service.homeservice.utility.ExpertFilter;
import com.home.service.homeservice.utility.ExpertSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import com.home.service.homeservice.repository.ExpertRepository;
import com.home.service.homeservice.service.ExpertService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.home.service.homeservice.validation.EntityValidator.isValid;

@RequiredArgsConstructor
@Service
public class ExpertServiceImpl implements ExpertService {
    private final ExpertRepository expertRepository;
    private final WalletService walletService;

    @Override
    public Expert saveOrUpdate(Expert expert) {
        return expertRepository.save(expert);
    }

    @Override
    public String delete(Expert expert) {
        expertRepository.delete(expert);
        return expert.getUserName();
    }

    @Override
    public void deleteById(Long id) {
        expertRepository.deleteById(id);
    }

    @Override
    public Optional<Expert> findById(Long id) {
        return expertRepository.findById(id);
    }

    @Override
    public Optional<Expert> findByUserName(String userName) {
        return expertRepository.findByUserName(userName);
    }

    @Override
    public Optional<Expert> changPassword(String oldPassword, String userName, String newPassword) {
        Optional<Expert> byUserName = expertRepository.findByUserName(userName);
        if (byUserName.isEmpty() || !byUserName.get().getPassword().equals(oldPassword))
            throw new TheInputInformationIsNotValidException("The Input Information Is Not Valid ");
        Expert expert = byUserName.get();
        expert.setPassword(newPassword);
        return Optional.of(expertRepository.save(expert));
    }

    @Override
    public List<Expert> findAll() {
        return expertRepository.findAll();
    }

    @Override
    public Expert signIn(String userName, String password) {
        Optional<Expert> byUserName = expertRepository.findByUserName(userName);
        if (byUserName.isEmpty()||!byUserName.get().getPassword().equals(password))
            throw new UserNameOrPasswordDosNotExistException("UserName Or Password Dos Not Exist");
        return byUserName.get();
    }

    @Override
    public Expert signUp(Expert expert) {
        Wallet build = Wallet.builder().user(expert).Balance(0L).build();
        expert.setWallet(build);
        expert.setRegisterDate(LocalDate.now());
        expert.setExpert_status(EXPERT_STATUS.NEW);
        walletService.saveOrUpdate(build);
        return expertRepository.save(expert);
    }

    @Override
    public List<Expert> searchFromExpert(ExpertFilter expertFilter) {
        Specification<Expert> spec = ExpertSpec.filterBy(expertFilter);
        return expertRepository.findAll(spec);
    }
}
