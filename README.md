# 📔 JournalEase

**Note-Taking & Journaling App** · `Mobile`

Simple and elegant note-taking application with rich text editing, search, and cloud sync.

## Why This Project

Journaling apps tend to pull people toward accounts, subscriptions, and cloud lock-in before they have written a single sentence. Mark wanted the opposite: a journal that opens, works, and keeps everything on the phone — no backend to configure, no data plan required, nothing that breaks on a plane or in a basement classroom. JournalEase is a deliberately local, distraction-free journal for students and anyone who wants a private place to think out loud.

The app also serves as a complete native Android exercise: activity flow and session gating, a RecyclerView note grid with color-coded cards, SharedPreferences persistence, and real document export (PDF and DOCX) — the everyday plumbing of a mobile product, built end to end.

## How It Works

Consider Aya, a student starting a new semester journal:

1. **Launch.** The branded splash screen — an owl in a graduation cap — covers the app's startup for about three seconds before handing off to the login screen.
2. **Sign in (demo mode).** Aya enters an email and password. The demo session accepts any credentials and remembers that she is logged in, so subsequent launches skip the login screen entirely and land straight in her notes.
3. **See her library.** The home screen shows a staggered two-column grid of color-coded note cards, with a search field across the top, a floating "+" button, and a menu offering logout.
4. **Write a note.** She taps "+", types a title and body (both required), and can hit "Clear Fields" to start over. A "Note Created Successfully" toast confirms the save and returns her to the grid, where the new card appears in a new color.
5. **Read and revise.** Tapping a card opens the detail view with a randomized background image; the edit button opens a pre-filled editor. Long-pressing a card's menu offers Edit, Delete (with a confirmation dialog), Export PDF, and Export docx — the exports are written to the device's Downloads folder.
6. **Close the loop.** When she is done for the day she logs out from the menu; the notes themselves persist locally on the device, ready for the next session.

Everything happens on-device. There is no server call in the loop — which is exactly the point.

## Modules

### Splash & Branding
`SplashScreenActivity` — a branded loading screen (owl mascot imagery, fade and translate animations) that displays for roughly three seconds before routing to the login flow.

**Scenario:** Aya sees the JournalEase identity immediately on launch, and the brief delay gives the app a deliberate, polished start instead of a hard cut to a login form.

### Session & Authentication
`DemoSession`, `MainActivity`, `signup`, `fogotpassword` — email/password login, sign-up, and forgot-password screens backed by a local demo session: any credentials are accepted, the logged-in flag persists in SharedPreferences, and returning users skip login. The forgot-password screen explains that no reset email is sent in demo mode.

**Scenario:** A first-time user registers with any email and a password of eight or more characters and is taken straight to their (initially empty or seeded) note library; the next day the app opens directly into their notes.

### Note Library
`notesactivity` — the home screen: a staggered-grid RecyclerView of color-coded note cards (colors assigned per note), a search field across the top, a floating action button to create notes, and a per-card popup menu for Edit, Delete, and export actions. Logout lives in the options menu.

**Scenario:** With a dozen entries accumulated, Aya scans the color-coded grid, filters with the search field, and reaches the note she wants in a couple of taps.

### Note Editor
`createnote`, `editnoteactivity` — title-and-body editors for new and existing notes, with required-field validation, a "Clear Fields" button to wipe the form without deleting the note, progress indicators, and toasts confirming every save.

**Scenario:** Halfway through drafting, Aya decides the title is wrong; she opens the editor, rewrites it, saves, and the card in the grid updates — her earlier text preserved throughout.

### Note Details
`notedetails` — a full reading view for a single note, showing title and body over a randomly chosen background image, with a floating button to jump straight into editing.

**Scenario:** Aya taps a card from the grid to reread yesterday's entry in a calmer, full-screen layout before deciding what to add today.

### Local Note Store
`LocalNoteStore`, `firebasemodel` — the persistence layer: notes are kept as a JSON array in SharedPreferences, exposed through a simple add/update/delete/get API with title-sorted retrieval and seeded demo content on first run. The store is deliberately offline — it replaces a cloud database so the app works with no network at all.

**Scenario:** On a flight with airplane mode on, Aya creates, edits, and deletes notes as usual; every change is written straight to device storage and survives the app being closed.

### Document Export
Export actions inside `notesactivity` — a per-note popup menu renders the entry as a PDF (via Android's `PdfDocument`) or a Word document (via Apache POI's `XWPFDocument`) and writes it to the device's Downloads folder, requesting storage permissions as needed.

**Scenario:** Aya exports a week's worth of reflection as a PDF to attach to a portfolio, or as a DOCX to keep editing in a word processor later.

## Features

- Create, edit, and delete notes
- Search through notes
- Color-coded note cards
- User authentication with email/password
- Clean, minimal Material Design UI

## Tech Stack

Implemented in this repository:

- Java
- Android SDK (AppCompat, Material Components, RecyclerView)
- Gradle
- SharedPreferences — local JSON note store (offline)
- Android `PdfDocument` + Apache POI — PDF and DOCX export

Earlier revisions of this README listed a Flutter/Firebase stack:

- Flutter
- Dart
- Firebase Auth
- Cloud Firestore

## Screenshots

### Brand identity — the owl wearing a graduation cap symbolizes wisdom and learning, setting the tone for a journaling app focused on reflection and personal growth. Loading screen masks Firebase initialization.

![Brand identity — the owl wearing a graduation cap symbolizes wisdom and learning, setting the tone for a journaling app focused on reflection and personal growth. Loading screen masks Firebase initialization.](screenshots/1.png)

### Empty state — shows new users what the interface looks like before any notes exist. The faded owl watermark and prominent "+" button guide users to create their first note.

![Empty state — shows new users what the interface looks like before any notes exist. The faded owl watermark and prominent "+" button guide users to create their first note.](screenshots/2.png)

### Note editor — the core creation interface where users type a title and body content. The "Clear Fields" button lets users start over without deleting the note manually.

![Note editor — the core creation interface where users type a title and body content. The "Clear Fields" button lets users start over without deleting the note manually.](screenshots/3.png)

### Confirmation feedback — the "Note Created Successfully" toast and visible note card confirm the save operation completed, giving users confidence their writing was preserved.

![Confirmation feedback — the "Note Created Successfully" toast and visible note card confirm the save operation completed, giving users confidence their writing was preserved.](screenshots/4.png)

### Growing collection — two color-coded note cards demonstrate how the home screen scales as users add more entries. Different colors help visually distinguish notes at a glance.

![Growing collection — two color-coded note cards demonstrate how the home screen scales as users add more entries. Different colors help visually distinguish notes at a glance.](screenshots/5.png)

### Account authentication — the login screen gates access to personal notes. Email/password with forgot-password recovery ensures only the note owner can read their private journal entries.

![Account authentication — the login screen gates access to personal notes. Email/password with forgot-password recovery ensures only the note owner can read their private journal entries.](screenshots/6.png)

## About

A lightweight journaling app built as a native Android (Java) application. Users sign in through a demo session, create notes with title and content, view them as color-coded cards in a staggered grid, search their entries, and export any note to PDF or DOCX. Notes are stored locally on the device, so the app works fully offline. Designed with a warm brown/beige color palette and owl mascot branding.

---

Built by [Mark Crysler Baddo](https://github.com/Kuraisura) · [Portfolio](https://kuraisler.xyz)
