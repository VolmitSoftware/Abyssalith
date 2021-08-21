FROM openjdk:11
RUN dir -a
RUN  chmod +x ./gradlew 
RUN dir -a
RUN ./gradlew shadowJar
RUN dir -a
COPY /build/libs .
WORKDIR /
CMD ["java", "-Xmx500m", "-Xms500m", "-jar", "Abyssalith-1.0-all"]