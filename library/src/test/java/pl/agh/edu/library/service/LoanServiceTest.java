package pl.agh.edu.library.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.agh.edu.library.dto.LoanDto;
import pl.agh.edu.library.model.Book;
import pl.agh.edu.library.model.Loan;
import pl.agh.edu.library.model.User;
import pl.agh.edu.library.repository.BookRepository;
import pl.agh.edu.library.repository.LoanRepository;
import pl.agh.edu.library.repository.UserRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private LoanService loanService;

    private User testUser;
    private Book testBook;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setFirstName("John");
        testUser.setLastName("Doe");

        testBook = new Book();
        testBook.setName("Test Book");
    }

    @Test
    void reserveBook_Successful_ReturnsLoanDto() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
        
        Loan savedLoan = new Loan();
        savedLoan.setUser(testUser);
        savedLoan.setBook(testBook);
        savedLoan.setState("RESERVED");
        
        when(loanRepository.save(any(Loan.class))).thenReturn(savedLoan);

        LoanDto result = loanService.reserveBook(1L, 1L);

        assertNotNull(result);
        assertEquals("RESERVED", result.getState());
        
        ArgumentCaptor<Loan> loanCaptor = ArgumentCaptor.forClass(Loan.class);
        verify(loanRepository).save(loanCaptor.capture());
        assertEquals("RESERVED", loanCaptor.getValue().getState());
    }

    @Test
    void loanBook_Successful_ReturnsLoanDto() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));

        Loan savedLoan = new Loan();
        savedLoan.setUser(testUser);
        savedLoan.setBook(testBook);
        savedLoan.setState("LOANED");

        when(loanRepository.save(any(Loan.class))).thenReturn(savedLoan);

        LoanDto result = loanService.loanBook(1L, 1L);

        assertNotNull(result);
        assertEquals("LOANED", result.getState());
        
        ArgumentCaptor<Loan> loanCaptor = ArgumentCaptor.forClass(Loan.class);
        verify(loanRepository).save(loanCaptor.capture());
        assertEquals("LOANED", loanCaptor.getValue().getState());
    }

    @Test
    void loanBook_LimitExceeded_ThrowsException() {
        for (int i = 0; i < 10; i++) {
            Loan loan = new Loan();
            loan.setState("LOANED");
            testUser.getLoans().add(loan);
        }

        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));

        Exception exception = assertThrows(RuntimeException.class, () -> {
            loanService.loanBook(1L, 1L);
        });

        assertEquals("Osiągnięto limit 10 wypożyczonych książek!", exception.getMessage());
        verify(loanRepository, never()).save(any(Loan.class));
    }

    @Test
    void returnBook_Successful_ReturnsLoanDto() {
        Loan loan = new Loan();
        loan.setId(1);
        loan.setState("LOANED");
        loan.setLoanDate(Date.valueOf(LocalDate.now().minusDays(5)));

        when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
        when(loanRepository.save(any(Loan.class))).thenReturn(loan);

        LoanDto result = loanService.returnBook(1L);

        assertNotNull(result);
        assertEquals("RETURNED", result.getState());
        
        ArgumentCaptor<Loan> loanCaptor = ArgumentCaptor.forClass(Loan.class);
        verify(loanRepository).save(loanCaptor.capture());
        assertEquals("RETURNED", loanCaptor.getValue().getState());
        assertNotNull(loanCaptor.getValue().getReturnDate());
    }
}