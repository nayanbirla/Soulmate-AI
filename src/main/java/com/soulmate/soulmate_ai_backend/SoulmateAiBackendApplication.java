package com.soulmate.soulmate_ai_backend;

import com.soulmate.soulmate_ai_backend.conversations.ChatMessage;
import com.soulmate.soulmate_ai_backend.conversations.Conversation;
import com.soulmate.soulmate_ai_backend.conversations.ConversationRepository;
import com.soulmate.soulmate_ai_backend.profiles.Gender;
import com.soulmate.soulmate_ai_backend.profiles.Profile;
import com.soulmate.soulmate_ai_backend.profiles.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class SoulmateAiBackendApplication implements CommandLineRunner {

	@Autowired
	ProfileRepository profileRepository;

	@Autowired
	ConversationRepository conversationRepository;

	public static void main(String[] args) {
		SpringApplication.run(SoulmateAiBackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		profileRepository.deleteAll();
        conversationRepository.deleteAll();
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
		Profile profile1=new Profile(
				"2",
				"Aman",
				"Yadav",
				21,
				"Indian",
				Gender.MALE,
				"Software Engineer",
				"abd.jpg",
				"INTP"
		);
		profileRepository.save(profile);
		profileRepository.save(profile1);
		profileRepository.findAll().forEach(System.out::println);

		Conversation conversation=new Conversation(
				"1",
				profile.id(),
				List.of(new ChatMessage("Hello",profile.id(), LocalDateTime.now()))
		);

		conversationRepository.save(conversation);
		conversationRepository.findAll().forEach(System.out::println);
	}
}
