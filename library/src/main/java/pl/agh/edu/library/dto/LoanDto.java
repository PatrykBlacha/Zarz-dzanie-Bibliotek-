package pl.agh.edu.library.dto;

import java.sql.Date;

public class LoanDto {
    private Integer id;
    private String state;
    private Date reservationDate;
    private Date loanDate;
    private Date returnDate;
    private Integer userId;
    private Integer bookId;

    public LoanDto() {}

    public LoanDto(Integer id, String state, Date reservationDate, Date loanDate, Date returnDate, Integer userId, Integer bookId) {
        this.id = id;
        this.state = state;
        this.reservationDate = reservationDate;
        this.loanDate = loanDate;
        this.returnDate = returnDate;
        this.userId = userId;
        this.bookId = bookId;
    }

    public static class Builder {
        private Integer id;
        private String state;
        private Date reservationDate;
        private Date loanDate;
        private Date returnDate;
        private Integer userId;
        private Integer bookId;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder state(String state) {
            this.state = state;
            return this;
        }

        public Builder reservationDate(Date reservationDate) {
            this.reservationDate = reservationDate;
            return this;
        }

        public Builder loanDate(Date loanDate) {
            this.loanDate = loanDate;
            return this;
        }

        public Builder returnDate(Date returnDate) {
            this.returnDate = returnDate;
            return this;
        }

        public Builder userId(Integer userId) {
            this.userId = userId;
            return this;
        }

        public Builder bookId(Integer bookId) {
            this.bookId = bookId;
            return this;
        }

        public LoanDto build() {
            return new LoanDto(id, state, reservationDate, loanDate, returnDate, userId, bookId);
        }
    }

    public static Builder builder() {
        return new Builder();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Date getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(Date loanDate) {
        this.loanDate = loanDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }
}