# QuizApp

Une application de quiz interactive développée en Java pour tester les compétences des stagiaires ! 🧠

## Fonctionnalités

- Interface graphique simple et intuitive (Java Swing)
- Affichage d’une série de questions à choix multiples
- Évaluation automatique du score
- Navigation entre les questions
- Affichage du résultat final

## Technologies utilisées

- Java 17
- Swing (interface graphique)
- IDE recommandé : IntelliJ IDEA / Eclipse
- Maven (optionnel, si dépendances ajoutées)

## Structure du projet
```text
QuizApp/
├── src/
│   └── quiz/
│       ├── Main.java
│       ├── Question.java
│       ├── Quiz.java
│       └── QuizFrame.java
├── README.md
└── ...
 ```

## Comment exécuter le projet

1. **Cloner le dépôt** :

```bash
git clone https://github.com/ton-utilisateur/QuizApp.git
cd QuizApp

## Compiler et exécuter avec java

javac src/quiz/*.java
java -cp src quiz.Main

## Aperçu
