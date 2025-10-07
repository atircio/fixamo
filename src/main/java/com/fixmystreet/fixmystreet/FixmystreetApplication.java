package com.fixmystreet.fixmystreet;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FixmystreetApplication {

	public static void main(String[] args) {

		Dotenv dotenv = Dotenv.load();
		dotenv.entries().forEach( dotenvEntry ->
						System.setProperty(dotenvEntry.getKey(), dotenvEntry.getValue()));


		SpringApplication.run(FixmystreetApplication.class, args);

	}

}
