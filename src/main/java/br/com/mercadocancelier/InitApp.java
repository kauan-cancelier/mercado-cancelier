package br.com.mercadocancelier;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import br.com.mercadocancelier.repository.ItemVendaRepository;
import br.com.mercadocancelier.repository.ProdutosRepository;
import br.com.mercadocancelier.repository.VendaRepository;

@SpringBootApplication
public class InitApp {
	
	@Autowired
	ProdutosRepository produtosRepository;
	
	@Autowired
	ItemVendaRepository itemVendaRepository;
	
	@Autowired
	VendaRepository vendaRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(InitApp.class, args);
	}
	
	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
		};
	}
}
