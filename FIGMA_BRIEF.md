# Gold Cascade — Figma AI Design Brief

---

## 1. Art Style & Color Palette

**Art Style:** Flat design with subtle depth effects and cartoon-realism metallic accents. All UI elements feature rounded corners (8–12px radius) and clean vector shapes. Gold nuggets and ore carts use glossy metallic finishes with inner highlights to communicate value and weight. The overall aesthetic balances playful accessibility with a premium, rewarding feel—suitable for ages 8+ while maintaining visual polish.

**Primary Color Palette:**
- **Deep Mine Blue:** `#1A3A52` (dark backgrounds, pegboards, mine walls)
- **Gold Ore:** `#FFD700` (nuggets, high-value accents, primary CTA buttons)
- **Rich Copper:** `#B87333` (secondary accents, cart highlights, medium-value UI)
- **Slate Gray:** `#3D4A5C` (text, secondary UI, pegboard pegs)

**Accent Colors:**
- **Emerald Green:** `#2ECC71` (success states, leaderboard top 3, positive feedback)
- **Warm Amber:** `#F39C12` (warnings, score multipliers, achievement notifications)

**Typography Mood & Weight:** Bold, confident, readable at small sizes. Use **Poppins Bold** (700 weight) for all headers and button labels. Use **Poppins Regular** (400 weight) for body text and score displays. All text is highly legible with strong contrast against backgrounds (minimum WCAG AA compliance).

---

## 2. App Icon — icon_512.png (512×512px)

**Description:**

The app icon features a **radial gradient background** transitioning from `#1A3A52` (top-left) to `#0F1F2E` (bottom-right), evoking the depth of a mine shaft. Centered within a 400×400px safe zone is a **stylized mine shaft cross-section** showing three gold nuggets mid-cascade through a pegboard. The nuggets are rendered in glossy `#FFD700` with inner highlights (`#FFFACD` at 60% opacity) and cast subtle drop shadows (`#000000` at 30% opacity) to suggest depth and weight. The pegboard pegs are small `#3D4A5C` circles arranged in a cascading pattern. At the bottom of the icon, a single **ore cart in profile** (rendered in `#B87333` with `#FFD700` ore lumps spilling inside) completes the visual narrative of the game mechanic. A subtle **outer glow** (`#FFD700` at 15% opacity, 12px blur) surrounds the entire composition to make the icon pop on home screens. The overall mood is energetic, rewarding, and instantly communicates "drop and collect."

---

## 3. UI Screens (480×854 portrait)

### MainMenuScreen

**Background:** Full-screen image of a mine entrance at dusk—rich `#1A3A52` sky with warm `#F39C12` lantern light spilling from a wooden mineshaft entrance frame (center-left). Subtle animated parallax clouds drift slowly.

**Header:** "GOLD CASCADE" in **Poppins Bold**, 48px, `#FFD700`, centered at top with 24px padding. Subtitle "Drop. Collect. Score." in Poppins Regular 14px, `#B87333`, centered below title.

**Buttons (vertical stack, center):** Four buttons occupy the center-right area:
- **"PLAY"** — 56px tall, `#FFD700` background, `#1A3A52` text, Poppins Bold 20px
- **"LEADERBOARD"** — 56px tall, `#B87333` background, white text, Poppins Bold 16px
- **"HOW TO PLAY"** — 56px tall, `#3D4A5C` background, white text, Poppins Bold 16px
- **"SETTINGS"** — 56px tall, `#3D4A5C` background, white text, Poppins Bold 16px

**Key Elements:** Decorative gold nugget illustrations (3–4 small nuggets) scattered around edges at 20% opacity. Subtle "TAP TO PLAY" pulse animation near the PLAY button.

---

### MineSelectScreen

**Background:** Underground cavern interior—`#1A3A52` with darker `#0F1F2E` cave walls on edges. Three vertical shafts descend from top to bottom, each glowing faintly with ore color (`#FFD700` for Shallow, `#B87333` for Deep, `#2ECC71` for Mother Lode).

