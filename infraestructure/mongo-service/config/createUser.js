db = db.getSiblingDB("trackingsystem"); // Cambiar a 'myDatabase'
db.createUser({
  user: "usertest",
  pwd: "usertest",
  roles: [{ role: "readWrite", db: "trackingsystem" }],
});
db.myCollection.insertOne({ name: "InitialData", value: 100 });


