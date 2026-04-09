# Specifications

**Link to Repo and project:**
* https://github.com/IndaPlus25/yea-rufusc-project
* https://github.com/orgs/IndaPlus25/projects/18/

**Project Description:**
This project is going to be the classic game Tetris, no fancy twists here.

## Technical Standards
* **Coordinate System:** (0,0) is the top-left corner.
* **Grid Size:** 10 columns × 20 rows.
* **Language/Framework:** Java with Swing.

## Features

### MVP:
* **Grid-system**: The state of the game board is saved in a 2D array (`Color[][]`) that tracks if a block is empty (`null`) or occupied by a specific color.
* **Graphic representation**: A `Board` class (JPanel) that renders the grid and the active piece using `paintComponent`.
* **7 different shapes**: I, J, L, O, S, T, and Z. Shapes have a rotation variable and coordinates of an “anchor piece” (pivot point) to keep track of them.
* **Collision detection**: Before movement or rotation, the game checks if the new position is within bounds and not occupied by the 2D array.
* **Movement/rotation**: Player input (Left, Right, Down, Rotate) to manipulate the current shape.
* **Line clearing**: When a row is full, it is deleted, and all rows above shift down.
* **Game loop**: A `javax.swing.Timer` that triggers a gravity-tick (automatic move down) and a `repaint()`.
* **Game Over**: Detection occurs when a new piece cannot be spawned at the top without colliding.

### Goal:
* **Storage (Hold)**: Capability to swap the current shape with a "held" shape once per turn.
* **Point-system**: Points awarded based on lines cleared (bonus for 4 lines at once).
* **Levels**: Difficulty increases (shorter Timer delay) and colors change based on total lines cleared.
* **Piece preview**: A sidebar UI displaying the next 1–3 upcoming pieces.

## Planning

**Rufus:**
* Grid-system (2D array logic)
* Graphic representation (paintComponent & Drawing)
* Line clearing logic
* Game loop (Timer setup)

**Yassin:**
* 7 different shapes (Matrix definitions)
* Collision detection logic (isOccupied check)
* Movement/Rotation logic

**Undecided:**
* Storage (Hold)
* Point-system
* Levels
* Piece preview

## Style-guide:
We will be following the **Google Java Style Guide**: https://google.github.io/styleguide/javaguide.html

## Workflow

### Main branch
The project will consist of one main branch, the trunk. All changes will be made through short-lived feature branches (e.g., `feat/rotation`) or directly to main for small fixes. The trunk should always be able to compile.

### Commits
Commits follow the **Conventional Commits** format: `type: description`.
* `feat:` for new features
* `fix:` for bug fixes
* `refactor:` for code improvements

**Example:** * “feat: add board grid structure"
* “fix: prevent moving outside board (closes #8)”

### Issues
* **Use for:** Bug reports on merged code, new feature suggestions, and tracking major tasks.
* **Do not use for:** Minor tasks you are currently working on.

### Definition of Done (DoD)
1. Code follows the style guide.
2. The feature is manually tested.
3. The code is merged to main via a Pull Request (PR) or direct push if small.