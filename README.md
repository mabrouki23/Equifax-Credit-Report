Projet Equifax – Système de rapport de crédit

Résumé
-------
Application Java simulant un bureau de crédit (CRC). Gère Clients, Accounts, Events, LenderInquiries, et calcule un credit score. Intègre : collections, lambdas, threads, Javadoc, tests unitaires.

Structure
---------
- src/main/java/com/equifax/domain : classes métier (Client, Account, Event, LenderInquiry, Creditor)
- src/main/java/com/equifax/service : services métier (ClientService, AccountService, EventService, LenderInquiryService)
- src/main/java/com/equifax/thread : threads (CreditScoreThread, NotificationThread)
- src/main/java/com/equifax/Main.java : démonstration
- src/test/java/com/equifax : tests unitaires

Requis
-------
- Java 25 (OpenJDK 25)
- Maven

Build & tests
--------------
1) Définir les options JVM nécessaires pour Mockito/Byte Buddy (si vous utilisez Mockito inline) :

PowerShell:
```
$env:MAVEN_OPTS='-Dnet.bytebuddy.experimental=true -XX:+EnableDynamicAgentLoading'
```

2) Compiler et lancer les tests:
```
mvn -f .\pom.xml clean test
```

Exécution
---------
Après packaging (ou via votre IDE), exécuter la classe principale :
```
mvn -f .\pom.xml -DskipTests package
java -cp target\credit-report-system-1.0.jar com.equifax.Main
```

Tests
-----
- Tests unitaires couvrant recherche de clients, calcul de balances, recalcul du credit score et notifications concurrentes.

Notes
-----
- Si Byte Buddy / Mockito inline pose des problèmes sur Java 25, utilisez JDK 21/22 ou évitez de mocker des classes du domaine (stubs/spies manuels utilisés dans certains tests).
