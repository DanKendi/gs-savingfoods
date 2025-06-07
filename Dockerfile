FROM eclipse-temurin:21-jdk-alpine


WORKDIR /app


COPY . .


RUN chmod +x ./mvn


RUN ./mvn -DoutputFile=target/mvn-dependency-list.log -B -DskipTests clean dependency:list install


CMD ["sh", "-c", "java -jar target/quarkus-app/quarkus-run.jar"]