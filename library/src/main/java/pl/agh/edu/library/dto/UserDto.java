package pl.agh.edu.library.dto;

public class UserDto {
    private Integer id;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String role;

    public UserDto() {}

    public UserDto(Integer id, String userName, String email, String firstName, String lastName, String role) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
    }

    public static class Builder {
        private Integer id;
        private String userName;
        private String email;
        private String firstName;
        private String lastName;
        private String role;

        public Builder id(Integer id) {
            this.id = id;
            return this;
        }

        public Builder userName(String userName) {
            this.userName = userName;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder firstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public Builder role(String role) {
            this.role = role;
            return this;
        }

        public UserDto build() {
            return new UserDto(id, userName, email, firstName, lastName, role);
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}