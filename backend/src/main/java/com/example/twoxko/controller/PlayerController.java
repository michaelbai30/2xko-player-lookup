// PlayerController.java Handles HTTP routes

package com.example.twoxko.controller;

import com.example.twoxko.model.PlayerResponse;
import com.example.twoxko.service.PlayerService;
import org.springframework.web.bind.annotation.*;

@RestController // Handle http requests and returns data
@RequestMapping("/api/players")  // routes start with /api/players
@CrossOrigin(origins = "*") // CORs allows frontend to run on localhost

public class PlayerController{
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService){
        this.playerService = playerService;
    }

    // handles the API endpoint at /api/players/{riotId}
    @GetMapping("/{riotId}")
    public PlayerResponse getPlayer(@PathVariable String riotId, @RequestParam(defaultValue = "50") int last) {
        // send back player info
        // automatically serialized and converted to json
        return playerService.getPlayerByRiotId(riotId, last);
    }
}