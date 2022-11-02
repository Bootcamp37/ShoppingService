FROM openjdk:11
VOLUME /tmp
EXPOSE 8084
ADD ./target/ShoppingService-0.0.1-SNAPSHOT.jar ms-shopping.jar
ENTRYPOINT ["java","-jar","/ms-shopping.jar"]