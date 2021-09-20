FROM openjdk:11
COPY /staging /abyssalith
CMD ["java", "-Xmx500m", "-Xms500m", "-jar", "Abyssalith.jar"]