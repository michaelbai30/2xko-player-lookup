//PlayerService.java Handles logic and data fetching

package com.example.twoxko.service;

import com.example.twoxko.model.Match;
import com.example.twoxko.model.PlayerResponse;
import com.example.twoxko.model.Winner;
import com.example.twoxko.util.EloStorage;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class PlayerService{
    private static final Map<String, PlayerResponse> PLAYERS = new HashMap<>();

    static {
        // Hardcoded player data to simulate matches
        // SonicFox matches
        List<Match> sonicFoxMatches = Arrays.asList(
            new Match(
                UUID.randomUUID().toString(),
                Instant.now().minusSeconds(3000),
                "SonicFox",
                "Leffen",
                "Challenger",
                "Challenger",
                Arrays.asList("Ahri", "Ekko"),
                Arrays.asList("Braum", "Teemo"),
                "Double Down",
                "2X Assist",
                3,
                Winner.P1
            ),
            new Match(
                UUID.randomUUID().toString(),
                Instant.now().minusSeconds(6000),
                "SonicFox",
                "Diaphone",
                "Challenger",
                "Platinum 3",
                Arrays.asList("Ahri", "Ekko"),
                Arrays.asList("Vi", "Illaoi"),
                "Double Down",
                "2X Assist",
                2,
                Winner.P2
            )
        );
        // Leffen matches
        List<Match> leffenMatches = Arrays.asList(
            new Match(
                UUID.randomUUID().toString(),
                Instant.now().minusSeconds(5000),
                "Leffen",
                "Boosted Bronzie",
                "Challenger",
                "Bronze 1",
                Arrays.asList("Jinx", "Yasuo"),
                Arrays.asList("Vi", "Ekko"),
                "2X Assist",
                "Double Down",
                2,
                Winner.P2
            ),
            new Match(
                UUID.randomUUID().toString(),
                Instant.now().minusSeconds(9000),
                "Leffen",
                "OppenheimerTeemo",
                "Challenger",
                "Platinum 1",
                Arrays.asList("Ahri", "Braum"),
                Arrays.asList("Warwick", "Teemo"),
                "2X Assist",
                "Sidekick",
                3,
                Winner.P1
            )
        );

        //Seed Base Elos for mock players
        EloStorage.seedEloFromRank("SonicFox", "Challenger");
        EloStorage.seedEloFromRank("Diaphone", "Platinum3");
        EloStorage.seedEloFromRank("Leffen", "Challenger");
        EloStorage.seedEloFromRank("Boosted Bronzie", "Bronze 1");
        EloStorage.seedEloFromRank("OppenheimerTeemo", "Master 1");
        
        // Simulate elo progression over match history
       sonicFoxMatches.forEach(EloStorage::simulateEloUpdate);
       leffenMatches.forEach(EloStorage::simulateEloUpdate);

        // Count WL and build responses
        int sonicFoxWins = (int) sonicFoxMatches.stream().filter(m -> m.getWinner() == Winner.P1).count();
        int sonicFoxLosses = sonicFoxMatches.size() - sonicFoxWins;
        PLAYERS.put("SonicFox",
            new PlayerResponse(
                "SonicFox",
                sonicFoxWins,
                sonicFoxLosses,
                sonicFoxMatches,
                EloStorage.getPlayerElo("SonicFox")
            )
        );

        int leffenWins = (int) leffenMatches.stream().filter(m -> m.getWinner() == Winner.P1).count();
        int leffenLosses = leffenMatches.size() - leffenWins;
        PLAYERS.put("Leffen",
            new PlayerResponse(
                "Leffen",
                leffenWins,
                leffenLosses,
                leffenMatches,
                EloStorage.getPlayerElo("Leffen`")
            )
        );
    }

    // ---API METHOD---
    // matchCount = how many last X matches to fetch (currently unused)
    // called by PlayerController.java
    public PlayerResponse getPlayerByRiotId(String riotId, int matchCount) {
        return PLAYERS.getOrDefault(riotId, PLAYERS.get("SonicFox"));
    } 
}
