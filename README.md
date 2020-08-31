# Kurzdokumentation zu 2048
## Funktionsweise des Spiels
Dieses Projekt stellt das Spiel 2048 mit verschiedenen KI-Varianten sowie Highscore-Liste und einer Speicherfunktion dar.
Zum Spielen einfach die .jar-Datei (sieh GitHub => Releases) öffnen. Bitte achten Sie darauf, die neueste Version von Java SE zu installieren!
Während des Spiels werden in dem Ordner, in dem auch die .jar-Datei platziert wurde, die beiden Dateien „savegame.tmp“ sowie „Scores“ erstellt. Erstere enthält den aktuellen Spielstand, wenn das Spiel gespeichert wurde, letztere die aktuelle Highscore-Tabelle.

## Aufbau des Projekts
In dem Ordner „src“ finden sie den Source-Code. Der Ordner „resources“ enthält die Javadoc-Dokumentation. In „out“ finden Sie den kompilierten Code sowie das jar-Artefakt.

## Aufbau des Source-Codes
Das Projekt ist nach dem MVC entwickelt worden, und ist aufgeteilt in die Packages model, view und controller. Benutzerinteraktionen auf der View werden von verschiedenen Listenern in controller und view aufgefangen und an das model weitergeleitet, wo der gewünschte Spielzug verarbeitet wird. Änderungen hier werden anschließend per Listenern an die View zurückgegeben.
Jedes Package besitzt neben den Klassen (sowie teilweise weiteren Packages zur Untergliederung) die Unterpackages „intf“ und „test“. Ersteres enthält die verwendeten Interfaces, letzteres Unit-Tests sowie verwendete Mocks. 
Model enthält sämtliche Klassen zur Spiellogik. Hier befinden sich die beiden wichtigsten Klassen „Matrix“ (repräsentiert das Spielfeld) sowie „Mover“ (führt Bewegungen auf dem Spielfeld aus). Zudem enthält das Model auch weitere Packages:
-	AI: enthält die KI-Varianten. Das wären „zufällig“, „kooperativ“ und „Minmax“. Es gibt dabei zwei Implementierungen der Minmax-Variante: Eine klassische „MinMaxAI“-Klasse sowie die Variante „MinMaxMulti“ mit der Hilfsklasse „MinMaxMultiCalc“, die Multithreading für eine bessere Performance benutzt.
-	leaderboard: Dieses Package enthält Fachlogik zur Highscore-Liste, zur Punktestandspeicherung sowie zur Spielstandspeicherung.
Controller enthält zum einen „Main“, welcher den Startpunkt des Projektes darstellt, sowie einige kleinere Listener, mit denen zwischen View und Model kommuniziert wird, sowie den „GameManager“, der das Spiel initialisiert, indem die benötigten Objekte erstellt werden und vor allem im Rahmen des Listener-Patterns miteinander verbunden werden.
View enthält alle Klassen zur Erstellung der Oberfläche. Die wichtigsten Klassen sind:
-	Grid: Dies stellt das 2048-Board dar, auf dem die verschiedenen Tiles mitsamt Animationen gezeichnet werden
-	RunController: Läuft als Thread während des Spiels. Gibt dabei regelmäßig Impulse zum Zeichnen der Oberfläche (die render()-Methoden), sowie zum Ermöglichen von Animationen (die update()-Methoden).
-	Tile: Stellt ein einzelnes Tile auf dem Board dar. Stellt Methoden zum Zeichnen sowie zu Animationen davon bereit.
-	ViewManager: Ähnlich wie der GameManager im controller baut diese Klasse die verschiedenen Objekte der view bei Spielstart auf. Zudem verwaltet diese Klasse Objekte, die Updates bzw. Rendering brauchen, und verwaltet das aktuelle Panel.
Das Package GUI der view enthält die verschiedenen Panels (Menü, Highscores sowie Spieloberfläche).

## Überblick über Listener zwischen Model und View
Die View aktualisiert sich durch Listener zwischen Model und View. Das sind folgende:
-	BoardObserver: Überträgt einzelne Spielzüge (Bewegungen von einzelnen Tiles und Erschaffen neuer Tiles) zwischen model und dem Grid. Der BoardObserver wird dabei von Mover und den KIs stets aktualisiert.
-	GameConditionObserver: Überträgt den aktuellen Zustand des Spiels zwischen Matrix und Grid (läuft gerade noch, Spiel gewonnen, Spiel verloren).
-	ScoreObserver: Überträgt den aktuellen Punktestand zwischen ScoreManager und dem PlayPanel, damit dieser stets in der GUI angezeigt werden kann.
-	LeaderboardObserver: Überträgt die Highscoreliste zwischen Leaderboards und dem LeaderboardsPanel.

## Projektmitglieder
-	Dominik Sauerer
-	Lukas Klein
-	Aaron Vermeulen
-	Hauke Preisig
