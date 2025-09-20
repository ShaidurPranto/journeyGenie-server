#!/bin/bash

# production can be set to true or false
export PRODUCTION=false
export TOKEN_SECRET="c2VjcmV0a2V5Zm9yYXV0aGVudGljYXRpb24xMjM0NTY3ODkwMTIzNDU2Nzg5MA=="
export TOKEN_VALIDITY_MINUTES=60
# token refresh can be set to true or false
export TOKEN_REFRESH_ENABLED=false
export OAUTH_REDIRECT_PAGE='/'
export BACKEND_URL=http://localhost:8081
export FRONTEND_URL=http://localhost:5173
export GEMINI_API_KEY="AIzaSyC2ksUK_5jghVWduRYmMzum3Y0v9H_6gC0"
export CLOUD_NAME='dg1sx19ve'
export CLOUDINARY_URL='cloudinary://214429925976299:KRgnNaisrd_3PPVxjDRHfSOWAhY@dg1sx19ve'
export STRIPE_SECRET_KEY="sk_test_51S50jrEJfTfXV2h7FCcng9Y1hfJC18yxml9NWZhTgJxZIXJ4AOawilAEhR3Tj3xzYsAle36WtDKCbRKjbvtY5LZW00X5s8fTnf"
export HUGGINGFACE_SECRET_KEY="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"

#export SPRING_DATASOURCE_URL="jdbc:postgresql://pg-219c63da-mdshaidurrahmanpranto-a1e3.g.aivencloud.com:13318/journeyGenie?ssl=require&user=avnadmin&password=AVNS_HCNmMosmM1XVfGdJIWw"
#export SPRING_DATASOURCE_USERNAME="avnadmin"
#export SPRING_DATASOURCE_PASSWORD="AVNS_HCNmMosmM1XVfGdJIWw"

export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/journeyGenie
export SPRING_DATASOURCE_USERNAME="postgres"
export SPRING_DATASOURCE_PASSWORD="postgres"

GOOGLE_CLIENT_ID=329814224341-gdcuhkd75fja16d28dp14sj8vif43lvg.apps.googleusercontent.com \
GOOGLE_CLIENT_SECRET=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 \
mvn spring-boot:run
