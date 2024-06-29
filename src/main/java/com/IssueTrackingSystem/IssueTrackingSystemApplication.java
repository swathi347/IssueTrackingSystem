package com.IssueTrackingSystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
public class IssueTrackingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(IssueTrackingSystemApplication.class, args);
	}

}
