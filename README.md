# JournalEase

An offline Android journal with color-coded notes, search, and PDF or Word export.

**Android** · **Learning Project** · 2022

[Portfolio](https://kuraisler.xyz)

## Purpose

JournalEase provides a small, local writing space for notes and journal entries. Users can capture a title and body, find earlier entries, revise them, and export an entry without relying on a cloud database.

## Intended users

Students and casual writers who want simple on-device note-taking and document export.

## Main capabilities

- Create, read, edit, delete, and search notes.
- Browse color-coded note cards in a staggered grid.
- Keep notes locally between app sessions.
- Export an entry as a PDF or DOCX document.

## How it was built

### Native Android screens

Java activities implement splash, demo login, library, detail, and editing screens. RecyclerView displays the note collection.

### On-device persistence

A LocalNoteStore serializes notes as JSON in SharedPreferences. A separate DemoSession stores the local sign-in flag.

### Document export

The export actions use Android PdfDocument and Apache POI to write PDF and DOCX output. The project adapts an earlier tutorial app to local storage.

## Technology stack

| Responsibility | Technologies |
| --- | --- |
| Android application | Java, Android SDK, AppCompat, Material Components, RecyclerView |
| Local storage | SharedPreferences, JSON |
| Document export | Android PdfDocument, Apache POI |

## Project scope and current state

A learning project adapted from a notes-app tutorial. Sign-in is a local demo session, not real authentication or encryption; there is no cloud sync. Export behavior depends on Android storage permissions.

The Android code adapts an earlier notes-app tutorial structure. Existing package and class names reflect that origin; the current persistence and session behavior are local.

## Source guide

- [`app/src/main/java/com/example/notesapptutorial/`](app/src/main/java/com/example/notesapptutorial/)
- [`app/src/main/java/com/example/notesapptutorial/LocalNoteStore.java`](app/src/main/java/com/example/notesapptutorial/LocalNoteStore.java)
- [`app/src/main/java/com/example/notesapptutorial/DemoSession.java`](app/src/main/java/com/example/notesapptutorial/DemoSession.java)

## Development setup

Import the Gradle project into Android Studio, synchronize dependencies, and run on a compatible emulator or device. app/build.gradle targets SDK 33 with minimum SDK 26. The current local demo session and note store do not require Firebase. Test document export permissions on the intended Android version.

## Screenshot and module walkthrough

Expand a screen below to see the capture and an explanation of the module, its visible controls, and its role in the application.

<details open>
<summary><strong>01 · JournalEase splash screen</strong></summary>

The owl-and-graduation-cap mark introduces the journal with its beige/brown visual theme. This is a startup identity screen, not a cloud-sync or authentication status display.

<p><img src="screenshots/1.png" alt="JournalEase splash screen" width="360"></p>

</details>

<details>
<summary><strong>02 · Empty note library</strong></summary>

The note list is empty, with a search action, overflow menu, and prominent add button. It gives first-time users a direct route to creating their first entry.

<p><img src="screenshots/2.png" alt="Empty note library" width="360"></p>

</details>

<details>
<summary><strong>03 · Note creation editor</strong></summary>

A title and body are being entered, with Clear Fields and save controls near the bottom. This module captures a new local journal entry before returning to the library.

<p><img src="screenshots/3.png" alt="Note creation editor" width="360"></p>

</details>

<details>
<summary><strong>04 · Saved note and confirmation</strong></summary>

A note card appears alongside a Note Created Successfully message. The card exposes an overflow menu for follow-up actions, confirming that the create flow has returned to the library.

<p><img src="screenshots/4.png" alt="Saved note and confirmation" width="360"></p>

</details>

<details>
<summary><strong>05 · Multiple color-coded entries</strong></summary>

Two cards show how the collection grows, with distinct colors, title/body previews, and per-note menus. Users can scan the library and reopen an entry without viewing every note in full.

<p><img src="screenshots/5.png" alt="Multiple color-coded entries" width="360"></p>

</details>

<details>
<summary><strong>06 · Local demo sign-in</strong></summary>

Email/password fields, password visibility, recovery, and registration links form the login interface. The current repository uses a local demo session; this screen should not be interpreted as real account protection or cloud synchronization.

<p><img src="screenshots/6.png" alt="Local demo sign-in" width="360"></p>

</details>

---

Project by [Mark Crysler Baddo](https://github.com/Kuraisura) · [Portfolio](https://kuraisler.xyz)
