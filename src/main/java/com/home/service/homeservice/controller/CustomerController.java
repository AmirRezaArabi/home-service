package com.home.service.homeservice.controller;

import com.home.service.homeservice.domain.*;
import com.home.service.homeservice.domain.enums.REQUEST_STATUS;
import com.home.service.homeservice.domain.enums.Role;
import com.home.service.homeservice.dto.request.BankCard;
import com.home.service.homeservice.dto.request.CustomerRequestDTO;
import com.home.service.homeservice.dto.request.CustomerRequestReqDTO;
import com.home.service.homeservice.dto.response.CustomerRequestResDTO;
import com.home.service.homeservice.dto.response.CustomerResponseDTO;
import com.home.service.homeservice.dto.response.OrderResDTO;
import com.home.service.homeservice.service.*;
import com.home.service.homeservice.service.impl.EmailSenderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

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

    private final EmailSenderService emailSenderService;
    private final ConfirmationTokenService confirmationTokenService;

//    private AtomicInteger counter = new AtomicInteger();
//    private Cache<Integer, String> captchaMap = CacheBuilder.newBuilder().expireAfterWrite(Duration.ofMinutes(10)).build();


    @PostMapping("/signup")
    ResponseEntity<CustomerResponseDTO> signUp(@RequestBody @Valid CustomerRequestDTO customerRequestDTO) {
        Customer customer = modelMapper.map(customerRequestDTO, Customer.class);
        customerService.signUp(customer);
        return new ResponseEntity<>(modelMapper.map(customer, CustomerResponseDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/signIn")
    ResponseEntity<CustomerResponseDTO> signIn(@RequestParam String userName, @RequestParam String password) {

        Customer customer = customerService.signIn(userName, password);
        return new ResponseEntity<>(modelMapper.map(customer, CustomerResponseDTO.class), HttpStatus.FOUND);
    }



    @GetMapping("/confirm-user-account")
    ResponseEntity<?> confirmUserAccount(@RequestParam String emailAddress) {
        Customer customer = customerService.findByEmailAddress(emailAddress);
        ConfirmationToken confirmationToken = new ConfirmationToken(customer);
        confirmationTokenService.saveOrUpdate(confirmationToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailAddress);
        mailMessage.setText("to confirm your account click here :" + "http://localhost:9999/customer/click-for-registration?token=" + confirmationToken.getToken());

        emailSenderService.sendEmail(mailMessage);
        return new ResponseEntity<>("The verification link has been sent", HttpStatus.OK);
    }

    @GetMapping("/click-for-registration")
    ResponseEntity<?> clickForRegistration(@RequestParam String token) {
        ConfirmationToken confirmationToken = confirmationTokenService.findByToken(token);
        Customer customer = customerService.findByEmailAddress(confirmationToken.getUser().getEmailAddress());
        confirmationTokenService.deleteByToken(token);
        if (customer.getIsEnabled())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Your account has already been verified");
        customer.setIsEnabled(true);
        customerService.saveOrUpdate(customer);
        return new ResponseEntity<>("account verified", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        customerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @PutMapping("/update")
    ResponseEntity<CustomerResponseDTO> updateCustomer(@RequestBody CustomerResponseDTO customerResponseDTO) {
        Customer customer = modelMapper.map(customerResponseDTO, Customer.class);
        customer.setRole(Role.ROLE_CUSTOMER);
        customer.setRegisterDate(LocalDate.now());
        customerService.saveOrUpdate(customer);
        return new ResponseEntity<>(modelMapper.map(customer, CustomerResponseDTO.class), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @PostMapping("/place-request")
    ResponseEntity<CustomerRequestResDTO> placeAnRequest(@RequestBody CustomerRequestReqDTO customerRequestReqDTO) {
        CustomerRequest customerRequest = customerAccessService.placeAnRequest(customerRequestReqDTO.getSubServiceId()
                , customerRequestReqDTO.getCustomerUserName()
                , customerRequestReqDTO.getTime().toLocalDateTime().toLocalDate()
                , customerRequestReqDTO.getPrice()
                , customerRequestReqDTO.getDescription()
                , customerRequestReqDTO.getAddress());

        CustomerRequestResDTO customerRequestResDTO = new CustomerRequestResDTO(customerRequest.getId(), customerRequest.getSubService().getName(), customerRequest.getSuggestionPrice(),
                customerRequest.getCustomer().getUsername(),
                customerRequest.getDescription(),
                customerRequestReqDTO.getTime(),
                customerRequest.getAddress());
        return new ResponseEntity<>(customerRequestResDTO, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")

    @GetMapping("/show-suggestion/{customerRequestId}")
    ResponseEntity<?> showSuggestion(@PathVariable Long customerRequestId) {
        List<Suggestion> allByCustomerRequestOrderBySuggestionPriceAsc = customerAccessService
                .findAllByCustomerRequestOrderBySuggestionPriceAsc(customerRequestId);
        return new ResponseEntity<>(allByCustomerRequestOrderBySuggestionPriceAsc, HttpStatus.FOUND);
    }


    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @GetMapping("/choose-suggestion/{id}")
    ResponseEntity<?> chooseSuggestion(@PathVariable Long id) {
        Order order = customerAccessService.chooseSuggestion(id);
        return new ResponseEntity<>(modelMapper.map(order, OrderResDTO.class), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @GetMapping("/order-done/{id}")
    ResponseEntity<?> orderDone(@PathVariable Long id) {
        Order order = customerAccessService.changeRequestStatusToDone(id);
        return new ResponseEntity<>(modelMapper.map(order, OrderResDTO.class), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @GetMapping("/order-started/{id}")
    ResponseEntity<?> orderStarted(@PathVariable Long id) {
        Order order = customerAccessService.changeRequestStatusToStarted(id);
        return new ResponseEntity<>(modelMapper.map(order, OrderResDTO.class), HttpStatus.CREATED);
    }
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @PostMapping("/pay-by-wallet/{orderId}")
    ResponseEntity<?> payByWallet(@PathVariable Long orderId) {
        userService.payWithWalletBalance(orderId);
        orderService.findById(orderId).setStatus(REQUEST_STATUS.PAYED);
        return new ResponseEntity<>("payed successfully", HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @PostMapping("/pay/online")
    ResponseEntity<?> payOnline(BankCard bankCard) {
//        String captchaText = captchaMap.getIfPresent(bankCard.captchaId());
//        captchaMap.invalidate(bankCard.captchaId());
//        if (!captchaText.equalsIgnoreCase(bankCard.captcha()))
//            throw new IllegalArgumentException("Invalid captcha");
        log.info("payed by " + bankCard);
        return new ResponseEntity<>(bankCard, HttpStatus.OK);
    }

    //    @GetMapping("/generate-captcha")
//    Captcha generateCaptcha() {
//        SpecCaptcha captcha = userService.getCaptcha(130, 48);
//        int id = counter.incrementAndGet();
//        captchaMap.put(id, captcha.text());
//        return new Captcha(id, captcha.toBase64());
//    }
    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @PostMapping("/set-comment/{orderId}")
    ResponseEntity<Long> setComment(@RequestParam String comment, @PathVariable Long orderId) {
        Order order = customerAccessService.setCommentByCustomer(orderId, comment);
        return new ResponseEntity<>(order.getId(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @PostMapping(("/set-score/{orderId}"))
    ResponseEntity<Long> setScore(@RequestParam int score, @PathVariable Long orderId) {
        Order order = customerAccessService.setScoreByCustomer(orderId, score);
        return new ResponseEntity<>(order.getId(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @GetMapping("show-wallet-balance")
    ResponseEntity<?> showBalance() {
        Wallet wallet = customerService.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).getWallet();
        return new ResponseEntity<>(wallet.getBalance(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @GetMapping("show-orders")
    ResponseEntity<?> showOrders(@RequestParam String status) {
        REQUEST_STATUS request_status = REQUEST_STATUS.valueOf(status);
        List<?> ordersByRequestStatus = customerAccessService.findOrdersByRequestStatus(request_status);
        return new ResponseEntity<>(ordersByRequestStatus.size(), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','CUSTOMER')")
    @GetMapping("show-orders-for-pay")
    ResponseEntity<?> showOrdersForPay() {
        List<OrderResDTO> orderResDTOS = customerAccessService.findOrdersForPay()
                .stream()
                .map(r -> modelMapper.map(r, OrderResDTO.class))
                .toList();

        return new ResponseEntity<>(orderResDTOS,HttpStatus.OK);
    }

}
