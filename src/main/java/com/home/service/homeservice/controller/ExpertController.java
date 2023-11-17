package com.home.service.homeservice.controller;

import com.home.service.homeservice.domain.ConfirmationToken;
import com.home.service.homeservice.domain.Expert;
import com.home.service.homeservice.domain.Suggestion;
import com.home.service.homeservice.domain.Wallet;
import com.home.service.homeservice.domain.enums.EXPERT_STATUS;
import com.home.service.homeservice.domain.enums.Role;
import com.home.service.homeservice.dto.request.CustomerRequestReqDTO;
import com.home.service.homeservice.dto.request.ExpertReqDTO;
import com.home.service.homeservice.dto.request.SuggestionRequestDTO;
import com.home.service.homeservice.dto.response.ExpertResDTO;
import com.home.service.homeservice.dto.response.OrderResForScoresDTO;
import com.home.service.homeservice.dto.response.SuggestionResponseDTO;
import com.home.service.homeservice.service.ConfirmationTokenService;
import com.home.service.homeservice.service.ExpertAccessService;
import com.home.service.homeservice.service.ExpertService;
import com.home.service.homeservice.service.impl.EmailSenderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/expert")
@RequiredArgsConstructor
public class ExpertController {

    private final ModelMapper modelMapper;
    private final ExpertService expertService;
    private final ExpertAccessService expertAccessService;

    private final EmailSenderService emailSenderService;
    private final ConfirmationTokenService confirmationTokenService;

    @PostMapping("/signup-with-pic")
    ResponseEntity<ExpertResDTO> signUpWithPicture(@RequestPart @Valid ExpertReqDTO expertReqDTO, @RequestPart MultipartFile
                                        multipartFile) throws IOException {
        Expert expert = modelMapper.map(expertReqDTO, Expert.class);
        expertService.signUpWithPicture(expert,multipartFile);
        return new ResponseEntity<>(modelMapper.map(expert, ExpertResDTO.class), HttpStatus.CREATED);
    }
    @PostMapping("/signup")
    ResponseEntity<ExpertResDTO> signUp(@RequestBody @Valid ExpertReqDTO expertReqDTO) {
        Expert expert = modelMapper.map(expertReqDTO, Expert.class);
        expertService.signUp(expert);
        return new ResponseEntity<>(modelMapper.map(expert, ExpertResDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/signIn")
    ResponseEntity<ExpertResDTO> signIn(@RequestParam String userName, @RequestParam String password) {
        Expert expert = expertService.signIn(userName, password);
        return new ResponseEntity<>(modelMapper.map(expert, ExpertResDTO.class), HttpStatus.FOUND);
    }
    @GetMapping("/confirm-user-account")
    ResponseEntity<?> confirmUserAccount(@RequestParam String emailAddress){
        Expert expert = expertService.findByEmailAddress(emailAddress);
        ConfirmationToken confirmationToken = new ConfirmationToken(expert);
        confirmationTokenService.saveOrUpdate(confirmationToken);
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(emailAddress);
        mailMessage.setFrom("arabiamirreza41@gmail.com");
        mailMessage.setText("to confirm your account click here :"+ "\n" + "http://localhost:9999/expert/click-for-registration?token=" +confirmationToken.getToken());

        emailSenderService.sendEmail(mailMessage);
        return new ResponseEntity<>("The verification link has been sent",HttpStatus.OK);    }

    @GetMapping("/click-for-registration")
    ResponseEntity<?> clickForRegistration(@RequestParam String token)
    {
        ConfirmationToken confirmationToken = confirmationTokenService.findByToken(token);
        Expert expert = expertService.findByEmailAddress(confirmationToken.getUser().getEmailAddress());
        confirmationTokenService.deleteByToken(token);
        if (expert.getIsEnabled())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Your account has already been verified");
        expert.setIsEnabled(true);
        expert.setExpert_status(EXPERT_STATUS.AWAITING_CONFIRMATION);
        expertService.saveOrUpdate(expert);
        return new ResponseEntity<>("account verified",HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EXPERT')")
    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        expertService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EXPERT')")
    @PutMapping("/update")
    ResponseEntity<ExpertResDTO> updateCustomer(@RequestBody ExpertResDTO expertReqDTO) {
        Expert expert = modelMapper.map(expertReqDTO, Expert.class);
        expert.setRole(Role.ROLE_EXPERT);
        expert.setRegisterDate(LocalDate.now());
        expertService.saveOrUpdate(expert);
        return new ResponseEntity<>(modelMapper.map(expert, ExpertResDTO.class), HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EXPERT')")
    @PostMapping("/send-suggestion")
    ResponseEntity<SuggestionResponseDTO> sendSuggestion(@RequestBody SuggestionRequestDTO suggestionRequestDTO) {
        Expert expert = expertService.findById(suggestionRequestDTO.getExpertId());
        Suggestion suggestion = expertAccessService.sendSuggestionByExpert
                (expert, suggestionRequestDTO.getCustomerRequestId(),
                suggestionRequestDTO.getPrice(),
                suggestionRequestDTO.getStartWorkDay().toLocalDateTime().toLocalDate()
                , suggestionRequestDTO.getDuration());


        return new ResponseEntity<>( modelMapper.map(suggestion,SuggestionResponseDTO.class)
                ,HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EXPERT')")
    @GetMapping("/show-my-request/{id}")
    ResponseEntity<?> showCustomerRequest(@PathVariable Long id){
        List<CustomerRequestReqDTO> customerRequestReqDTOS = expertAccessService.showCustomRequest(id)
                .stream()
                .map(cr -> modelMapper.map(cr, CustomerRequestReqDTO.class))
                .toList();
        return  new ResponseEntity<>(customerRequestReqDTOS,HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EXPERT')")
    @GetMapping("show-wallet-balance")
    ResponseEntity<?> showBalance(){
        Wallet wallet = expertService.findByUserName(SecurityContextHolder.getContext().getAuthentication().getName()).getWallet();
        return new ResponseEntity<>(wallet.getBalance(),HttpStatus.OK);
    }

    @PreAuthorize("hasAnyRole('ADMIN','EXPERT')")
    @GetMapping("show-scores")
    ResponseEntity<List<OrderResForScoresDTO>> showScores(){
        List<OrderResForScoresDTO> orderResForScoresDTOS = expertAccessService.findAllByExpertUsername()
                .stream()
                .map(r -> modelMapper.map(r, OrderResForScoresDTO.class))
                .toList();
        return new ResponseEntity<>(orderResForScoresDTOS,HttpStatus.OK);
    }

}