const downloadBtn = document.getElementById("downloadBtn");

downloadBtn.addEventListener("click", async () => {
    const response = await fetch(`${SERVER_URL}/bookmarks/download`, {
        method: "GET",
        credentials: "include"
    });
    if (response.ok) {
        const res = await response.text();
        const blob = new Blob([res], { type: "text/html" });
        const link  = document.createElement("a");
        link.href = URL.createObjectURL(blob);
        link.download = "bookmarks.html";
        link.click();
        console.log("Downloaded bookmarks successfully")
    } else {
        console.error("Failed to download bookmarks");
    }
});