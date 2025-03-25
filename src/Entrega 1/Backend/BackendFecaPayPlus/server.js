const express = require('express')
require('dotenv').config()
const cors = require('cors')
const app = express()
const userRoutes = require('./routes/userRoutes')

const PORT = process.env.PORT || 3001

app.use(express.json())
app.use(cors())

app.use('/api', userRoutes)


app.listen(PORT, () =>{
    console.log(`Servidor rodando na porta ${PORT}`)
})

