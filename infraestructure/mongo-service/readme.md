### Mongo-service

#### Descripcion
Este microservicio es util para encapsular un servidor MongoDB en un contenedor docker, con algunas configuraciones y accesos creados.

Para acceder al contenedor desde el exterior se pueden utilizar un cliente **mongosh**

```sh
    mongosh "mongodb://usertest:usertest@localhost:27017/"
```