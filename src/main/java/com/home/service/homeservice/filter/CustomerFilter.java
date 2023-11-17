package com.home.service.homeservice.filter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public final class CustomerFilter {
    private  String name ;
    private  String emailAddress;

}
