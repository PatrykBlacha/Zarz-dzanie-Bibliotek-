# Etap 1: Budowanie aplikacji przy użyciu Gradle
FROM gradle:8.5.0-jdk21 AS build

# Ustaw katalog roboczy wewnątrz kontenera
WORKDIR /home/gradle/src

# Skopiuj cały projekt do katalogu roboczego
COPY . .

# Uruchom budowanie aplikacji bez wykonywania testów
RUN gradle build --no-daemon -x test

# Etap 2: Tworzenie finalnego, lekkiego obrazu
FROM eclipse-temurin:21-jre-jammy

# Ustaw katalog roboczy
WORKDIR /app

# Skopiuj zbudowany plik .jar z etapu budowania
COPY --from=build /home/gradle/src/build/libs/*.jar app.jar

# Ustaw port, na którym aplikacja będzie nasłuchiwać
EXPOSE 8080

# Polecenie uruchamiające aplikację przy starcie kontenera
ENTRYPOINT ["java", "-jar", "app.jar"]
