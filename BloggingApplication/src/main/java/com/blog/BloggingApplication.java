package com.blog;

import java.util.List;

import javax.sound.midi.Soundbank;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.blog.config.AppConstant;
import com.blog.entity.Role;
import com.blog.repository.RoleRepo;

@SpringBootApplication
public class BloggingApplication implements CommandLineRunner {

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BloggingApplication.class, args);
	}

	//Generally Model Mapper Liberary is used to convert the one model object to another one
	//AS Here Configuration function already avaliable so here we have defined this method
	//Else we can create our own seperate ModelMapper class also
	//Using bean annotation we are saying that we can create the Object of ModelMapper using Autowiring
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		
		try {
		
			Role role1 = new Role();
			role1.setRoleId(AppConstant.ROLE_ADMIN);
			role1.setRoleName("ROLE_ADMIN");

			Role role2 = new Role();
			role2.setRoleId(AppConstant.ROLE_NORMAL);
			role2.setRoleName("ROLE_NORMAL");

			List<Role> rolesList = List.of(role1, role2);

			List<Role> savedRoles = this.roleRepo.saveAll(rolesList);

			savedRoles.forEach(role -> System.out.println(role.getRoleName()));

			//System.out.println("Encoded Password For 12345 = " + passwordEncoder.encode("123"));
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