**Header:** "SELECT YOUR MINE" in Poppins Bold 32px, `#FFD700`, top-center with 20px padding.

**Mine Selection Cards (vertical stack, center, 20px gap between):**
1. **Shallow Seam Card** — `#2D5A7B` background, 320px wide × 100px tall, rounded 12px, left-aligned shaft icon (`#FFD700` nugget). Label "SHALLOW SEAM" in Poppins Bold 18px, `#FFD700`. Subtext "Beginner Friendly" in Poppins Regular 12px, `#B87333`. Right side shows difficulty indicator (1 star in `#F39C12`). Tap to enter ShallowSeamScreen.

2. **Deep Vein Card** — `#3D6B8F` background, 320px wide × 100px tall, rounded 12px, shaft icon (`#B87333` ore). Label "DEEP VEIN" in Poppins Bold 18px, `#FFD700`. Subtext "Intermediate Challenge" in Poppins Regular 12px, `#B87333`. Right side shows difficulty (2 stars). Tap to enter DeepVeinScreen.

3. **Mother Lode Card** — `#4D7B9F` background, 320px wide × 100px tall, rounded 12px, shaft icon (`#2ECC71` premium ore). Label "MOTHER LODE" in Poppins Bold 18px, `#FFD700`. Subtext "Expert Only" in Poppins Regular 12px, `#B87333`. Right side shows difficulty (3 stars). Tap to enter MotherLodeScreen.

**Bottom Button:** "BACK" button (56px tall, `#3D4A5C`, white text) bottom-center, returns to MainMenuScreen.

---

### ShallowSeamScreen

**Background:** Low-density pegboard cavern—`#1A3A52` with sparse `#3D4A5C` pegs arranged in gentle columns. Warm `#F39C12` light from above illuminates ore carts at the base in `#B87333`.

**Header:** "SHALLOW SEAM" in Poppins Bold 24px, `#FFD700`, top-center. Real-time score display: "SCORE: 0" in Poppins Bold 32px, `#FFD700`, top-right. Nugget counter "NUGGETS: 10/10" in Poppins Regular 14px, `#B87333`, top-left.

**Game Area (center, 340×520px):** Pegboard visualization with animated physics—gold nuggets (`#FFD700` circles with highlights) fall through pegs. Four ore carts at bottom in shades of `#B87333`, each labeled with point values in small Poppins Regular 10px, white text. Cart glow increases when a nugget lands (brief `#FFD700` highlight).

**Tap Zone:** Upper 60% of screen—invisible tap-responsive area triggers nugget release with visual feedback (brief white flash at tap point).

**Bottom HUD:** "TAP TO DROP" instruction in Poppins Regular 12px, `#F39C12`, pulsing gently. Pause button (small, `#3D4A5C`, top-right corner) and Menu button (small, `#3D4A5C`, top-left corner).

---

### DeepVeinScreen

**Background:** Medium-density pegboard cavern—`#1A3A52` with denser `#3D4A5C` peg grid. Darker lighting with `#B87333` ore glowing from walls. Six ore carts at bottom with varied `#B87333` and `#2ECC71` colors indicating different point tiers.

**Header:** "DEEP VEIN" in Poppins Bold 24px, `#FFD700`, top-center. Real-time score "SCORE: 0" in Poppins Bold 32px, `#FFD700`, top-right. Nugget counter "NUGGETS: 10/10" in Poppins Regular 14px, `#B87333`, top-left.

**Game Area (center, 340×520px):** Pegboard with tighter peg spacing creating more complex physics paths. Gold nuggets fall with more visible deflections. Six ore carts at bottom in gradient `#B87333` to `#2ECC71`, each clearly labeled with point multipliers (1x, 1.5x, 2x, etc.) in Poppins Regular 10px, white.

