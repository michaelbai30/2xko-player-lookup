// script.js

// base url
const API_BASE = "http://localhost:8080/api/players";

// get elements
const lookupBtn = document.getElementById("lookupBtn");
const riotIdInput = document.getElementById("riotIdInput");
const playerInfoDiv = document.getElementById("playerInfo");

// when you click the button
lookupBtn.addEventListener("click", () =>{
    const riotId = riotIdInput.value.trim();
    if (!riotId){
        showError("Please enter a valid Riot ID.");
        return;
    }

    fetchPlayer(riotId);
});

async function fetchPlayer(riotId){
    try{
        const res = await fetch(`${API_BASE}/${riotId}?last-=50`);
        if (!res.ok){
            throw new Error("Player not found");
        }
        const data = await res.json();
        renderPlayer(data);
    }

    catch(error){
        showError(error.message);
    }
}

function renderPlayer(player){
    let html = `
    <h2>${player.riotId}</h2>
    <p><strong>Wins:</strong> ${player.wins} |
    <strong>Losses</strong> ${player.losses} |
    <strong>Win Rate</strong> ${(player.winRate * 100).toFixed(2)}%</p>
    <h3> Match History </h3>
    <ul>
        ${player.matches.map(match => 
            `
            <li>
                <strong>${match.p1RiotId} (${match.p1Rank})</strong> vs <strong>${match.p2RiotId} (${match.p2Rank})</strong><br>
                Teams: ${match.p1Team.join(", ")} (${match.p1Fuse}) vs. ${match.p2Team.join(", ")} (${match.p2Fuse})<br>
                Rounds: ${match.rounds}<br>
                Winner: ${match.winner}
            </li>
            `).join("")}
    </ul>
    `;

    playerInfoDiv.innerHTML = html;
}

function showError(msg) {
  playerInfoDiv.innerHTML = `<p style="color:salmonred;">${msg}</p>`;
}