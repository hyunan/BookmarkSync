const uploadFile = document.getElementById("uploadFile");
uploadFile.addEventListener("change", async (e) => {
    e.preventDefault();
    const file = uploadFile.files[0];
    if (!file) {
        alert("No file uploaded.");
        return;
    }
    const fileNameParts = file.name.split(".");
    if (fileNameParts[fileNameParts.length - 1] !== "html") {
        alert("Must upload HTML bookmark files.")
        return;
    }
    const formData = new FormData();
    formData.append("file", file);
    const response = await fetch(`${SERVER_URL}/bookmarks/upload`, {
        method: "POST",
        body: formData,
        credentials: "include"
    });
    if (response.ok) {
        const data = await response.json();
        console.log("Succeeded: ", data);
    } else {
        const data = await response.json();
        console.error("Failed: ", data);
    }
    console.log(`${file.name} was successfully uploaded`);
});