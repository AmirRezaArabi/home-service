package com.home.service.homeservice.utility;

import com.home.service.homeservice.domain.Customer;
import com.home.service.homeservice.dto.request.CustomerRequestDTO;
import com.home.service.homeservice.dto.response.CustomerResponseDTO;


import java.util.Iterator;
import java.util.List;

//@Mapper(componentModel = "spring")
public interface CustomerMapper {

     //CustomerMapper INSTANCE = Mappers.getMapper( CustomerMapper.class );


    CustomerResponseDTO customerToResponseDTO(Customer customer);
    List<CustomerResponseDTO> customerToResponseDTO (Iterator<Customer> customerIterator);
    Customer responseDTOToCustomer(CustomerResponseDTO customerResponseDTO);
    List<Customer> responseDTOToCustomer (Iterator<CustomerResponseDTO> customerResponseDTOIterator);


    CustomerRequestDTO customerToRequestDTO(Customer customer);
    List<CustomerRequestDTO> customerToRequestDTO (Iterator<Customer> customerIterator);
    Customer requestDTOToCustomer(CustomerRequestDTO customerRequestDTO);
    List<Customer> requestDTOToCustomer (Iterator<CustomerRequestDTO> customerRequestDTOIterator);

}
