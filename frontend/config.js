const SERVER_URL = "http://localhost:8080";

function renderBookmarks(bookmarks) {
    const container = document.getElementById("bookmark-view");
    container.innerHTML = "";

    bookmarks.forEach(e => {
        const div = document.createElement("div");
        
        const innerDiv = document.createElement("div");
        innerDiv.className = "p-2";

        const p = document.createElement("p");
        p.textContent = e.title;
        innerDiv.appendChild(p);

        const a = document.createElement("a");
        a.className = "text-blue-400 hover:text-blue-700"
        a.href = e.url;
        a.textContent = e.url;
        a.target = "_blank";
        innerDiv.appendChild(a);

        container.appendChild(innerDiv);
    });
}

async function displayBookmarks() {
    const response = await fetch(`${SERVER_URL}/bookmarks/view`, {
        method: "GET",
        credentials: "include"
    });
    if (response.ok) {
        const bookmarks = await response.json();
        renderBookmarks(bookmarks);
    } else {
        console.error("Failed to load bookmarks");
    }
}