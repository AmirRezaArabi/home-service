package com.home.service.homeservice.service.impl;

import com.home.service.homeservice.domain.Expert;
import com.home.service.homeservice.exception.TheInputInformationIsNotValidException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.home.service.homeservice.repository.ExpertRepository;
import com.home.service.homeservice.service.ExpertService;

import java.util.List;
import java.util.Optional;

import static com.home.service.homeservice.validation.EntityValidator.isValid;

@RequiredArgsConstructor
@Service
public class ExpertServiceImpl implements ExpertService {
    private final ExpertRepository expertRepository;

    @Override
    public Expert saveOrUpdate(Expert expert) {
        if (!isValid(expert))
            return null;
        return expertRepository.save(expert);
    }

    @Override
    public String delete(Expert expert) {
        expertRepository.delete(expert);
        return expert.getUserName();
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
}
