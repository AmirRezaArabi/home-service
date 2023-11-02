package com.home.service.homeservice.config;

import com.home.service.homeservice.utility.CustomerMapper;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationContext {

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