**Tap Zone:** Upper 60% of screen—tap-sensitive with subtle crosshair cursor feedback (thin `#FFD700` lines) appearing at tap location momentarily.

**Bottom HUD:** "TAP STRATEGICALLY" in Poppins Regular 12px, `#F39C12`, centered. Pause and Menu buttons (top corners).

---

### MotherLodeScreen

**Background:** High-density pegboard cavern—`#0F1F2E` (darkest) with very dense `#3D4A5C` peg forest. Rich `#2ECC71` premium ore glows from walls and ceiling. Eight ore carts at bottom with full spectrum from `#B87333` through `#2ECC71` to rare `#FFD700` premium carts.

**Header:** "MOTHER LODE" in Poppins Bold 24px, `#FFD700`, top-center. Real-time score "SCORE: 0" in Poppins Bold 32px, `#FFD700`, top-right. Nugget counter "NUGGETS: 10/10" in Poppins Regular 14px, `#2ECC71`, top-left (color changed to reflect difficulty).

**Game Area (center, 340×520px):** Pegboard with maximum peg density (challenging physics). Gold nuggets fall with complex multi-deflection paths. Eight ore carts at bottom spanning `#B87333` → `#2ECC71` → `#FFD700` (premium), each labeled with aggressive point values and multipliers (1x, 2x, 3x, 5x max) in Poppins Regular 10px, white. Premium `#FFD700` carts have subtle glow effect.

**Tap Zone:** Upper 60% of screen—tap-sensitive with advanced crosshair feedback (small `#FFD700` crosshair + faint ripple effect on tap).

**Bottom HUD:** "MASTER THE MINE" in Poppins Regular 12px, `#2ECC71`, centered. Pause and Menu buttons (top corners).

---

### RoundResultScreen

**Background:** Full-screen gradient from `#1A3A52` (top) to `#0F1F2E` (bottom), overlaid with semi-transparent `#000000` (40% opacity) panel. Decorative falling gold nuggets animate softly in background at 10% opacity.

**Header:** "ROUND COMPLETE" in Poppins Bold 28px, `#FFD700`, centered top with 20px padding.

**Result Panel (center, 320×380px):** Semi-transparent `#2D5A7B` rounded rectangle (12px radius), centered vertically.
- **Final Score Display:** "FINAL SCORE" label in Poppins Regular 14px, `#B87333`, above large "9,450 PTS" in Poppins Bold 48px, `#FFD700`, centered. Animated counter ticks up from 0 to final score over 1.5 seconds.
- **Breakdown (below score):** "Gold Collected: 8 nuggets" in Poppins Regular 12px, white. "Best Cart: Premium Mother Lode (5x)" in Poppins Regular 12px, `#2ECC71`.
- **Leaderboard Placement:** "You rank #7 globally" in Poppins Regular 12px, `#B87333`.

**Bottom Buttons (vertical stack, 40px above bottom edge, center):**
- **"PLAY AGAIN"** — 56px tall, `#FFD700` background, `#1A3A52` text, Poppins Bold 18px, wide button (280px). Taps return to MineSelectScreen.
- **"MAIN MENU"** — 56px tall, `#3D4A5C` background, white text, Poppins Bold 16px, wide button (280px). Taps return to MainMenuScreen.

---

### GameOverScreen

**Background:** Underground vault interior—`#0F1F2E` with gold ore deposits glowing in `#FFD700` on walls. Subtle animated light flicker effect.

**Header:** "MINING SESSION COMPLETE" in Poppins Bold 28px, `#FFD700`, top-center with 24px padding.

**Session Summary Panel (center, 340×420px):** `#2D5A7B` rounded rectangle (12px radius).
- **Total Session Score:** "SESSION TOTAL" in Poppins Regular 14px, `#B87333`, above "47,230 PTS" in Poppins Bold 52px, `#FFD700`, centered.
- **Statistics Grid (below, 2 columns):**
  - "Best Mine: Deep Vein" / "Accuracy: 78%" — Poppins Regular 12px, white, `#B87333` labels
  - "Nuggets Dropped: 30" / "Average per Round: 15,743" — Poppins Regular 12px, white
