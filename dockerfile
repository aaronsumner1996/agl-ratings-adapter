FROM eclipse-temurin

COPY build/libs/*all.jar agl-ratings-adapter-0.1.jar

CMD ["java","-jar","agl-ratings-adapter-0.1.jar"]