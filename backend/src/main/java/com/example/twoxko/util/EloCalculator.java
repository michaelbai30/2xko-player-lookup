package com.example.twoxko.util;

// Calculate and update elo rating based on the outcome of matches
// PLayers with higher ELO have a higher "probability" of winning against those with a lower ELO
// Elo is updated after each match
// If a player with a higher elo wins, fewer points are gained, vs. if a player with a lower elo wins

// Formula (for P1):
// probability of P1 winning: expected = (1.0 / (1.0 + pow(10, ((rating1 - rating2) / 400)))); 
// New rating for P1: rating1 = rating1 + K*(actual - expected);
// where actual is 1 or 0 based on match result.
public class EloCalculator{

    // rating change factor
    private static final int K = 32;

    public static double expectedScore(double playerRating, double opponentRating){
        return 1.0 / (1.0 + Math.pow(10.0, (opponentRating - playerRating) / 400.0));
    }
    public static double updateRating(double oldRating, double opponentRating, boolean didWin){
        double expected = expectedScore(oldRating, opponentRating);
        double actual = didWin ? 1.0: 0.0;
        double newRating = oldRating + K * (actual - expected);
        return newRating;
    }
}