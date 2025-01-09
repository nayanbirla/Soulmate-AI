package com.soulmate.soulmate_ai_backend.matches;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface MatchRepository extends MongoRepository<Match,String> {
}
