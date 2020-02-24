FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/FFNExercise-1.0-SNAPSHOT.jar FFNExercise-1.0-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/FFNExercise-1.0-SNAPSHOT.jar"]