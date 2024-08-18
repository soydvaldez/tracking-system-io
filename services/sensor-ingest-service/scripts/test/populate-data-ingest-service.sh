#!/bin/zsh
#Realiza consultas al microservicio de ingesta para simular lecturas de sensores de temperatura

MIN=1
MAX=70

#Manda una peticion al servidor 
function request_ingest_service {
  HOST=http://localhost:8000/api/v1/ingestdata;
  JSON=''

  function generate_data {
    RANDOM_NUMBER=$((MIN + RANDOM % (MAX - MIN + 1)))

    produced_by=bash_script
    sensorId=sensor_temp1
    timestamp="$(date -u -Iseconds)"
    temperature="${RANDOM_NUMBER}"
    degree=celsius

    JSON=$(jq -n --arg produced_by "$produced_by" --arg sensorId "$sensorId" --arg timestamp "$timestamp" --arg temperature "$temperature" --arg degree "$degree" \
    '{produced_by: $produced_by, sensorId: $sensorId, timestamp: $timestamp ,temperature: $temperature, degree: $degree}')
  }

  count=9
  while [ $count -lt 10 ]; do
    generate_data
    echo $JSON | jq .
    #sleep 1


    response=$(curl -X POST -u user:password -s -i ${HOST} \
      -H "Content-Type: application/json" \
      --data "$JSON")

    echo $response

    ((count++))
  done

  location_resource=$(echo $response | grep -i "^Location:" | awk '{print $2}' | tr -d '\r')


  #if [[ "$location_resource" ]]; then

  if [[ "$location_resource" ]]; then
    echo "Realizando peticion al server..."
    curl -u user:password "$location_resource"
  fi

}

request_ingest_service
echo "Finalizo el script!"

