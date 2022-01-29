package com.example.batch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.sql.SQLException;

@SpringBootApplication
public class BatchApplication {

	public static void main(String[] args) throws SQLException {
		//TODO : ProcessBuilder pour lancer h2 sur processus different
		SpringApplication.run(BatchApplication.class, args);
	}

}
