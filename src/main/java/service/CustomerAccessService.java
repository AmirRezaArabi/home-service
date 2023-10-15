package service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
@Service
public interface CustomerAccessService {

    boolean placeAnOrder (String subService,String customerUserName , LocalDate time ,Long suggestionPrice,String description,String address );
}
