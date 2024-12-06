# Kleiderschrank-Backend
### Allgemeine Informationen:
- `Spring Boot 3.4.0`
- `Java`
- `H2-Database` mit `Hibernate`
- Backend zu der **Next.js React** Webseite **Kleiderschrank**

## Grundlegende Funktionen
1. Erstellen eines **Benutzerkontos**
2. Erstellung eines **Kleiderschranks**
3. Erstellung, Bearbeitung und Verwaltung von **Kleidungsstücken**
4. Verwalten des **`inLaundry` Status**
5. Erstellung, Bearbeitung und Verwaltung von **Outfits**
6. Ausgabe einer **Statistik** zu getragenen und nicht-getragenen Kleidungsstücken

## Datenstruktur

./  
├── data/                                   # Generierte H2-Datenbank  
├── src ... kleiderschrank/  
│   ├── controller/                         # RestController  
│   ├── domain/                             # Datenbank Entitäten  
│   ├── dto/                                # Data Transfer Objects  
│   ├── repository/  
│   ├── service/                            # Logik für die Rest Controller  
│   └── KleiderschrankApplication.java  
├── application.yaml  
└── README.md
