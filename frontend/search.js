const TIMEOUT_INTERVAL = 300;

const searchBar = document.getElementById("searchBar");
let debounceTimer;
searchBar.addEventListener("input", () => {
    clearInterval(debounceTimer);
    const query = searchBar.value.trim();
    if (!query) {
        displayBookmarks();
        return;
    }
    debounceTimer = setTimeout(() => searchBookmarks(query), TIMEOUT_INTERVAL);
});

async function searchBookmarks(query) {
    const response = await fetch(`${SERVER_URL}/bookmarks/search?query=${encodeURIComponent(query)}`, {
        method: "GET",
        credentials: "include"
    });
    if (response.ok) {
        const filteredBookmarks = await response.json()
        console.log(filteredBookmarks);
        renderBookmarks(filteredBookmarks);
        console.log("Search succeeded");
    } else {
        console.error("Failed to search bookmarks");
    }
}