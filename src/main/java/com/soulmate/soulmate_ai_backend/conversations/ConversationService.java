package com.soulmate.soulmate_ai_backend.conversations;

import com.soulmate.soulmate_ai_backend.profiles.Profile;
import org.springframework.ai.autoconfigure.vertexai.gemini.VertexAiGeminiChatProperties;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.*;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ConversationService {

    @Autowired
    VertexAiGeminiChatModel chatModel;

    public Conversation generateProfileResponse(Conversation conversation, Profile profile,Profile user){

        //System Message

        SystemMessage systemMessage = new SystemMessage(
                "You are a " + profile.age() + "-year-old " + profile.ethnicity() + " " + profile.gender() +
                        " named " + profile.firstName() + " " + profile.lastName() +
                        ". You are matched with a " + user.age() + "-year-old " + user.ethnicity() + " " + user.gender() +
                        " named " + user.firstName() + " " + user.lastName() +
                        " on Soulmate, an app like Tinder. This is an in-app text conversation between you two. " +
                        "Respond as if you are " + profile.firstName() +
                        ", keeping your messages natural, engaging, and appropriate for a Tinder-like chat. " +
                        "Your bio is: '" + profile.bio() +
                        "'. Your Myers-Briggs personality type is " + profile.myersBriggsPersonalityType() +
                        ". Keep responses short, relevant, and conversational. Do not use hashtags. Give your response in maximum 30 words."
        );

        List<AbstractMessage> messages=conversation.messages().stream().map(message ->{
            if(message.authorId().equals(profile.id())){
                return new AssistantMessage(message.messageText());
            }else{
                return new UserMessage(message.messageText());
            }
        }).toList();

        List<Message> allMessages=new ArrayList<>();
        allMessages.addAll(messages);
        allMessages.add(systemMessage);
        Prompt prompt=new Prompt(allMessages);

        ChatResponse response=chatModel.call(prompt);

        conversation.messages().add(new ChatMessage(response.getResult().getOutput().getContent(), profile.id(), LocalDateTime.now()));
        return conversation;
    }

}
