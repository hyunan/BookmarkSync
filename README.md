# BookmarkSync
Bookmark syncing utility where users can upload/download bookmarks. Searching

## What was used
- Spring Boot
- Jsoup
- TailwindCSS

## To run
1. Clone the repo.
2. Inside directory, run this command: `mvn spring-boot:run`
3. Open `index.html` using LiveServer or other service. Code is configured to accept requests `localhost:5500`, but can be configured in `src/main/java/com/hyunan/bookmarkmanager/WebConfig.java`