package com.home.service.homeservice;



import com.home.service.homeservice.domain.Expert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.home.service.homeservice.repository.ExpertRepository;


@SpringBootApplication
public class HomeServiceApplication implements CommandLineRunner {




	public static void main(String[] args) {
		SpringApplication.run(HomeServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

	}
}

