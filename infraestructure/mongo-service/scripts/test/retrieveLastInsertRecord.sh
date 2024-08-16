#!/bin/bash
# Obtiene el registro de temperatura mas reciente insertado en la base

mongosh "mongodb://usertest:usertest@localhost:27017/trackingsystem" --quiet --eval "JSON.stringify(db.sensor_data.find().sort({timestamp: -1}).limit(1).toArray())"