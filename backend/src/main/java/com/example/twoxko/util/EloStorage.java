package com.example.twoxko.util;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.example.twoxko.model.Match;
import com.example.twoxko.model.Winner;


public class EloStorage {
    // Rank -> Base ELO
    private static final Map<String, Double> RANK_TO_ELO_BASE = new HashMap<>();

    // Player / Riot ID -> Current ELO
    private static final Map<String, Double> PLAYER_ELOS = new HashMap<>();

    private EloStorage(){}; // no instances

    static{
        // base elo is +166 per rank (500 per division)
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
    }

    public static double getBaseElo(String rank) {
        return RANK_TO_ELO_BASE.getOrDefault(rank, 1500.0);
    }
    
    public static void seedEloFromRank(String player, String rank){
        PLAYER_ELOS.put(player, getBaseElo(rank));
    }

    public static double getPlayerElo(String player){
        return PLAYER_ELOS.getOrDefault(player, 1500.0);
    }

    public static Map<String, Double> getAllPlayerElos(){
        return Collections.unmodifiableMap(PLAYER_ELOS);
    }

    public static void simulateEloUpdate(Match match) {
        String p1 = match.getP1RiotId();
        String p2 = match.getP2RiotId();

        // if a new player is detected
        PLAYER_ELOS.putIfAbsent(p1, getBaseElo(match.getP1Rank()));
        PLAYER_ELOS.putIfAbsent(p2, getBaseElo(match.getP2Rank()));

        double p1EloBefore = PLAYER_ELOS.get(p1);
        double p2EloBefore = PLAYER_ELOS.get(p2);

        boolean p1Win = (match.getWinner() == Winner.P1);

        double newP1Elo = EloCalculator.updateRating(p1EloBefore, p2EloBefore, p1Win);
        double newP2Elo = EloCalculator.updateRating(p2EloBefore, p1EloBefore, !p1Win);

        PLAYER_ELOS.put(p1, newP1Elo);
        PLAYER_ELOS.put(p2, newP2Elo);
        
        // save for UI rendering
        match.setEloSnapshots(p1EloBefore, newP1Elo, p2EloBefore, newP2Elo);
    }
}
