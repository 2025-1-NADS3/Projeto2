require("dotenv").config();

const { Pool } = require('pg')

const fecapayDB = new Pool ({
        connectionString: process.env.DATABASE_URL,
});

fecapayDB.connect()
    .then(() => console.log("Conectado ao DB"))
    .catch(error => console.error("Não conectado", error))

module.exports = fecapayDB    