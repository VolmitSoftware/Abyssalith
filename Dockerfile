FROM openjdk:11
RUN  chmod +x ./gradlew 
RUN ./gradlew shadowJar
COPY /build/libs .
WORKDIR /
CMD ["java", "-Xmx500m", "-Xms500m", "-jar", "Abyssalith-1.0-all"]