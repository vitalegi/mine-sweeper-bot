# README

Bot per il gioco Campo Minato <http://campo-minato.com/>.

## Analisi

- 43x353 &rarr; angolo superiore sinistro
- 287x597 &rarr; angolo inferiore destro
- larghezza di ogni cella: 27.1
- Celle:
  - centro blu (1 mina): 0,0,255
  - centro verde (2 mine): 0,123,0
  - centro rosso (3 mine): 255,0,0
  - grigio: 189,189,189

## Task

- identificare il contenuto di ogni cella
- definire la prossima mossa
- cliccare lo schermo
- identificare lo stato del gioco

## Sviluppi futuri

- riconoscere quando la partita è finita
- riconoscere/stimare il punteggio della partita
- avviare una nuova partita
- introdurre IA per la gestione dei parametri di esecuzione
- introdurre IA per la decisione delle azioni da compiere

## Setup Ambiente

```cmd
mvn install:install-file -DgroupId=com.opencv -DartifactId=opencv -Dversion=4.0.1 -Dpackaging=jar -Dfile=C:\a\software\opencv\build\java\opencv-401.jar

mvn install:install-file -DgroupId=com.opencv -DartifactId=opencv-runtime -Dversion=4.0.1 -Dpackaging=dll -Dfile=C:\a\software\opencv\build\java\x64\opencv_java401.dll
```

## Installazione

```cmd
mvn clean package
```

## Esecuzione

```cmd
java -jar target\campo-minato-bot-0.0.1-SNAPSHOT.jar
```