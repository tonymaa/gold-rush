#!/bin/sh

# Start Spring Boot in the background
java -jar /app/app.jar &

# Start Nginx in the foreground
nginx -g 'daemon off;' 