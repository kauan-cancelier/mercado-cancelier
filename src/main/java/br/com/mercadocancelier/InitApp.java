package br.com.mercadocancelier;

import java.awt.EventQueue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.datatype.hibernate5.jakarta.Hibernate5JakartaModule;

import br.com.mercadocancelier.views.TelaPrincipal;

@SpringBootApplication
public class InitApp {

	@Autowired
	private TelaPrincipal telaPrincipal;
	
	public static void main(String[] args) {
		SpringApplicationBuilder builder = new SpringApplicationBuilder(InitApp.class);
		builder.headless(false);
		builder.run(args);
	}

	@Bean
	public Hibernate5JakartaModule jsonHibernate5Module() {
		return new Hibernate5JakartaModule();
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						telaPrincipal.setVisible(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
		};
	}
}
