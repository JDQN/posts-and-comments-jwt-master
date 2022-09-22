FROM openjdk:11.0.12
EXPOSE 8080
ADD target/alpha-post-and-comments.jar alpha-post-and-comments.jar
ENTRYPOINT ["java", "-jar", "/alpha-post-and-comments.jar"]