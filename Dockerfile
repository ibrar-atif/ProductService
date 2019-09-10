FROM openjdk:8
ADD target/Product-Service.jar Product-Service.jar
EXPOSE 8087
ENTRYPOINT ["java","-jar","Product-Service.jar"]