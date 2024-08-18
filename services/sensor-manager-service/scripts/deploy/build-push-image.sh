#!/bin/bash
#Construye una imagen docker y la agrega a un registry para almacenar la imagen generada.

WORKDIR=$(dirname $0)
export $(grep -v "^#" $WORKDIR/.env | xargs)

source scripts/utils.sh

# Nombre y Versión del script
SCRIPT_NAME="Build Docker Image"; VERSION="0.0.1"

# Tags para el versionamiento de la imagen
TAGS=("1.0.0" "latest")

# Crear un banner usando figlet
figlet "$SCRIPT_NAME"
echo "Version: $VERSION"
echo
echo "==================================================================================="
echo "Bienvenido al script para construir imagenes docker." | toUpperCase
echo
echo "Docker_Registry: http://localhost:5000/"
echo "Name_Image: ${IMAGE_NAME}                               Perfil: ${PROFILES_ACTIVE}"
echo "Tags: ['1.0.0', 'latest']                     Dependencies: ['Dockerfile', '.env']"
echo "==================================================================================="
echo
echo "Preciona CTRL + C para cancelar."


# Función que se ejecutará cuando se presione CTRL + C
cleanup() {
    echo "\n"
    echo "Se ha recibido SIGINT (CTRL + C). Saliendo  del script..."
    # Aquí puedes realizar cualquier acción de limpieza, como cerrar conexiones o eliminar archivos temporales
    exit 1
}

# Configura el manejador de la señal SIGINT para ejecutar la función cleanup
trap cleanup SIGINT

# Funcionalidades encapsuladas del script

# Corre las pruebas con gradle y construye el ejecutable JAR
function run_tests_build_jar() {
    ./gradlew clean build
}

function build_images() {
   docker build \
    -t localhost:5000/"${IMAGE_NAME}":${TAGS[0]} \
    -t localhost:5000/"${IMAGE_NAME}":${TAGS[1]} .
}

function push_images() {
    for TAG in "${TAGS[@]}"; do
        docker push localhost:5000/"${IMAGE_NAME}":"${TAG}"
    done
}

function list_images() {
    echo "localhost:5000/"${IMAGE_NAME}":${TAGS[0]}"
    echo "localhost:5000/"${IMAGE_NAME}":${TAGS[1]}"
}

# Punto principal de script: agrupa las funciones
function main_function() {
    echo
    echo "[Creando Imagen]............................................................ [status: on process]"
    echo

    run_tests_build_jar && 
    build_images && 
    push_images && 
    list_images
}

# Captura y procesamiento de parametros de entrada:
# Variable para la opción "-y"
AUTO_CONFIRM=false

# Procesar los argumentos del script
while getopts "y" opt; do
  case $opt in
    y)
      AUTO_CONFIRM=true
      ;;
    *)
      echo "Opción no válida: -$OPTARG" >&2
      exit 1
      ;;
  esac
done

# Validacion y toma de decisiones en funcion de los parametros de entrada:
if  $AUTO_CONFIRM; then
    main_function
else
    read -p "Deseas continuar con la construccion de la imagen? para continuar ingresa [Y/n] y presiona enter: " input_option
    echo
    sleep 1
    if [[ $(echo "$input_option" | toUpperCase) == "Y"  ]];then
        main_function
    fi
fi



# echo "Compilando y ejecutando pruebas del proyecto...........................................................[STATUS: IN PROCCESS]"
# sleep 2
#   ./gradlew clean build && 
# echo "Compilando y ejecutando pruebas del proyecto..................................................................[FINISHED: OK]"

# echo
# echo "Construyendo la imagen.................................................................................[STATUS: IN PROCCESS]"
# sleep 2
#     build_images &&
# echo "Construyendo la imagen........................................................................................[FINISHED: OK]"
# echo

# echo "Subiendo imagen al Docker Registry.....................................................................[STATUS: IN PROCCESS]"
# sleep 2
#  push_images && 
# echo "Subiendo imagen al Docker Registry.....................................................................[STATUS: IN PROCCESS]"
# echo

# echo "Listando imagenes creadas..............................................................................[STATUS: IN PROCCESS]"
# sleep 2
# list_images
# echo "Listando imagenes creadas.....................................................................................[FINISHED: OK]"
