# Burro Hotel Management System

Prosta, ale w pełni funkcjonalna aplikacja konsolowa w Javie do zarządzania hotelem, zintegrowana z relacyjną bazą danych PostgreSQL. Projekt oparty na architekturze wielowarstwowej.

## Funkcjonalności
* Przeglądanie dostępnych pokoi.
* Rejestracja nowych gości hotelowych.
* Tworzenie rezerwacji ze zautomatyzowanym obliczaniem kosztów pobytu.
* Trwały zapis danych (Persistence) dzięki bazie PostgreSQL.

## Technologie
* **Język:** Java (np. 17+)
* **Baza danych:** PostgreSQL
* **Sterownik bazy:** JDBC (PostgreSQL Driver 42.7.2)
* **Narzędzie do budowania:** Gradle

## Struktura bazy danych
Projekt wykorzystuje 4 powiązane tabele oraz widoki (Views):
* `rooms`
* `guests`
* `reservations`
* `employees`

Aby uruchomić projekt lokalnie, należy najpierw wykonać skrypty SQL (tworzące tabele i widoki), które można znaleźć w pliku `DBconfig.sql`

## Jak uruchomić?
1. Sklonuj repozytorium: `git clone https://github.com/jakubstanisz/Hotel-project.git`
2. Utwórz bazę danych PostgreSQL o nazwie `hotel`.
3. Skonfiguruj klasę `DatabaseConnector` wpisując swojego użytkownika i hasło.
4. Uruchom klasę `Main.java`.
