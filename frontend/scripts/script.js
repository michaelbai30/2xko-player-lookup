// script.js

// base url
const API_BASE = "http://localhost:8080/api/players";

// get elements
const lookupBtn = document.getElementById("lookupBtn");
const riotIdInput = document.getElementById("riotIdInput");
const playerInfoDiv = document.getElementById("playerInfo");

// when you click the button
lookupBtn.addEventListener("click", () => {
  const riotId = riotIdInput.value.trim();
  if (!riotId) {
    showError("Please enter a valid Riot ID.");
    return;
  }

  fetchPlayer(riotId);
});

async function fetchPlayer(riotId) {
  try {
    const res = await fetch(`${API_BASE}/${riotId}?last=50`);
    if (!res.ok) {
      throw new Error("Player not found");
    }
    const data = await res.json();
    renderPlayer(data);
  } catch (error) {
    showError(error.message);
  }
}

function renderPlayer(player) {
  const matches = player.matches || [];
  let html = `
    <h2>${player.riotId}</h2>
    <p><strong>Wins:</strong> ${player.wins || 0} |
    <strong>Losses:</strong> ${player.losses || 0} |
    <strong>Win Rate:</strong> ${((player.winRate || 0) * 100).toFixed(2)}%</p>
    <h3>Match History</h3>
  `;

  if (matches.length === 0) {
    html += `<p>No matches found.</p>`;
  } else {
    html += matches.map(match => renderMatchCard(match, player.riotId)).join("");
  }
  playerInfoDiv.innerHTML = html;
}

// match card contains info about player names, ranks, teams, fuses, winner, and num rounds
function renderMatchCard(match, curPlayer) {
  const p1TeamHTML = renderTeam(match.p1Team, match.p1Fuse);
  const p2TeamHTML = renderTeam(match.p2Team, match.p2Fuse);

  // visually show winner
  const p1Highlight = match.winner === "P1" ? "winner-glow" : "";
  const p2Highlight = match.winner === "P2" ? "winner-glow" : "";

  return `
    <div class="match-card">
      <p>
        <strong>${match.p1RiotId} (${match.p1Rank || "Unranked"})</strong>
        vs
        <strong>${match.p2RiotId} (${match.p2Rank || "Unranked"})</strong>
      </p>
      <div class="teams">
        <div class="team ${p1Highlight}">
          ${p1TeamHTML}
        </div>
        <span class="vs-text">vs.</span>
        <div class="team ${p2Highlight}">
          ${p2TeamHTML}
        </div>
      </div>
      <p>Rounds: ${match.rounds || "?"}</p>
      <p><strong>Winner:</strong> ${match.winner || "N/A"}</p>
    </div>
  `;
}

function renderTeam(teamMembers, fuseName) {
  const membersHTML = (teamMembers || []).map(member => `
    <div class="character">
      <img src="assets/characters/${member.toLowerCase()}.png" alt="${member}" title="${member}">
      <span>${member}</span>
    </div>
  `).join("");
  // "2X Assist" becomes "2x-assist.webp"
  const fuseHTML = `
    <div class="fuse">
      <img src="assets/fuses/${fuseName.toLowerCase().replace(/\s+/g, "-")}.webp" alt="${fuseName}" title="${fuseName}">
      <span>${fuseName}</span>
    </div>
  `;

  return `<div class="team-block">${membersHTML}${fuseHTML}</div>`;
}

function showError(msg) {
  playerInfoDiv.innerHTML = `<p style="color: salmon;">${msg}</p>`;
}
