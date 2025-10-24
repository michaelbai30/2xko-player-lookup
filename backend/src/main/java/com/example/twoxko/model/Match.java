// Match.java models how a match is represented

package com.example.twoxko.model;

import java.time.Instant;
import java.util.List;

public class Match{
    private String matchId;
    private Instant startedAt;
    private String p1RiotId;
    private String p2RiotId;
    private String p1Rank;
    private String p2Rank;
    private List<String> p1Team;
    private List<String> p2Team;
    private String p1Fuse;
    private String p2Fuse;
    private int rounds; // numRounds
    private Winner winner;

    public Match(){}

    public Match(String matchId, Instant startedAt, String p1RiotId, String p2RiotId, String p1Rank, String p2Rank, List<String> p1Team, List<String> p2Team, String p1Fuse, String p2Fuse, int rounds, Winner winner){
        this.matchId = matchId;
        this.startedAt = startedAt;
        this.p1RiotId = p1RiotId;
        this.p2RiotId = p2RiotId;
        this.p1Rank = p1Rank;
        this.p2Rank = p2Rank;
        this.p1Team = p1Team;
        this.p2Team = p2Team;
        this.p1Fuse = p1Fuse;
        this.p2Fuse = p2Fuse;
        this.rounds = rounds;
        this.winner = winner;
    }

    public String getMatchId(){return this.matchId;}
    public Instant getStartedAt(){return this.startedAt;}
    public String getP1RiotId(){return this.p1RiotId;}
    public String getP2RiotId(){return this.p2RiotId;}
    public String getP1Rank(){return this.p1Rank;}
    public String getP2Rank(){return this.p2Rank;}
    public List<String> getP1Team(){return this.p1Team;}
    public List<String> getP2Team(){return this.p2Team;}
    public String getP1Fuse(){return this.p1Fuse;}
    public String getP2Fuse(){return this.p2Fuse;}
    public int getRounds(){return this.rounds;}
    public Winner getWinner(){return this.winner;}

    public void setMatchId(String matchId){this.matchId = matchId;}
    public void setStartedAt(Instant startedAt){this.startedAt = startedAt;}
    public void setP1RiotId(String p1RiotId){this.p1RiotId = p1RiotId;}
    public void setP2RiotId(String p2RiotId){this.p2RiotId = p2RiotId;}
    public void setP1Rank(String p1Rank){this.p1Rank = p1Rank;}
    public void setP2Rank(String p2Rank){this.p2Rank = p2Rank;}
    public void setP1Team(List<String> p1Team){this.p1Team = p1Team;}
    public void setP2Team(List<String> p2Team){this.p2Team = p2Team;}
    public void setP1Fuse(String p1Fuse){this.p1Fuse = p1Fuse;}
    public void setP2Fuse(String p2Fuse){this.p2Fuse = p2Fuse;}
    public void setRounds(int rounds){this.rounds = rounds;}
    public void setWinner(Winner winner){this.winner = winner;}
}

