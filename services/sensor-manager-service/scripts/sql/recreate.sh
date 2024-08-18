#!/bin/bash
# script para eliminar la tabla de sensors de la base de datos.

# Cargando Ambiente
WORKDIR=$(dirname $0)
export $(grep -v "^#" $WORKDIR/.env | xargs)

echo "==============================================================="
echo "RECREATE.SH script"
echo "Borra la tabla: SENSORS de la base de datos"
echo "==============================================================="
echo "POSTGRES_HOST: $POSTGRES_HOST:$POSTGRES_PORT"
echo "POSTGRES_DBNAME: ${POSTGRES_DBNAME}"

TABLE_TO_DROP="sensors"
echo "TABLE_TO_DROP: $TABLE_TO_DROP"

function to_lowercase() {
    tr '[:upper:]' '[:lower:]'
}

function to_uppercase() {
    tr '[:lower:]' '[:upper:]'
}

function create_table() {
    local scriptList=("schema.sql" "data.sql")
    echo "${#scripts[@]}"

    for script in "${scriptList[@]}"; do
        psql -h localhost -U postgres \
         -d mydatabase < src/main/resources/${script}
    done
}

function create_backup() {
    pg_dump -h localhost -U postgres \
     -d mydatabase -t sensors -f $WORKDIR/backup/sensors-table_"$(get_current_datetime)".sql
}

function get_current_datetime_in_seconds() {
  date -j -f "%Y-%m-%dT%H:%M:%S" "$(get_current_datetime)" +%s
}

# Convertir fechas y horas a segundos desde la época Unix en macOS
function get_current_datetime() {
    date +"%Y-%m-%dT%H:%M:%S"
}

function get_more_recent_backup_file() {
  ls -ltr $WORKDIR/backup/ | grep -E '[0-9]{4}-[0-9]{2}-[0-9]{2}T[0-9]{2}:[0-9]{2}:[0-9]{2}' | grep -v "total" | head -n 1 | awk '{print $NF}'
}

function drop_table() {
  if [[ "${SPRING_PROFILES_ACTIVE}" == "dev" && "${POSTGRES_HOST}" == "localhost" ]]; then
      read -p "Estas seguro de realizar esta accion? Se eliminará la tabla: \"$(echo $TABLE_TO_DROP | to_uppercase)\" [Y/n]":  input_option
  
      process_option="$(echo $input_option | to_lowercase)"
      if [[ ${process_option} == "y" ]]; then
          psql -h ${POSTGRES_HOST} \
          -U postgres -W \
          --dbname=mydatabase -c "DROP TABLE IF EXISTS $TABLE_TO_DROP;"
      fi
  fi
}

# get_more_recent_backup_file
# create_backup && drop_table
# echo "var_tmp: $var_tmp"
