const baseUrl = "http://localhost:8080";

let currentUser = null;

function register() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    fetch(`${baseUrl}/auth/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
    }).then(() => alert("Registered successfully!"));
}

function login() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;
    fetch(`${baseUrl}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
    })
        .then((response) => response.text())
        .then((userId) => {
            currentUser = userId;
            document.getElementById("auth").style.display = "none";
            document.getElementById("dashboard").style.display = "block";
            loadExpenses();
        });
}

function addExpense() {
    const category = document.getElementById("category").value;
    const amount = document.getElementById("amount").value;
    const date = document.getElementById("date").value;

    fetch(`${baseUrl}/expenses`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ category, amount, date, userId: currentUser }),
    }).then(() => loadExpenses());
}

function loadExpenses() {
    fetch(`${baseUrl}/expenses/${currentUser}`)
        .then((response) => response.json())
        .then((data) => {
            const expenseList = document.getElementById("expenses");
            expenseList.innerHTML = "";
            data.forEach((expense) => {
                const li = document.createElement("li");
                li.textContent = `${expense.category} - $${expense.amount} (${expense.date})`;
                expenseList.appendChild(li);
            });
        });
}

function logout() {
    currentUser = null;
    document.getElementById("auth").style.display = "block";
    document.getElementById("dashboard").style.display = "none";
}

