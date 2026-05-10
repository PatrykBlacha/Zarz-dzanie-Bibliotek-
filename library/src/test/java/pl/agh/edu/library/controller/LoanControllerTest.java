package pl.agh.edu.library.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import pl.agh.edu.library.dto.LoanDto;
import pl.agh.edu.library.security.JwtFilter;
import pl.agh.edu.library.security.JwtUtil;
import pl.agh.edu.library.service.LoanService;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LoanController.class)
@AutoConfigureMockMvc(addFilters = false)
class LoanControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LoanService loanService;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private JwtFilter jwtFilter;

    private LoanDto testLoanDto;

    @BeforeEach
    void setUp() {
        testLoanDto = LoanDto.builder()
                .id(1)
                .state("RESERVED")
                .userId(1)
                .bookId(1)
                .build();
    }

    @Test
    void reserveBook_ReturnsOkWithLoanDto() throws Exception {
        when(loanService.reserveBook(1L, 1L)).thenReturn(testLoanDto);

        mockMvc.perform(post("/api/loans/reserve")
                .param("userId", "1")
                .param("bookId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("RESERVED"))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.bookId").value(1));
    }

    @Test
    void loanBook_ReturnsOkWithLoanDto() throws Exception {
        testLoanDto.setState("LOANED");
        when(loanService.loanBook(1L, 1L)).thenReturn(testLoanDto);

        mockMvc.perform(post("/api/loans/loan")
                .param("userId", "1")
                .param("bookId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("LOANED"));
    }

    @Test
    void returnBook_ReturnsOkWithLoanDto() throws Exception {
        testLoanDto.setState("RETURNED");
        when(loanService.returnBook(1L)).thenReturn(testLoanDto);

        mockMvc.perform(post("/api/loans/return/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.state").value("RETURNED"));
    }

    @Test
    void loanBook_WhenLimitExceeded_ReturnsBadRequest() throws Exception {
        when(loanService.loanBook(1L, 1L)).thenThrow(new RuntimeException("Limit exceeded"));

        mockMvc.perform(post("/api/loans/loan")
                .param("userId", "1")
                .param("bookId", "1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAllLoans_ReturnsListOfLoans() throws Exception {
        when(loanService.getAllLoans()).thenReturn(Arrays.asList(testLoanDto));

        mockMvc.perform(get("/api/loans")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].state").value("RESERVED"));
    }
}