# Vocabuilder
**Vocabuilder** is a mobile app for effective memorization of foreign words using the spaced repetition method, similar to Anki.

- [Overview](#overview)
  - [About this file](#about-this-file)
  - [Project Description](#project-description)
  - [Key Features](#key-features)
  - [Project information](#project-information)
  - [Project Structure](#project-structure)

## Overview
This project supports multiple languages. Please select your preferred language:
- English (current)
- [Русский](README-ru.md)

### About this file
The purpose of this file is to provide basic overview, setup, usage and other instructions as well as the general background information about the project.

### Project Description
Vocabuilder helps language learners quickly expand their vocabulary and retain knowledge for a long time. The app implements the popular and scientifically proven spaced repetition technique, adapting the review intervals based on the user's mastery level of each word. The user creates word sets or imports ready-made ones, then regularly reviews them at optimal times for better memorization.

### Key Features
The app has the following key features:
- creating flashcards with words, translations, examples, and pronunciation;

- importing and exporting word sets;

- spaced repetition algorithm with dynamic interval adjustment;

- user-friendly interface for flashcard learning;

- progress statistics and review history;

- support for multiple interface languages and word sets.

### Project information
This section provides the general information about the application, used tech stack and the team.

| | |
-- | --
**Title** | Vocabuilder
**Main repository** | https://gitlab.com/dev-pm/vocabuilder
**Mirroring repo** | https://github.com/dev-pm-pro/vocabuilder
**Released** | Sep 17, 2025
**Author(s)** | Viktor Volkov
**Publisher(s)** | DevPM
**Platform(s)** | Android
**Data storage** | Local database (SQLite/Realm) + optional synchronization
**UI/UX** | Modern and responsive design
**Core algorythm** | Repetition interval algorithm based on SM-2 or a modified approach

### Project Structure
This section defines the general project's structure and provides a top-level file/directory layout. Some items may not be present in the actual project or listed here depending on the specific user's configuration and current project state.

```sh
.📂                      # Project root
├─<📁 .git               # Version control files
├>>📁 .tmp               # Temporary files
├──📁 assets             # Additional value files
├─*📁 img                # Binary graphics
├─*📁 res                # Static resources
├──📁 samples            # Usage examples
├──📁 src                # Source code
├──📁 svg                # Vector graphics
├──📁 wiki               # Knowledge base
├──📜 .gitignore         # VCS blob ignores
├──📜 README.md          # Project overview
├──📜 SETUP.md           # Setup instructions
└─>📜 TODOs.md           # Task list
```

- `─` denotes an item
- `<` denotes a hidden item ingored by default;
- `*` denotes an item present in the project but ignored by VCS (put in the *.gitignore* file);
- `>` denotes an item ignored locally (at specific user's project level — should be put in the *.git/info/exclude* file)
- `>>` denotes an item ignored globally (at specific user's system level — should be put in the *~/.gitignore_global* file)
