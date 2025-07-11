# Estágio 1: Build com Maven e Java 21
FROM maven:3.9.6-eclipse-temurin-21-alpine AS builder
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src ./src
RUN mvn package -DskipTests

# Estágio 2: Execução com JRE
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app
# Copia o JAR do estágio de build
COPY --from=builder /app/target/pbe2senaihubapi-0.0.1-SNAPSHOT.jar app.jar
# Expõe a porta que a aplicação vai usar (Render usa a variável PORT)
EXPOSE 8080
# Comando para iniciar a aplicação
CMD ["java", "-jar", "app.jar"]