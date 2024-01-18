package com.assist.control;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import static java.lang.System.out;

@SpringBootApplication
public class AssistControlApplication {

	public static void main(String[] args) {

		SpringApplication.run(AssistControlApplication.class, args);

		out.println("█▀▀ █░█ █▄░█ █▀▀ █ █▀█ █▄░█ ▄▀█ █▄░█ █▀▄ █▀█");
		out.println("█▀░ █▄█ █░▀█ █▄▄ █ █▄█ █░▀█ █▀█ █░▀█ █▄▀ █▄█");

	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

}
