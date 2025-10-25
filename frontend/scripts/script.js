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

  // "Diamond 3" -> "diamond3"
  const p1RankFormatted = (match.p1Rank || "").split(" ").map(word => word.charAt(0).toLowerCase() + word.slice(1).toLowerCase()).join("");
  const p2RankFormatted = (match.p2Rank || "").split(" ").map(word => word.charAt(0).toLowerCase() + word.slice(1).toLowerCase()).join("");
  return `
    <div class="match-card">
      <p>
        <strong>${match.p1RiotId}</strong>
        <img src="assets/ranks/${p1RankFormatted}.webp" alt="${p1RankFormatted}" title="${p1RankFormatted}">
        vs
        <strong>${match.p2RiotId}</strong>
        <img src="assets/ranks/${p2RankFormatted}.webp" alt="${p2RankFormatted}" title="${p2RankFormatted}">
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

  const membersHTML = (teamMembers || []).map(member => {

    // Wiki Example: https://wiki.play2xko.com/en-us/Ahri
    const wikiURL = `https://wiki.play2xko.com/en-us/${member.charAt(0).toUpperCase() + member.slice(1).toLowerCase()}`
    return `
      <div class="character">
      <a href="${wikiURL}" target="_blank" title="Open ${member} wiki page.">
      <img src="assets/characters/${member.toLowerCase()}.png" alt="${member}" title="${member}">
      </a>
      <span>${member}</span>
      </div>
    `;}).join("");

  // Wiki: "Double Down" -> "Double_Down"
  const fuseFormatted = fuseName.split(" ").map(word => word.charAt(0).toUpperCase() + word.slice(1).toLowerCase()).join("_");
  const fuseWikiUrl = `https://wiki.play2xko.com/en-us/${fuseFormatted}`;

  // Asset naming convention: "2X Assist" becomes "2x-assist.webp"
  const fuseHTML = `
    <div class="fuse">
    <a href="${fuseWikiUrl}" target="_blank" title="Open ${fuseName} wiki page.">
      <img src="assets/fuses/${fuseName.toLowerCase().replace(/\s+/g, "-")}.webp" alt="${fuseName}" title="${fuseName}">
    </a>
      <span>${fuseName}</span>
    </div>
  `;

  return `<div class="team-block">${membersHTML}${fuseHTML}</div>`;
}

function showError(msg) {
  playerInfoDiv.innerHTML = `<p style="color: salmon;">${msg}</p>`;
}
