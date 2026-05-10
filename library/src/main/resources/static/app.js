const API = "http://localhost:8081/api";


//auth
document.getElementById("registerForm").addEventListener("submit", async (e) => {
    e.preventDefault();

    const payload = {
        firstName: document.getElementById("regName").value, 
        lastName: "Nowy",
        email: document.getElementById("regEmail").value,
        password: document.getElementById("regPassword").value,
        role: document.getElementById("regRole").value,
        userName: document.getElementById("regName").value
    };
    
    const response = await fetch(`${API}/users`, { 
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
    });

    if (response.ok) {
        alert("User registered! Now try to login.");
    } else {
        alert("Error registering user");
    }
});

document.getElementById("loginForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    
    const payload = {
        userName: document.getElementById("loginEmail").value,
        password: document.getElementById("loginPassword").value
    };

    const response = await fetch(`http://localhost:8081/account/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
    });

    const data = await response.text(); 
    
    if (response.ok && data !== "wrong username or password") {
        localStorage.setItem("user", payload.userName);
        localStorage.setItem("jwt_token", data); 
        alert("Login successful!");
        showDashboard();
    } else {
        alert("Wrong username or password"); 
    }
});

function showDashboard() {
    const user = localStorage.getItem("user");
    if (!user) {
        document.getElementById("authSection").style.display = "block";
        document.getElementById("dashboard").style.display = "none";
        return;
    }
    document.getElementById("authSection").style.display = "none";
    document.getElementById("welcome").innerText = "Logged in as: " + user;
    document.getElementById("dashboard").style.display = "block";
}

document.getElementById("logoutBtn").addEventListener("click", () => {
    localStorage.clear();
    showDashboard();
});

//token
function getHeaders() {
    const token = localStorage.getItem("jwt_token");
    return {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`
    };
}

//books

document.getElementById("btnAddBook").addEventListener("click", async () => {
    const name = document.getElementById("bookName").value;
    const author = document.getElementById("bookAuthor").value;
    const quantity = document.getElementById("bookQuantity").value;

    if(!name || !author || !quantity) { alert("Podaj Tytuł, Autora i Ilość"); return; }

    const payload = { name, author, quantity: parseInt(quantity) };

    const response = await fetch(`${API}/books`, {
        method: "POST",
        headers: getHeaders(),
        body: JSON.stringify(payload)
    });

    if(response.ok) {
        alert("Książka dodana pomyślnie!");
    } else {
        alert("Błąd dodawania książki (Brak uprawnień ADMIN lub błąd serwera)");
    }
});


//loans, reservations

document.getElementById("btnReserve").addEventListener("click", async () => {
    const userId = document.getElementById("loanUserId").value;
    const bookId = document.getElementById("loanBookId").value;

    if(!userId || !bookId) { alert("Podaj User ID i Book ID"); return; }

    const response = await fetch(`${API}/loans/reserve?userId=${userId}&bookId=${bookId}`, {
        method: "POST",
        headers: getHeaders()
    });

    if(response.ok) {
        alert("Zarezerwowano pomyślnie!");
        loadLoans();
    } else {
        alert("Błąd rezerwacji (sprawdź czy ID istnieją)");
    }
});

document.getElementById("btnLoan").addEventListener("click", async () => {
    const userId = document.getElementById("loanUserId").value;
    const bookId = document.getElementById("loanBookId").value;

    if(!userId || !bookId) { alert("Podaj User ID i Book ID"); return; }

    const response = await fetch(`${API}/loans/loan?userId=${userId}&bookId=${bookId}`, {
        method: "POST",
        headers: getHeaders()
    });

    if(response.ok) {
        alert("Wypożyczono pomyślnie!");
        loadLoans();
    } else {
        alert("Błąd wypożyczenia (sprawdź czy ID istnieją)");
    }
});

document.getElementById("btnReturn").addEventListener("click", async () => {
    const loanId = document.getElementById("returnLoanId").value;

    if(!loanId) { alert("Podaj Loan ID"); return; }

    const response = await fetch(`${API}/loans/return/${loanId}`, {
        method: "POST",
        headers: getHeaders()
    });

    if(response.ok) {
        alert("Zwrócono pomyślnie!");
        loadLoans();
    } else {
        alert("Błąd zwrotu");
    }
});

document.getElementById("btnListLoans").addEventListener("click", loadLoans);

async function loadLoans() {
    const response = await fetch(`${API}/loans`, {
        headers: getHeaders()
    });
    if(response.ok) {
        const data = await response.json();
        document.getElementById("loansOutput").innerText = JSON.stringify(data, null, 2);
    } else {
        document.getElementById("loansOutput").innerText = "Błąd pobierania listy. Kod 403 - Brak Uprawnień.";
    }
}
