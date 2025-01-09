package com.soulmate.soulmate_ai_backend.conversations;

import com.soulmate.soulmate_ai_backend.profiles.Profile;
import com.soulmate.soulmate_ai_backend.profiles.ProfileRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;
@CrossOrigin
@RestController
public class ConversationController {

    private ConversationRepository conversationRepository;

    private ProfileRepository profileRepository;

    private ConversationService conversationService;

    public ConversationController(ConversationRepository conversationRepository,ProfileRepository profileRepository,ConversationService conversationService)
    {
        this.conversationRepository=conversationRepository;
        this.profileRepository=profileRepository;
        this.conversationService=conversationService;
    }

    @PostMapping("/conversations")
    public Conversation createNewConverSation(@RequestBody CreateConversationRequest request){

        profileRepository.findById(request.profileId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Unable to find the profile ID "+request.profileId()));

        Conversation conversation=new Conversation(
                UUID.randomUUID().toString(),
                request.profileId(),
                new ArrayList<>()
        );
        conversationRepository.save(conversation);
        return conversation;
    }

    @PostMapping("/conversations/{conversationId}")
    public Conversation addMessageToConverSation(@PathVariable String conversationId,@RequestBody ChatMessage chatMessage){

        Conversation conversation=conversationRepository.findById(conversationId)
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Unable to find conversation with the ID "+conversationId));

        String matchProfileId=conversation.profileId();

        Profile profile=profileRepository.findById(matchProfileId).orElseThrow(()-> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Unable to find a profile with ID "+ matchProfileId
        ));

        Profile user=profileRepository.findById(chatMessage.authorId())
                .orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Unable to find the profile ID "+chatMessage.authorId()));

        ChatMessage messageWithTime =new ChatMessage(
                chatMessage.messageText(),
                chatMessage.authorId(),
                LocalDateTime.now()
        );
        conversation.messages().add(messageWithTime);

        conversationService.generateProfileResponse(conversation,profile,user);

        conversationRepository.save(conversation);
        return conversation;
    }

    @GetMapping("/conversations/{conversationsId}")
    public Conversation getConversation(@PathVariable String conversationsId){
        return conversationRepository.findById(conversationsId)
                .orElseThrow(()-> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find conversation with the ID "+conversationsId
                ));
    }

    public record CreateConversationRequest(
            String profileId
    ){
    }


}
