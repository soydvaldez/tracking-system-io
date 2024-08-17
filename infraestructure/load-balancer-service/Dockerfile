FROM nginx:latest

RUN apt-get update && apt-get install jq openssh-server -y
COPY config/nginx.conf /etc/nginx/nginx.conf

EXPOSE 80
