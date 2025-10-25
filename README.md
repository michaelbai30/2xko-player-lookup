# 2XKO Player Lookup Service

A **player lookup service** for Riot Games’ upcoming fighting game based on the League of Legends universe **2XKO**.  
Since the official Riot API isn’t publicly available yet, this project currently uses **mock data** to simulate player stats, matches, and win/loss ratios.
Once the API is released, this backend will connect directly to Riot’s official endpoints to provide real player data.

If things go well, I intend to deploy this as a website.

---

## Project Overview

The goal of this service is to:
- Look up a player by Riot ID
- Display their Win/Loss count and ratio. I'm particularly interested in this since the current early release version of the games doesn't show your WL ratios
- Display their last X matches (ideally for the whole season once the service is scaled)
- Show each match's details including player names, ranks, teams used, fuses used, winner, and number of rounds played

In the near future:
- Compute WL Ratio of individual **Teams** and **Characters** by **Rank** to show meta across different levels of play.
- Compute ELO ratings for each player through a custom elo ranking algorithm (Glicko2 rating system).
- Tier List

--- 

## Stack

**Backend**: Java, Spring Boot<br>
**Frontend**: JS, HTML, CSS<br>
**Build Tools**: Maven

---

## Setup Instructions
Ensure you have the following installed:
- **Java 21+**
- **Maven 3.8+**

1) Clone the repo
2) From the project root run:
   ```bash
   cd backend
   mvn clean install
   mvn spring-boot:run
   ```

3) Open another terminal tab. From the project root run:
   ```bash
   cd frontend
   open index.html
   ```
