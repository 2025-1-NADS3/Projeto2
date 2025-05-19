require("dotenv").config();
const { Pool } = require("pg");
const fs = require("fs");

let config

if (process.env.DATABASE_URL) {
    console.log("Conectado Localmente")
    config = {
        connectionString: process.env.DATABASE_URL,
        ssl: process.env.NODE_ENV === "production"
            ? {
                  rejectUnauthorized: false,
                  ca: process.env.SSL_CERT ? fs.readFileSync(process.env.SSL_CERT) : undefined
              }
            : false
    }
} else {
    console.log("Conectado na azure")
    config = {
        user: process.env.DB_USER,
        host: process.env.DB_HOST,
        database: process.env.DB_NAME,
        password: process.env.DB_PASSWORD,
        port: process.env.DB_PORT || 5432,
        ssl: process.env.NODE_ENV === "production"
            ? {
                  rejectUnauthorized: false,
                  ca: process.env.SSL_CERT ? fs.readFileSync(process.env.SSL_CERT) : undefined
              }
            : false
    }
}

const fecapayDB = new Pool(config)

fecapayDB.connect()
    .then(() => console.log("✅ Conectado ao banco de dados!"))
    .catch(error => console.error("❌ Erro ao conectar:", error))

module.exports = fecapayDB
