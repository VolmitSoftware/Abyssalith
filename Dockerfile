FROM openjdk:11
COPY . /ab
RUN cd /ab && dir -a
RUN cd /ab && chmod +x ./gradlew && ./gradlew shadowJar 
WORKDIR /ab/build/libs
CMD ["java", "-Xmx500m", "-Xms500m", "-jar", "Abyssalith-1.0-all"]