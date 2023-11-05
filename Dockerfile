FROM openjdk:17
ADD target/stringUnique.jar backend.jar
ENTRYPOINT ["java", "-jar", "backend.jar"]