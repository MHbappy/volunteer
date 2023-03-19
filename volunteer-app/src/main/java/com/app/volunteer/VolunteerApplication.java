package com.app.volunteer;

import com.app.volunteer.model.UserRole;
import com.app.volunteer.model.Users;
import com.app.volunteer.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
@OpenAPIDefinition
public class VolunteerApplication implements CommandLineRunner {

	@Autowired
	private UserService userService;

	public static void main(String[] args) {
		SpringApplication.run(VolunteerApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Bean
	public RestTemplate restTemplate(){
		RestTemplate restTemplate = new RestTemplate();
		return restTemplate;
	}

	/**
	 * create default user
	 * @param params
	 * @throws Exception
	 */
	@Override
	public void run(String... params) throws Exception {
		Users volunteer = null;
		Users librarian = null;
		Users account = null;
		try {
			volunteer = userService.search("volunteer@localhost");
			librarian = userService.search("librarian@localhost");
			account = userService.search("library@localhost");
		}catch (Exception ex){
			ex.printStackTrace();
		}
		if (volunteer == null){
			Users admin = new Users();
			admin.setPassword("123456");
			admin.setEmail("volunteer@localhost");
			admin.setAppUserRoles(new ArrayList<UserRole>(Arrays.asList(UserRole.VOLUNTEER)));
			userService.signup(admin);
		}

		if (librarian == null){
			Users client = new Users();
			client.setPassword("123456");
			client.setEmail("librarian@localhost");
			client.setAppUserRoles(new ArrayList<UserRole>(Arrays.asList(UserRole.LIBRARIAN)));
			userService.signup(client);
		}

		if (account == null){
			Users client = new Users();
			client.setPassword("123456");
			client.setEmail("library@localhost");
			client.setAppUserRoles(new ArrayList<UserRole>(Arrays.asList(UserRole.ACCOUNT)));
			userService.signup(client);
		}
	}
}
