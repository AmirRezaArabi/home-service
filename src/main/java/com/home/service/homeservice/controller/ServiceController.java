package com.home.service.homeservice.controller;

import com.home.service.homeservice.domain.Service;
import com.home.service.homeservice.dto.request.ServiceRequestDTO;
import com.home.service.homeservice.service.AdminAccessService;
import com.home.service.homeservice.service.ServiceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/service")
@RequiredArgsConstructor
public class ServiceController {

    private final AdminAccessService adminAccessService;
    private final ModelMapper modelMapper;
    private final ServiceService serviceService;

    @PostMapping("/add")
    ResponseEntity<Service> signUp(@RequestParam String serviceName) {
        return new ResponseEntity<>(adminAccessService.addService(serviceName), HttpStatus.CREATED);
    }


    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        if (serviceService.findById(id).isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        serviceService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/update")
    ResponseEntity<ServiceRequestDTO> updateCustomer(@RequestBody ServiceRequestDTO serviceRequestDTO) {
        Service service = modelMapper.map(serviceRequestDTO, Service.class);
        serviceService.saveOrUpdate(service);
        return new ResponseEntity<>(modelMapper.map(service, ServiceRequestDTO.class), HttpStatus.OK);
    }

}