- **Achievement Badge (if earned):** Optional small badge (e.g., "GOLD DIGGER" or "LUCKY STRIKE") in `#F39C12`, Poppins Bold 10px, centered above statistics if player earned an achievement.

**Bottom Buttons (vertical stack, center, 40px above bottom):**
- **"PLAY AGAIN"** — 56px tall, `#FFD700` background, `#1A3A52` text, Poppins Bold 18px, 280px wide
- **"MAIN MENU"** — 56px tall, `#3D4A5C` background, white text, Poppins Bold 16px, 280px wide

---

### LeaderboardScreen

**Background:** Rich mine interior—`#1A3A52` with ornate carved stone walls. Gold trim (`#FFD700`) frames the leaderboard area for prestige.

**Header:** "GLOBAL LEADERBOARD" in Poppins Bold 28px, `#FFD700`, top-center. Filter tabs below header (horizontal, center): "ALL TIME" (active, `#FFD700` underline), "THIS WEEK", "THIS MONTH" — Poppins Regular 12px, `#B87333`.

**Leaderboard List (center, 320×520px):** White background with 12px rounded corners, faint `#3D4A5C` border.
- **Rows (10 visible, scrollable):** Each row is 48px tall with light `#3D4A5C` divider below.
  - **Rank Column:** "#1", "#2", etc. in Poppins Bold 14px, `#1A3A52`, left-aligned with 12px padding.
  - **Player Name:** "GoldRush99" in Poppins Regular 13px, `#1A3A52`, 60px column width.
  - **Score:** "85,340" in Poppins Bold 14px, `#FFD700`, right-aligned with 12px padding.
  - **Top 3 Rows:** Highlight with subtle background tint—#1 `#FFD700` (10% opacity), #2 `#B87333` (10%), #3 `#2ECC71` (10%).
  - **Current Player Row:** Highlighted with `#1A3A52` background (15% opacity) and a small badge "YOU" in Poppins Bold 10px, `#FFD700`.

**Bottom Button:** "BACK" button (56px tall, `#3D4A5C`, white text, Poppins Bold 16px, 280px wide), bottom-center, returns to MainMenuScreen.

---

### SettingsScreen

**Background:** Cozy mine office interior—`#1A3A52` with wooden beams and a large map of the mines on the wall. Warm `#F39C12` lighting from a desk lamp.

**Header:** "SETTINGS" in Poppins Bold 28px, `#FFD700`, top-center with 20px padding.

**Settings Panel (center, 340×480px):** `#2D5A7B` rounded rectangle (12px radius), vertically stacked settings.

**Settings Options (vertical stack, 16px gaps):**

1. **Sound Settings (row 1):**
   - Label "SOUND" in Poppins Regular 14px, white, left-aligned
   - Toggle switch (circular, `#B87333` inactive / `#2ECC71` active), right-aligned. Current state: ON (green).

2. **Music Settings (row 2):**
   - Label "MUSIC" in Poppins Regular 14px, white, left-aligned
   - Toggle switch, right-aligned. Current state: ON (green).

3. **Haptics Settings (row 3):**
   - Label "HAPTICS (Vibration)" in Poppins Regular 14px, white, left-aligned
   - Toggle switch, right-aligned. Current state: ON (green).

4. **Difficulty Preset (row 4):**
   - Label "DIFFICULTY" in Poppins Regular 14px, white, left-aligned
   - Dropdown showing "NORMAL" (with small triangle toggle), right-aligned. Options: EASY, NORMAL, HARD (each adjusts peg density and cart layout slightly for existing mines).

