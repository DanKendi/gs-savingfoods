FROM maven:3.9.6-eclipse-temurin-21 AS build
WORKDIR /app
COPY . .

RUN chmod +x ./mvnw
RUN ./mvnw -DoutputFile=target/mvn-dependency-list.log -B -DskipTests clean dependency:list install


FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/quarkus-app/ ./target/quarkus-app/

CMD ["java", "-jar", "target/quarkus-app/quarkus-run.jar"]