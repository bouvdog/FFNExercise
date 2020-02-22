FROM openjdk:8-jdk-alpine
VOLUME /tmp
COPY target/cfna-1.0-SNAPSHOT.jar cfna-1.0-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/cfna-1.0-SNAPSHOT.jar"]