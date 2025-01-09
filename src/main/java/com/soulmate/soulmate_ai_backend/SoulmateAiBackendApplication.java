package com.soulmate.soulmate_ai_backend;

import com.soulmate.soulmate_ai_backend.conversations.ChatMessage;
import com.soulmate.soulmate_ai_backend.conversations.Conversation;
import com.soulmate.soulmate_ai_backend.conversations.ConversationRepository;
import com.soulmate.soulmate_ai_backend.matches.MatchRepository;
import com.soulmate.soulmate_ai_backend.profiles.Gender;
import com.soulmate.soulmate_ai_backend.profiles.Profile;
import com.soulmate.soulmate_ai_backend.profiles.ProfileCreationService;
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

	@Autowired
	MatchRepository matchRepository;

//	@Autowired
//	private OllamaChatModel chatModel;

	@Autowired
	private ProfileCreationService profileCreationService;

	public static void main(String[] args) {
		SpringApplication.run(SoulmateAiBackendApplication.class, args);
	}

	public void run(String... args) {

		profileCreationService.saveProfilesToDB();

	}
}
