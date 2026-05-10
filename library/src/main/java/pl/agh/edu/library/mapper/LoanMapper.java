package pl.agh.edu.library.mapper;

import pl.agh.edu.library.dto.LoanDto;
import pl.agh.edu.library.model.Loan;

public class LoanMapper {

    public static LoanDto toDto(Loan loan) {
        if (loan == null) {
            return null;
        }
        return LoanDto.builder()
                .id(loan.getId())
                .state(loan.getState())
                .reservationDate(loan.getReservationDate())
                .loanDate(loan.getLoanDate())
                .returnDate(loan.getReturnDate())
                .userId(loan.getUser() != null ? loan.getUser().getId() : null)
                .bookId(loan.getBook() != null ? loan.getBook().getId() : null)
                .build();
    }
}