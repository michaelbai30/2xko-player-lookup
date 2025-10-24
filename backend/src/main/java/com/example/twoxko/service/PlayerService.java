//PlayerService.java Handles logic and data fetching

package com.example.twoxko.service;

import com.example.twoxko.model.Match;
import com.example.twoxko.model.PlayerResponse;
import com.example.twoxko.model.Winner;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class PlayerService{
    public PlayerResponse getPlayerByRiotId(String riotId, int matchCount){
        // Simulate Recent Matches

        List<Match> matches = Arrays.asList(
            new Match(
                UUID.randomUUID().toString(),
                Instant.now().minusSeconds(3000),
                riotId,
                "Opponent#1234",
                "Diamond",
                "Platinum",
                Arrays.asList("Ahri", "Ekko"),
                Arrays.asList("Vi", "Illaoi"),
                "Double Down",
                "2X Assist",
                3,
                Winner.P1
            ),

            new Match(
                UUID.randomUUID().toString(),
                Instant.now().minusSeconds(7000),
                riotId,
                "Diamond",
                "Masters",
                "Opponent#5678",
                Arrays.asList("Warwick", "Ahri"),
                Arrays.asList("Teemo", "Braum"),
                "2X Assist",
                "Double Down",
                3,
                Winner.P2
            )
        );

        int wins = (int) matches.stream().filter(m -> m.getWinner() == Winner.P1).count();
        int losses = matches.size() - wins;

        return new PlayerResponse(riotId, wins, losses, matches);

    }

}