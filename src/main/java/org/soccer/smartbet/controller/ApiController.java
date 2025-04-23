package org.soccer.smartbet.controller;

import org.soccer.smartbet.domain.Fixture;
import org.soccer.smartbet.domain.League;
import org.soccer.smartbet.dto.ApiResponse;
import org.soccer.smartbet.dto.FixtureDTO;
import org.soccer.smartbet.service.FootballDataService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/football")
public class ApiController {

    private final FootballDataService footballDataService;

    public ApiController(FootballDataService footballDataService) {
        this.footballDataService = footballDataService;
    }

    @GetMapping("/leagues")
    public ResponseEntity<ApiResponse<List<League>>> getTopLeagues(
            @RequestParam(defaultValue = "30") int count) {
        List<League> leagues = footballDataService.getTopLeagues(count);
        return ResponseEntity.ok(ApiResponse.success(leagues));
    }

    @GetMapping("/fixtures")
    public ResponseEntity<ApiResponse<Page<FixtureDTO>>> getFixtures(
            @RequestParam(required = false) Integer leagueId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            Pageable pageable) {
        Page<FixtureDTO> fixtures = footballDataService.getFixtures(leagueId, date, pageable);
        return ResponseEntity.ok(ApiResponse.success(fixtures));
    }

    @GetMapping("/fixtures/{id}")
    public ResponseEntity<ApiResponse<FixtureDTO>> getFixtureDetails(@PathVariable Integer id) {
        FixtureDTO fixture = footballDataService.getFixtureDetails(id);
        return ResponseEntity.ok(ApiResponse.success(fixture));
    }

    @GetMapping("/predictions/{fixtureId}")
    public ResponseEntity<ApiResponse<FixturePrediction>> getFixturePredictions(
            @PathVariable Integer fixtureId) {
        FixturePrediction prediction = footballDataService.getFixturePredictions(fixtureId);
        return ResponseEntity.ok(ApiResponse.success(prediction));
    }
}pa