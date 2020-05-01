package com.example.reactivewebfluxclient;

import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.annotation.Id;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class ReactiveWebfluxClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReactiveWebfluxClientApplication.class, args);
	}

    @Bean
    WebClient client() {
        return WebClient.create("http://localhost:8080");
    }

//    @Bean
//    CommandLineRunner demo(WebClient webClient) {
//	    return args -> {
//            webClient
//                    .get()
//                    .uri("/users")
//                    .exchange()
//                    .flatMapMany( clientResponse -> clientResponse.bodyToFlux(User.class))
//                    .subscribe(System.out::println);
//        };
//    }
}

@RestController
class ClientController {

    @Autowired
    WebClient webClient;

    @GetMapping(value = "hopUsers" , produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<User> hopGetUser() {

       return webClient
                .get()
                .uri("/users")
                .exchange()
                .flatMapMany( clientResponse -> clientResponse.bodyToFlux(User.class));

    }
}


@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
class User {

    @Id
    private String id;
    private String fName;
    private String lName;
    private int age;

}
