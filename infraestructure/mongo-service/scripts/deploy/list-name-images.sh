WORKDIR=$(dirname $0)
export $(grep -v "^#" $WORKDIR/.env | xargs)

echo $REGISTRY/$IMAGE_NAME:1.0.0
echo $REGISTRY/$IMAGE_NAME:latest