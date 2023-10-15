package service.impl;

import domain.Expert;
import exception.TheInputInformationIsNotValidException;
import lombok.RequiredArgsConstructor;
import repository.ExpertRepository;
import service.ExpertService;

import java.util.List;
import java.util.Optional;

import static validation.EntityValidator.isValid;

@RequiredArgsConstructor
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
