# Högre eller Lägre – Android app kortspel

Detta är min individuella appidé för kursen Androidutveckling.

Appen är ett enkelt kortspel där användaren ska gissa om nästa kort i leken blir **högre** eller **lägre** än det aktuella kortet.

App idé

- **Namn:** Högre eller Lägre  
- **Plattform:** Native Android (Kotlin, Android Studio)  
- **Mål:** Träna intuition och chansning med ett enkelt och tydligt kortspel.

**Regler (enkelt):**

1. Ett kort dras från en blandad kortlek.
2. Användaren gissar om nästa kort blir högre eller lägre.
3. Vid rätt gissning ökar poängen med 1.
4. Vid fel gissning nollställs poängen och användaren får välja om hen vill spela igen.

## Skiss – vyer

### 1. Startskärm (MainActivity)

- Appens logotyp
- Titel: **Högre eller Lägre**
- Kort beskrivning av spelet
- Knapp: **Starta spelet**

### 2. Spelskärm (GameActivity)

- Titel: **Högre eller Lägre**
- Instruktion: “Gissa om nästa kort blir högre eller lägre.”
- Text som visar aktuellt kort, t.ex. `Kort: K ♣`
- Text som visar poäng: `Poäng: 0`
- Två knappar:
  - **Högre**
  - **Lägre**
- En liten bild/ikon som visas när användaren gissar rätt.

## Användarperspektiv (kort motivering)

- Appen ska vara **enkel** och **snabb att förstå**.
- Allt viktigt (kort, poäng och knappar) är centrerat och tydligt.
- Färgerna är lugna (lila/vit) för att inte störa användaren.
- Knapparna är stora nog för att fungera bra på olika skärmstorlekar.

## Teknik (plan)

- **Språk:** Kotlin  
- **Layout:** XML (ConstraintLayout eller LinearLayout)  
- **Minst två aktiviteter:**  
  - `MainActivity` – startskärm  
  - `GameActivity` – själva spelet  
- En klass för kortleken (Card + Suit) och spel-logik.
