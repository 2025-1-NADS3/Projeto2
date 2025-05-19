const express = require('express')
require('dotenv').config()
const cors = require('cors')
const userRoutes = require('./routes/userRoutes')
const balanceRoutes = require('./routes/balanceRoutes')
const boletoRoutes = require('./routes/boletosRoutes')
const produtosRoutes = require('./routes/produtosRoutes')

const app = express()
const PORT = process.env.PORT || 3001

app.use(express.json())
app.use(cors())

app.use('/api', userRoutes, balanceRoutes, boletoRoutes, produtosRoutes)

// Exportar o app para testes
module.exports = app

// Iniciar o servidor apenas se não estiver em modo de teste
if (process.env.NODE_ENV !== 'test') {
  const server = app.listen(PORT, () => {
    console.log(`Servidor rodando na porta ${PORT}`)
  })
  
  module.exports = server
}