{\rtf1\ansi\ansicpg1252\cocoartf2867
\cocoatextscaling0\cocoaplatform0{\fonttbl\f0\fswiss\fcharset0 Helvetica;}
{\colortbl;\red255\green255\blue255;}
{\*\expandedcolortbl;;}
\paperw11900\paperh16840\margl1440\margr1440\vieww29200\viewh15700\viewkind0
\pard\tx720\tx1440\tx2160\tx2880\tx3600\tx4320\tx5040\tx5760\tx6480\tx7200\tx7920\tx8640\pardirnatural\partightenfactor0

\f0\fs24 \cf0 # Use official Java 17 runtime\
FROM eclipse-temurin:17-jdk\
\
# Set working directory inside container\
WORKDIR /app\
\
# Copy the built jar into container\
COPY target/*.jar app.jar\
\
# Expose Spring Boot default port\
EXPOSE 8080\
\
# Run the application\
ENTRYPOINT ["java","-jar","app.jar"]\
}