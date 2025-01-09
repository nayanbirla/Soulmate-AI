package com.soulmate.soulmate_ai_backend.matches;

import com.soulmate.soulmate_ai_backend.conversations.Conversation;
import com.soulmate.soulmate_ai_backend.conversations.ConversationRepository;
import com.soulmate.soulmate_ai_backend.profiles.Profile;
import com.soulmate.soulmate_ai_backend.profiles.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@CrossOrigin
@RestController
public class MatchController {

    @Autowired
    ProfileRepository profileRepository;

    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    private MatchRepository matchRepository;

    public record CreateMatchRequest(String profileId) {}

    @PostMapping("/matches")
    public Match createNewMatch(@RequestBody MatchController.CreateMatchRequest request){

       Profile profile= profileRepository.findById(request.profileId())
                .orElseThrow(()->new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Unable to find a profile with ID "+request.profileId())
                );

       Conversation conversation=new Conversation(
              UUID.randomUUID().toString(),
              request.profileId(),
              new ArrayList<>()
      );
      conversationRepository.save(conversation);
      Match match=new Match(UUID.randomUUID().toString(),profile,conversation.id());
      matchRepository.save(match);
      return match;
    }

    @GetMapping("/matches")
    public List<Match> getAllMatches(){
        return matchRepository.findAll();
    }
}
