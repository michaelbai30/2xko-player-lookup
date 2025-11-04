//PlayerService.java Handles logic and data fetching

package com.example.twoxko.service;

import com.example.twoxko.model.Match;
import com.example.twoxko.model.PlayerResponse;
import com.example.twoxko.model.Winner;
import com.example.twoxko.util.EloCalculator;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class PlayerService{

    private static final Map<String, Double> RANK_TO_ELO_BASE = new HashMap<>();
    private static final Map<String, PlayerResponse> MOCK_PLAYERS = new HashMap<>();
    private static final Map<String, Double> MOCK_PLAYER_ELOS = new HashMap<>();

    static {
        // Rank to Elo Base Table
        // TODO: Move this somewhere else
        RANK_TO_ELO_BASE.put("Aspirant", 0.0);

        RANK_TO_ELO_BASE.put("Iron 1", 166.0);
        RANK_TO_ELO_BASE.put("Iron 2", 332.0);
        RANK_TO_ELO_BASE.put("Iron 3", 500.0);

        RANK_TO_ELO_BASE.put("Bronze 1", 666.0);
        RANK_TO_ELO_BASE.put("Bronze 2", 832.0);
        RANK_TO_ELO_BASE.put("Bronze 3", 1000.0);

        RANK_TO_ELO_BASE.put("Silver 1", 1166.0);
        RANK_TO_ELO_BASE.put("Silver 2", 1332.0);
        RANK_TO_ELO_BASE.put("Silver 3", 1500.0);

        RANK_TO_ELO_BASE.put("Gold 1", 1666.0);
        RANK_TO_ELO_BASE.put("Gold 2", 1832.0);
        RANK_TO_ELO_BASE.put("Gold 3", 2000.0);

        RANK_TO_ELO_BASE.put("Platinum 1", 2166.0);
        RANK_TO_ELO_BASE.put("Platinum 2", 2332.0);
        RANK_TO_ELO_BASE.put("Platinum 3", 2500.0);

        RANK_TO_ELO_BASE.put("Emerald 1", 2666.0);
        RANK_TO_ELO_BASE.put("Emerald 2", 2832.0);
        RANK_TO_ELO_BASE.put("Emerald 3", 3000.0);

        RANK_TO_ELO_BASE.put("Diamond 1", 3166.0);
        RANK_TO_ELO_BASE.put("Diamond 2", 3332.0);
        RANK_TO_ELO_BASE.put("Diamond 3", 3500.0);

        RANK_TO_ELO_BASE.put("Master 1", 3666.0);
        RANK_TO_ELO_BASE.put("Master 2", 3832.0);
        RANK_TO_ELO_BASE.put("Master 3", 4000.0);

        RANK_TO_ELO_BASE.put("Grandmaster 1", 4166.0);
        RANK_TO_ELO_BASE.put("Grandmaster 2", 4332.0);
        RANK_TO_ELO_BASE.put("Grandmaster 3", 4500.0);

        RANK_TO_ELO_BASE.put("Challenger", 5000.0);

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
        MOCK_PLAYER_ELOS.put("SonicFox", getEloFromRank("Challenger"));
        MOCK_PLAYER_ELOS.put("Leffen", getEloFromRank("Challenger"));
        MOCK_PLAYER_ELOS.put("Diaphone", getEloFromRank("Platinum 3"));
        MOCK_PLAYER_ELOS.put("Boosted Bronzie", getEloFromRank("Bronze 1"));
        MOCK_PLAYER_ELOS.put("OppenheimerTeemo", getEloFromRank("Platinum 1"));

        // Simulate elo progression over match history
       sonicFoxMatches.forEach(PlayerService::simulateEloUpdate);
       leffenMatches.forEach(PlayerService::simulateEloUpdate);

        // Count WL and build responses
        int sonicFoxWins = (int) sonicFoxMatches.stream().filter(m -> m.getWinner() == Winner.P1).count();
        int sonicFoxLosses = sonicFoxMatches.size() - sonicFoxWins;
        MOCK_PLAYERS.put("SonicFox",
            new PlayerResponse(
                "SonicFox",
                sonicFoxWins,
                sonicFoxLosses,
                sonicFoxMatches,
                MOCK_PLAYER_ELOS.get("SonicFox")
            )
        );

        int leffenWins = (int) leffenMatches.stream().filter(m -> m.getWinner() == Winner.P1).count();
        int leffenLosses = leffenMatches.size() - leffenWins;
        MOCK_PLAYERS.put("Leffen",
            new PlayerResponse(
                "Leffen",
                leffenWins,
                leffenLosses,
                leffenMatches,
                MOCK_PLAYER_ELOS.get("Leffen")
            )
        );
    }

    // ---API METHOD---
    // matchCount = how many last X matches to fetch (currently unused)
    public PlayerResponse getPlayerByRiotId(String riotId, int matchCount) {
        return MOCK_PLAYERS.getOrDefault(riotId, MOCK_PLAYERS.get("SonicFox"));
    }

    // --- HELPER METHODS ---
    private static double getEloFromRank(String rank) {
        return RANK_TO_ELO_BASE.getOrDefault(rank, 1500.0);
    }

    private static void simulateEloUpdate(Match match) {
        String p1 = match.getP1RiotId();
        String p2 = match.getP2RiotId();

        // if a new player is detected
        MOCK_PLAYER_ELOS.putIfAbsent(p1, getEloFromRank(match.getP1Rank()));
        MOCK_PLAYER_ELOS.putIfAbsent(p2, getEloFromRank(match.getP2Rank()));

        double p1EloBefore = MOCK_PLAYER_ELOS.get(p1);
        double p2EloBefore = MOCK_PLAYER_ELOS.get(p2);

        boolean p1Win = (match.getWinner() == Winner.P1);

        double newP1Elo = EloCalculator.updateRating(p1EloBefore, p2EloBefore, p1Win);
        double newP2Elo = EloCalculator.updateRating(p2EloBefore, p1EloBefore, !p1Win);

        MOCK_PLAYER_ELOS.put(p1, newP1Elo);
        MOCK_PLAYER_ELOS.put(p2, newP2Elo); 
        match.setEloSnapshots(p1EloBefore, newP1Elo, p2EloBefore, newP2Elo);
    }
}
