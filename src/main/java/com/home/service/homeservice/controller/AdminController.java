package com.home.service.homeservice.controller;

import com.home.service.homeservice.domain.Expert;
import com.home.service.homeservice.domain.Service;
import com.home.service.homeservice.domain.SubService;
import com.home.service.homeservice.dto.request.OrderFilterReqDTO;
import com.home.service.homeservice.dto.response.CustomerResponseDTO;
import com.home.service.homeservice.dto.response.ExpertResDTO;
import com.home.service.homeservice.dto.response.OrderResDTO;
import com.home.service.homeservice.dto.response.SubServiceResponseDTO;
import com.home.service.homeservice.filter.ExpertFilter;
import com.home.service.homeservice.service.AdminAccessService;
import com.home.service.homeservice.service.OrderService;
import com.home.service.homeservice.service.ServiceService;
import com.home.service.homeservice.service.SubServiceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminAccessService adminAccessService;
    private final ServiceService serviceService;
    private final SubServiceService subServiceService;

    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @PutMapping("/update-price-description")
    ResponseEntity<SubServiceResponseDTO> updatePriceOrDescription(@RequestParam String subServiceName,
                                                                   @RequestParam String description,
                                                                   @RequestParam Long basePrice) {
        SubService subService = adminAccessService.updatePriceOrDescription(subServiceName, description, basePrice);

        return new ResponseEntity<>(modelMapper.map(subService, SubServiceResponseDTO.class), HttpStatus.OK);
    }

    @PostMapping("/add-expert-sub-service")
    ResponseEntity<ExpertResDTO> addExpertToSystem(@RequestParam String expertUserName, @RequestParam String subService) {
        Expert expert = adminAccessService.addExpertToSystem(expertUserName, subService);
        return new ResponseEntity<>(modelMapper.map(expert, ExpertResDTO.class), HttpStatus.OK);
    }

    @DeleteMapping("/remove-expert-sub-service")
    ResponseEntity<ExpertResDTO> removeExpertToSystem(@RequestParam String expertUserName, @RequestParam String subService) {
        Expert expert = adminAccessService.removeExpertToSystem(expertUserName, subService);
        return new ResponseEntity<>(modelMapper.map(expert, ExpertResDTO.class), HttpStatus.OK);
    }

    @PutMapping("/confirm-expert/{expertUserName}")
    ResponseEntity<ExpertResDTO> confirmExpert(@PathVariable String expertUserName) {
        Expert expert = adminAccessService.confirmExpert(expertUserName);
        return new ResponseEntity<>(modelMapper.map(expert, ExpertResDTO.class), HttpStatus.OK);
    }

    @GetMapping("/filter-user")
    ResponseEntity<List<CustomerResponseDTO>> search(@RequestBody ExpertFilter expertFilter) {
        List<CustomerResponseDTO> customerResponseDTOS = adminAccessService.searchFromCustomer(expertFilter)
                .stream()
                .map(cf -> modelMapper
                        .map(cf, CustomerResponseDTO.class)).toList();
        return new ResponseEntity<>(customerResponseDTOS, HttpStatus.OK);
    }

    @GetMapping("/show-all-service")
    ResponseEntity<List<String>> findAllServices() {
        return new ResponseEntity<>(serviceService.findAll().stream().map(Service::getName).toList(), HttpStatus.OK);
    }

    @GetMapping("/show-all-sub-service")
    ResponseEntity<List<String>> findAllSubServices() {
        return new ResponseEntity<>(subServiceService.findAll().stream().map(SubService::getName).toList(), HttpStatus.OK);
    }

    @GetMapping("/filter-order")
    ResponseEntity<List<OrderResDTO>> searchFromOrder(@RequestBody OrderFilterReqDTO orderFilterReqDTO) {
        List<OrderResDTO> orderResDTOS = orderService.searchFromOrders(orderFilterReqDTO).stream().map(o -> modelMapper.map(o, OrderResDTO.class)).toList();
        return new ResponseEntity<>(orderResDTOS, HttpStatus.OK);
    }
    @GetMapping("/show-all-orders")
    ResponseEntity<List<OrderResDTO>> findAllOrders(){
        List<OrderResDTO> orderResDTOS = orderService
                .findAll()
                .stream()
                .map(r -> modelMapper.map(r, OrderResDTO.class))
                .toList();
        return new ResponseEntity<>(orderResDTOS,HttpStatus.OK);
    }

}
