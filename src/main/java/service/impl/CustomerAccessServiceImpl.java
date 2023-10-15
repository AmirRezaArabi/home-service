package service.impl;

import domain.Customer;
import domain.CustomerRequest;
import domain.Service;
import domain.SubService;
import domain.enums.REQUEST_STATUS;
import exception.TheSuggestedPriceIsLowerThanBasePriceExveption;
import lombok.RequiredArgsConstructor;
import service.*;

import java.time.LocalDate;
@RequiredArgsConstructor
public class CustomerAccessServiceImpl implements CustomerAccessService {
    private final CustomerService customerService;
    private final SubServiceService subServiceService;
    private final CustomerRequestService customerRequestService;

    @Override
    public boolean placeAnOrder(String underServiceName, String customerUserName, LocalDate time, Long suggestionPrice, String description, String address) {

        Customer customer = customerService.findByUserName(customerUserName).get();
        SubService subService = subServiceService.findByName(underServiceName).get();
        if (suggestionPrice<subService.getBasePrice())
            throw new TheSuggestedPriceIsLowerThanBasePriceExveption("The suggested price is lower than the base price");
        Service service = subService.getService();
        CustomerRequest customerRequest = CustomerRequest.builder().subService(subService)
                .customer(customer).startDay(time).suggestionPrice(suggestionPrice)
                .description(description).address(address).request_status(REQUEST_STATUS.WAITING_FOR_SUGGESTION)
                .service(service).build();
        return customerRequestService.saveOrUpdate(customerRequest)!=null;
    }
}
