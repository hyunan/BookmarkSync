document.getElementById("loginForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const username = document.getElementById("uname").value;
    const password = document.getElementById("psw").value;
    const clickedButton = document.activeElement.id;
    if (clickedButton === "create") {
        console.log("CREATED USER");
        const response = await fetch("http://localhost:8080/users/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ username, password })
        });
        if (response.ok) {
            const data = await response.json();
            console.log("Account created with: " + username + " and " + password);
        } else {
            const data = await response.json();
            console.log("Create Error: ", data);
        }
    } else if (clickedButton === "login") {
        console.log("LOGIN USER");
        const response = await fetch("http://localhost:8080/users/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ username, password })
        });
        if (response.ok) {
            const data = await response.json();
            console.log("Login successful: ", data);
        } else {
            const data = await response.json();
            console.error("Login failed: ", data);
        }
    }
});