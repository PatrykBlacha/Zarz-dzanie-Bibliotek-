package pl.agh.edu.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.agh.edu.library.dto.LoanDto;
import pl.agh.edu.library.service.LoanService;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping("/reserve")
    public ResponseEntity<LoanDto> reserveBook(@RequestParam Long userId, @RequestParam Long bookId) {
        try {
            return ResponseEntity.ok(loanService.reserveBook(userId, bookId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/loan")
    public ResponseEntity<LoanDto> loanBook(@RequestParam Long userId, @RequestParam Long bookId) {
        try {
            return ResponseEntity.ok(loanService.loanBook(userId, bookId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/return/{loanId}")
    public ResponseEntity<LoanDto> returnBook(@PathVariable Long loanId) {
        try {
            return ResponseEntity.ok(loanService.returnBook(loanId));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public List<LoanDto> getAllLoans() {
        return loanService.getAllLoans();
    }
}