# 8-bit Game Development Guidelines

## Project Overview
This is a console-based grid game inspired by 1990s 8-bit aesthetics. The game features a 5x5 grid world where players control actors with health, score, strength, and position attributes.

## Architecture
- **bean/block/**: Game entity models (Actor, MainActor)
- **function/console/view/**: UI components extending abstract View class
- **function/console/**: Game loop and console logic

## Key Components
- **Actor**: Base class for game entities with health, score, strength, type, and (x,y) position
- **MainActor**: Player character extending Actor
- **View**: Abstract base for UI screens with `show()`, `update()`, and `clear()` methods
- **GamingView**: Main game display showing 5x5 grid with ■ characters and right-side stats panel

## Coding Patterns
- Views use ANSI escape sequences (`\033[H\033[2J`) for console clearing
- Grid display: 5x5 layout with stats overlay (health, score, level, items, turn)
- Package structure: `bean` for data models, `function` for features
- Method naming: `updata()` instead of `update()` (follow existing convention)

## Development Workflow
- Pure Java project, no build tools required
- Run via `javac` compilation and `java` execution
- Console-based with real-time display updates

## File Structure Examples
- New views should extend `View` and implement abstract methods
- Actors placed in `bean/block/` with position coordinates
- Game logic in `function/` packages

## Conventions
- Use ■ character for grid cells
- Stats display format: "| Label: value" on right side
- Position coordinates: (x,y) with 0-based indexing
- Follow existing package naming (euipment → equipment, but maintain consistency)