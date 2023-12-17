package com.projet;

import com.projet.entities.Client;
import com.projet.metier.CustomerMetierInterface;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class ProjetApplication {

	static CustomerMetierInterface customerMetier;


	public static void main(String[] args) {

		ApplicationContext contexte = SpringApplication.run(ProjetApplication.class, args);

	}



}
