//PlayerService.java Handles logic and data fetching

package com.example.twoxko.service;

import com.example.twoxko.model.Match;
import com.example.twoxko.model.PlayerResponse;
import com.example.twoxko.model.Winner;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class PlayerService {

    private static final Map<String, PlayerResponse> MOCK_PLAYERS = new HashMap<>();

    // hardcoding fake match histories before API goes live
    static {
        // User 1: SonicFox
        List<Match> sonicFoxMatches = Arrays.asList(
            new Match(
                UUID.randomUUID().toString(),
                Instant.now().minusSeconds(3000),
                "SonicFox",
                "Leffen",
                "Challenger",
                "Diamond 3",
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
                Winner.P1
            )
        );

        int sonicFoxWins = (int) sonicFoxMatches.stream().filter(m -> m.getWinner() == Winner.P1).count();
        int sonicFoxLosses = sonicFoxMatches.size() - sonicFoxWins;
        MOCK_PLAYERS.put("SonicFox", new PlayerResponse("SonicFox", sonicFoxWins, sonicFoxLosses, sonicFoxMatches));

        // User 2: Leffen
        List<Match> leffenMatches = Arrays.asList(
            new Match(
                UUID.randomUUID().toString(),
                Instant.now().minusSeconds(5000),
                "Leffen",
                "Boosted Bronzie",
                "Diamond 2",
                "Bronze 1",
                Arrays.asList("Jinx", "Yasuo"),
                Arrays.asList("Vi", "Ekko"),
                "2X Assist",
                "Double Down",
                2,
                Winner.P1
            ),
            new Match(
                UUID.randomUUID().toString(),
                Instant.now().minusSeconds(9000),
                "Leffen",
                "OppenheimerTeemo",
                "Diamond 2",
                "Platinum 1",
                Arrays.asList("Ahri", "Braum"),
                Arrays.asList("Warwick", "Teemo"),
                "2X Assist",
                "Sidekick",
                3,
                Winner.P1
            )
        );

        int leffenWins = (int) leffenMatches.stream().filter(m -> m.getWinner() == Winner.P1).count();
        int leffenLosses = leffenMatches.size() - leffenWins;
        MOCK_PLAYERS.put("Leffen", new PlayerResponse("Leffen", leffenWins, leffenLosses, leffenMatches));
    }

    // Method that handles lookups
    // matchCount is how many last X matches we look up. Right now, not in use.
    public PlayerResponse getPlayerByRiotId(String riotId, int matchCount) {
        return MOCK_PLAYERS.getOrDefault(riotId, MOCK_PLAYERS.get("SonicFox"));
    }
}