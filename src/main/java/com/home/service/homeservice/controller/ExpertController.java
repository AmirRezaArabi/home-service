package com.home.service.homeservice.controller;

import com.home.service.homeservice.domain.Expert;
import com.home.service.homeservice.domain.Suggestion;
import com.home.service.homeservice.dto.request.CustomerRequestReqDTO;
import com.home.service.homeservice.dto.request.ExpertReqDTO;
import com.home.service.homeservice.dto.request.SuggestionRequestDTO;
import com.home.service.homeservice.dto.response.ExpertResDTO;
import com.home.service.homeservice.dto.response.SuggestionResponseDTO;
import com.home.service.homeservice.service.ExpertAccessService;
import com.home.service.homeservice.service.ExpertService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/expert")
@RequiredArgsConstructor
public class ExpertController {

    private final ModelMapper modelMapper;
    private final ExpertService expertService;
    private final ExpertAccessService expertAccessService;

    @PostMapping("/signup")
    ResponseEntity<ExpertResDTO> signUp(@RequestBody @Valid ExpertReqDTO expertReqDTO) {
        Expert expert = modelMapper.map(expertReqDTO, Expert.class);
        expertService.signUp(expert);
        return new ResponseEntity<>(modelMapper.map(expert, ExpertResDTO.class), HttpStatus.CREATED);
    }

    @GetMapping("/signIn")
    ResponseEntity<ExpertResDTO> signUp(@RequestParam String userName, @RequestParam String password) {
        Expert expert = expertService.signIn(userName, password);
        return new ResponseEntity<>(modelMapper.map(expert, ExpertResDTO.class), HttpStatus.FOUND);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        if (expertService.findById(id).isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        expertService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    ResponseEntity<ExpertResDTO> updateCustomer(@RequestBody ExpertResDTO expertReqDTO) {
        Expert expert = modelMapper.map(expertReqDTO, Expert.class);
        expertService.saveOrUpdate(expert);
        return new ResponseEntity<>(modelMapper.map(expert, ExpertResDTO.class), HttpStatus.OK);
    }

    @PostMapping("/send-suggestion")
    ResponseEntity<SuggestionResponseDTO> sendSuggestion(@RequestBody SuggestionRequestDTO suggestionRequestDTO) {
        Expert expert = expertService.findById(suggestionRequestDTO.getExpertId()).get();
        Suggestion suggestion = expertAccessService.sendSuggestionByExpert
                (expert, suggestionRequestDTO.getCustomerRequestId(),
                suggestionRequestDTO.getPrice(),
                suggestionRequestDTO.getStartWorkDay().toLocalDateTime().toLocalDate()
                , suggestionRequestDTO.getDuration());


        return new ResponseEntity<>( modelMapper.map(suggestion,SuggestionResponseDTO.class)
                ,HttpStatus.CREATED);
    }
    @GetMapping("/show-my-request/{id}")
    ResponseEntity<?> showCustomerRequest(@PathVariable Long id){
        List<CustomerRequestReqDTO> customerRequestReqDTOS = expertAccessService.showCustomRequest(id)
                .stream()
                .map(cr -> modelMapper.map(cr, CustomerRequestReqDTO.class))
                .toList();
        return  new ResponseEntity<>(customerRequestReqDTOS,HttpStatus.OK);
    }
}