FROM openjdk:8
ADD target/empservice.jar empservice.jar
EXPOSE 8083
ENTRYPOINT ["java","-jar","empservice.jar"]