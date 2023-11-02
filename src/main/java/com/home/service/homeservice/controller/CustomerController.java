package com.home.service.homeservice.controller;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.home.service.homeservice.domain.Customer;
import com.home.service.homeservice.domain.CustomerRequest;
import com.home.service.homeservice.domain.Order;
import com.home.service.homeservice.domain.enums.REQUEST_STATUS;
import com.home.service.homeservice.dto.request.BankCard;
import com.home.service.homeservice.dto.request.CustomerRequestDTO;
import com.home.service.homeservice.dto.request.CustomerRequestReqDTO;
import com.home.service.homeservice.dto.response.*;
import com.home.service.homeservice.service.CustomerAccessService;
import com.home.service.homeservice.service.CustomerService;
import com.home.service.homeservice.service.OrderService;
import com.home.service.homeservice.service.UserService;
import com.wf.captcha.SpecCaptcha;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
@Log4j2
public class CustomerController {

    private final ModelMapper modelMapper;
    private final CustomerService customerService;

    private final OrderService orderService;

    private final UserService userService;
    private final CustomerAccessService customerAccessService;

    private AtomicInteger counter = new AtomicInteger();
    private Cache<Integer, String> captchaMap = CacheBuilder.newBuilder().expireAfterWrite(Duration.ofMinutes(10)).build();


    @PostMapping("/signup")
    ResponseEntity<CustomerResponseDTO> signUp(@RequestBody @Valid CustomerRequestDTO customerRequestDTO) {
        Customer customer = modelMapper.map(customerRequestDTO, Customer.class);
        customerService.signUp(customer);
        return new ResponseEntity<>(modelMapper.map(customer, CustomerResponseDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/signIn")
    ResponseEntity<CustomerResponseDTO> signUp(@RequestParam String userName, @RequestParam String password) {
        Customer customer = customerService.signIn(userName, password);
        return new ResponseEntity<>(modelMapper.map(customer, CustomerResponseDTO.class), HttpStatus.FOUND);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        if (customerService.findById(id).isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        customerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    ResponseEntity<CustomerResponseDTO> updateCustomer(@RequestBody CustomerResponseDTO customerResponseDTO) {
        Customer customer = modelMapper.map(customerResponseDTO, Customer.class);
        customerService.saveOrUpdate(customer);
        return new ResponseEntity<>(modelMapper.map(customer, CustomerResponseDTO.class), HttpStatus.OK);
    }

    @PostMapping("/place-request")
    ResponseEntity<CustomerRequestResDTO> placeAnRequest(@RequestBody CustomerRequestReqDTO customerRequestReqDTO) {
        CustomerRequest customerRequest = customerAccessService.placeAnRequest(customerRequestReqDTO.getSubServiceId()
                , customerRequestReqDTO.getCustomerUserName()
                , customerRequestReqDTO.getTime().toLocalDateTime().toLocalDate()
                , customerRequestReqDTO.getPrice()
                , customerRequestReqDTO.getDescription()
                , customerRequestReqDTO.getAddress());

        CustomerRequestResDTO customerRequestResDTO = new CustomerRequestResDTO(customerRequest.getId(), customerRequest.getSubService().getName(), customerRequest.getSuggestionPrice(),
                customerRequest.getCustomer().getUserName(),
                customerRequest.getDescription(),
                customerRequestReqDTO.getTime(),
                customerRequest.getAddress());
        return new ResponseEntity<>(customerRequestResDTO, HttpStatus.CREATED);
    }

    @GetMapping("/show-suggestion/{customerRequestId}")
    ResponseEntity<?> showSuggestion(@PathVariable Long customerRequestId) {
        List<SuggestionResponseDTO> suggestionResponseDTOS = customerAccessService.showSuggestion(customerRequestId)
                .stream()
                .map(suggestion -> modelMapper.map(suggestion, SuggestionResponseDTO.class)).toList();
        return new ResponseEntity<>(suggestionResponseDTOS, HttpStatus.FOUND);
    }


    @GetMapping("/choose-suggestion/{id}")
    ResponseEntity<?> chooseSuggestion(@PathVariable Long id) {
        Order order = customerAccessService.chooseSuggestion(id);
        return new ResponseEntity<>(modelMapper.map(order, OrderResDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/order-done/{id}")
    ResponseEntity<?> orderDone(@PathVariable Long id) {
        Order order = customerAccessService.changeRequestStatusToDone(id);
        return new ResponseEntity<>(modelMapper.map(order, OrderResDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/order-started/{id}")
    ResponseEntity<?> orderStarted(@PathVariable Long id) {
        Order order = customerAccessService.changeRequestStatusToStarted(id);
        return new ResponseEntity<>(modelMapper.map(order, OrderResDTO.class), HttpStatus.CREATED);
    }

    @PostMapping("/pay-by-wallet/{orderId}")
    ResponseEntity<?> payByWallet(@PathVariable Long orderId) {
        userService.payWithWalletBalance(orderId);
        orderService.findById(orderId).get().setRequest_status(REQUEST_STATUS.PAYED);
        return new ResponseEntity<>("payed successfully", HttpStatus.OK);
    }

    @PostMapping("/pay/online")
    ResponseEntity<?> payOnline(BankCard bankCard) {
        String captchaText = captchaMap.getIfPresent(bankCard.captchaId());
        captchaMap.invalidate(bankCard.captchaId());
        if (!captchaText.equalsIgnoreCase(bankCard.captcha()))
            throw new IllegalArgumentException("Invalid captcha");
        log.info("payed by " + bankCard);
        return new ResponseEntity<>(bankCard, HttpStatus.OK);
    }

    @GetMapping("/generate-captcha")
    Captcha generateCaptcha() {
        SpecCaptcha captcha = userService.getCaptcha(130, 48);
        int id = counter.incrementAndGet();
        captchaMap.put(id, captcha.text());
        return new Captcha(id, captcha.toBase64());
    }

}
