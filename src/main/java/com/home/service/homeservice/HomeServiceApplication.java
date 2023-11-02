package com.home.service.homeservice;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class HomeServiceApplication implements CommandLineRunner {

	@Autowired
	ModelMapper modelMapper;



	public static void main(String[] args) {
		SpringApplication.run(HomeServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {


	}


}

