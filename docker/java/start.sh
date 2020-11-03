#!/bin/bash
# This scripts waits for database initialization


YELLOW='\033[0;33m'
NC='\033[0m' # No Color
chars="/-\|"


while ! mysqladmin ping -hmysql -uroot -proot --silent; do
for (( i=0; i<${#chars}; i++ )); do
    sleep 0.5
    # echo -en "${chars:$i:1}" "\r"
    echo -en "${YELLOW}${chars:$i:1}Waiting for database initialization...${NC}" "\r"
  done
done

java -jar /usr/irapvalidator.jar

