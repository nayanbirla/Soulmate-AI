package com.soulmate.soulmate_ai_backend;

import com.soulmate.soulmate_ai_backend.profiles.Gender;
import com.soulmate.soulmate_ai_backend.profiles.Profile;
import com.soulmate.soulmate_ai_backend.profiles.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SoulmateAiBackendApplication implements CommandLineRunner {

	@Autowired
	ProfileRepository profileRepository;

	public static void main(String[] args) {
		SpringApplication.run(SoulmateAiBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Profile profile=new Profile(
				"1",
				"Nayan",
				"Birla",
				22,
				"Indian",
				Gender.MALE,
				"Software Engineer",
				"abc.jpg",
				"INTP"
		);
		profileRepository.save(profile);
		profileRepository.findAll().forEach(System.out::println);
	}
}
