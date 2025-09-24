document.getElementById("loginForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const username = document.getElementById("uname").value;
    const password = document.getElementById("psw").value;

    const response = await fetch("/users/login", {
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
});