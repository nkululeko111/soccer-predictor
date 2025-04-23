package org.soccer.smartbet.controller;

import org.soccer.smartbet.domain.BettingTicket;
import org.soccer.smartbet.dto.ApiResponse;
import org.soccer.smartbet.dto.TicketDTO;
import org.soccer.smartbet.service.BettingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequestMapping("/betting")
public class BettingController {

    private final BettingService bettingService;

    public BettingController(BettingService bettingService) {
        this.bettingService = bettingService;
    }

    @GetMapping("/tickets")
    public String getTicketsPage(Model model, Principal principal, Pageable pageable) {
        Page<TicketDTO> tickets = bettingService.getUserTickets(principal.getName(), pageable);
        model.addAttribute("tickets", tickets);
        return "tickets";
    }

    @PostMapping("/tickets/generate")
    @ResponseBody
    public ResponseEntity<ApiResponse<TicketDTO>> generateTicket(
            @RequestParam String type,
            Principal principal) {
        TicketDTO ticket = bettingService.generateTicket(principal.getName(), type);
        return ResponseEntity.ok(ApiResponse.success(ticket));
    }

    @PutMapping("/tickets/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponse<TicketDTO>> updateTicket(
            @PathVariable Long id,
            @RequestBody TicketDTO ticketDTO,
            Principal principal) {
        TicketDTO updatedTicket = bettingService.updateTicket(id, ticketDTO, principal.getName());
        return ResponseEntity.ok(ApiResponse.success(updatedTicket));
    }

    @DeleteMapping("/tickets/{id}")
    @ResponseBody
    public ResponseEntity<ApiResponse<Void>> deleteTicket(
            @PathVariable Long id,
            Principal principal) {
        bettingService.deleteTicket(id, principal.getName());
        return ResponseEntity.ok(ApiResponse.success(null));
    }
}