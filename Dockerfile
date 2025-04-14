# Build frontend
FROM node:18 AS frontend-build
WORKDIR /app/frontend
COPY frontend/package*.json ./
RUN npm install
COPY frontend/ ./
RUN npm run build

# Build backend
FROM maven:3.8.4-openjdk-17 AS backend-build
WORKDIR /app/backend
COPY backend/goldRush/pom.xml .
RUN mvn dependency:go-offline
COPY backend/goldRush/ .
RUN mvn clean package -DskipTests

# Final stage
FROM nginx:alpine
WORKDIR /app

# Install OpenJDK
RUN apk add --no-cache openjdk17-jre

# Copy backend jar
COPY --from=backend-build /app/backend/target/*.jar /app/app.jar

COPY ./sqlite/gold.sqlite /app/sqlite/gold.sqlite

# Copy frontend build
COPY --from=frontend-build /app/frontend/build /usr/share/nginx/html

# Copy nginx configuration
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Expose port
EXPOSE 80

# Start both nginx and spring boot
COPY start.sh /app/start.sh
RUN chmod +x /app/start.sh

CMD ["/app/start.sh"] 