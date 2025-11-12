package com.fr.movie_finder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
public class MovieFinderApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieFinderApplication.class, args);
	}

}
