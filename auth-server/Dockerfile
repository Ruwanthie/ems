FROM openjdk:8
ADD target/authserver.jar authserver.jar
EXPOSE 8282
ENTRYPOINT ["java","-jar","authserver.jar"]