5. **Data Management (row 5):**
   - Label "RESET GAME DATA" in Poppins Regular 14px, `#F39C12` (warning color), left-aligned
   - Small "RESET" button (40px tall, `#F39C12` background, `#1A3A52` text, Poppins Bold 12px), right-aligned. Taps show confirmation dialog: "Erase all scores and progress?" with CONFIRM (`#F39C12`) and CANCEL (`#3D4A5C`) buttons.

**Bottom Buttons (vertical stack, center, 40px above bottom):**
- **"BACK"** — 56px tall, `#B87333` background, white text, Poppins Bold 16px, 280px wide, returns to MainMenuScreen.

---

### HowToPlayScreen

**Background:** Tutorial chamber interior—`#1A3A52` with illustrated stone panels showing game mechanics. Soft `#B87333` spotlights highlight key areas.

**Header:** "HOW TO PLAY" in Poppins Bold 28px, `#FFD700`, top-center with 20px padding.

**Content Area (center, scrollable, 340×620px):** White background with 12px rounded corners and `#3D4A5C` subtle border. Content vertically stacked with 16px padding internal.

**Tutorial Sections (scrollable, each ~120px tall):**

1. **Section 1: "The Goal"**
   - Illustration (small, 80×80px, centered): Gold nugget icon + ore cart icon
   - Title: "Drop & Collect" in Poppins Bold 14px, `#1A3A52`, centered
   - Text: "Drop 10 gold nuggets through the pegboard. Guide them into ore carts to score points." in Poppins Regular 12px, `#3D4A5C`, centered, max width 300px.

2. **Section 2: "How to Drop"**
   - Illustration (small, 80×80px, centered): Finger tapping icon + nugget falling icon
   - Title: "Tap to Release" in Poppins Bold 14px, `#1A3A52`, centered
   - Text: "Tap anywhere on the screen to drop the next nugget. Physics will bounce it left and right through pegs." in Poppins Regular 12px, `#3D4A5C`, centered.

3. **Section 3: "Scoring"**
   - Illustration (small, 80×80px, centered): Ore carts in gradient, point multipliers
   - Title: "Earn Points" in Poppins Bold 14px, `#1A3A52`, centered
   - Text: "Different ore carts have different values. Premium carts (green/gold) award higher multipliers. Aim high!" in Poppins Regular 12px, `#3D4A5C`, centered.

4. **Section 4: "Mine Difficulty"**
   - Illustration (small, 80×80px, centered): Three mine shafts with density indicator
   - Title: "Choose Your Challenge" in Poppins Bold 14px, `#1A3A52`, centered
   - Text: "Shallow Seam: Beginner | Deep Vein: Intermediate | Mother Lode: Expert. Pegs get denser, carts more varied." in Poppins Regular 12px, `#3D4A5C`, centered.

5. **Section 5: "Tips & Tricks"**
   - Title: "Pro Tips" in Poppins Bold 14px, `#1A3A52`, left-aligned
   - Bullet list (Poppins Regular 11px, `#3D4A5C`):
     - "Tap early to guide nuggets left; tap late to push right."
     - "Watch the physics—pegs in the middle slow nuggets."
     - "Combo bonuses: land 3+ in the same cart for a multiplier!"

**Bottom Button:** "GOT IT!" button (56px tall, `#FFD700` background, `#1A3A52` text, Poppins Bold 16px, 280px wide), bottom-center, returns to MainMenuScreen.

---

## 4. Export Checklist

- icon_512.png (512×512)
- main_menu_screen.png (480×854)
- mine_select_screen.png (480×854)
- shallow_seam_gameplay.png (480×854)
- deep_vein_gameplay.png (480×854)
- mother_lode_gameplay.png (480×854)
- round_result_screen.png (480×854)
- game_over_screen.png (480×854)
- leaderboard_screen.png (480×854)
- settings_screen.png (480×854)
- how_to_play_screen.png (480×854)
