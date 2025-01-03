# Campus List: A Modern Android App for Exploring Universities
Campus List is an Android application built with Kotlin that allows you to explore universities in Indonesia. This app provides a user-friendly interface for browsing university details, searching for specific universities, and even opening their websites within the app.

<div style="display: flex; justify-content: space-around;">
  <img src="assets/screenshoot/home-light.jpeg" alt="Alt Text" title="Optional Title" style="width:300px; height:auto;">
  <img src="assets/screenshoot/home-dark.jpeg" alt="Alt Text" title="Optional Title" style="width:300px; height:auto;">
</div>

## Features
- Beautiful UI: The app leverages Jetpack Compose to create a modern and visually appealing user interface.
- Persistent University List: University data is saved locally using either Room or DataStore, ensuring a smooth and efficient experience even without internet access.
- Search Functionality: Easily search for universities by name using the built-in search feature.
- In-App Browser: Open university websites directly within the app for seamless navigation.
- Unit Testing: Rigorous unit tests ensure the app's reliability and performance.

## Technical Stack
- Programming Language: Kotlin
- Architecture: MVVM/MVI
- Libraries & Tools:
    - Kotlin Coroutines & Flow
    - Jetpack Compose (UI)
    - Hilt (Dependency Injection)
    - Room or DataStore (Local Data Persistence)
- Minimum SDK Version: 26
- Target SDK Version: Latest stable version