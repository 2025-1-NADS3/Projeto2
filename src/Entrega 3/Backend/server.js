const express = require('express')
require('dotenv').config()
const cors = require('cors')
const app = express()
const userRoutes = require('./routes/userRoutes')
const balanceRoutes = require('./routes/balanceRoutes')
const boletoRoutes = require('./routes/BoletosRoutes')

const PORT = process.env.PORT || 3001

app.use(express.json())
app.use(cors())

app.use('/api', userRoutes, balanceRoutes, boletoRoutes)


app.listen(PORT, () =>{
    console.log(`Servidor rodando na porta ${PORT}`)
})

