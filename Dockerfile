FROM eclipse-temurin:11-jdk

# Install X11 and GUI dependencies with timeout and retry
RUN apt-get update --fix-missing && \
    apt-get install -y --no-install-recommends \
    libxext6 \
    libxrender1 \
    libxtst6 \
    libxi6 \
    libxrandr2 \
    libasound2 \
    && apt-get clean \
    && rm -rf /var/lib/apt/lists/*

# Set working directory
WORKDIR /app

# Copy source code and dependencies
COPY src/ /app/src/
COPY lib/mysql-connector-j-8.2.0.jar /app/lib/

# Compile Java source
RUN javac -cp "/app/lib/mysql-connector-j-8.2.0.jar" -d /app /app/src/*/*.java

# Set classpath
ENV CLASSPATH="/app:/app/lib/mysql-connector-j-8.2.0.jar"

# Run the GUI application
CMD ["java", "-cp", "/app:/app/lib/mysql-connector-j-8.2.0.jar", "view.Main"]