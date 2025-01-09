package com.soulmate.soulmate_ai_backend.matches;

import com.soulmate.soulmate_ai_backend.profiles.Profile;

public record Match(String id,Profile profile,String conversationId) {

}
