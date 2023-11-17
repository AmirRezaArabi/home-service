package com.home.service.homeservice.controller;

import com.home.service.homeservice.domain.SubService;
import com.home.service.homeservice.dto.request.SubServiceRequestDTO;
import com.home.service.homeservice.dto.response.SubServiceResponseDTO;
import com.home.service.homeservice.service.AdminAccessService;
import com.home.service.homeservice.service.SubServiceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/sub-service")
@RestController
@RequiredArgsConstructor
public class SubServiceController {
    private final SubServiceService subServiceService;

    private final AdminAccessService adminAccessService;
    private final ModelMapper modelMapper;

    @PostMapping("/add")
    ResponseEntity<SubServiceResponseDTO> addSubService(@RequestBody SubServiceRequestDTO subServiceRequestDTO) {
        SubService subService = adminAccessService.addSubService(subServiceRequestDTO.getServiceName(),
                subServiceRequestDTO.getName(),
                subServiceRequestDTO.getDescription(),
                subServiceRequestDTO.getBasePrice()
        );
        return new ResponseEntity<>(modelMapper.map(subService, SubServiceResponseDTO.class), HttpStatus.CREATED);
    }


    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteSubService(@PathVariable Long id) {
        subServiceService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    ResponseEntity<SubServiceResponseDTO> updateSubService(@RequestBody SubServiceResponseDTO subServiceResponseDTO) {
        SubService map = modelMapper.map(subServiceResponseDTO, SubService.class);
        SubService subService = subServiceService.saveOrUpdate(map);
        return new ResponseEntity<>(modelMapper.map(subService, SubServiceResponseDTO.class), HttpStatus.OK);
    }
}
