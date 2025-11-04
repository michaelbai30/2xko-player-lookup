// PlayerResponse.java models the structure of a player data

package com.example.twoxko.model;

import java.util.List;

public class PlayerResponse{
    private String riotId;
    private int wins;
    private int losses;
    private double winRate;
    private double currentElo;
    private List<Match> matches; // last X matches

    public PlayerResponse(){};

    public PlayerResponse(String riotId, int wins, int losses, List<Match> matches, double currentElo){
        this.riotId = riotId;
        this.wins = wins;
        this.losses = losses;
        this.matches = matches;
        this.currentElo = currentElo;
        int total = wins + losses;
        this.winRate = total == 0 ? 0.0 : (double) wins / total;
    }

    public String getRiotId(){return this.riotId;}
    public void setRiotId(String riotId) {this.riotId = riotId;}

    public int getWins(){return this.wins;}
    public void setWins(int wins) {this.wins = wins;}

    public int getLosses(){return this.losses;}
    public void setLosses(int losses) {this.losses = losses;}

    public double getWinRate(){return this.winRate;}
    public void setWinRate(double winRate) {this.winRate = winRate;}

    public double getCurrentElo(){return this.currentElo;}
    public void setCurrentElo(double currentElo){this.currentElo = currentElo;}

    public List<Match> getMatches(){return this.matches;}
    public void setMatches(List<Match> matches) {this.matches = matches;}
}
