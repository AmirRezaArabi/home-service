package service;


import domain.Expert;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public interface ExpertService {

    Expert saveOrUpdate (Expert expert);

    String delete (Expert expert);
    Optional<Expert> findById (Long id);
    Optional<Expert> findByUserName (String userName);

    Optional<Expert> changPassword(String oldPassword, String userName, String newPassword );
    List<Expert> findAll();
}
