const express = require('express')
require('dotenv').config()
const cors = require('cors')
const app = express()
const userRoutes = require('./routes/userRoutes')
const balanceRoutes = require('./routes/balanceRoutes')
const boletoRoutes = require('./routes/boletosRoutes')
const produtosRoutes = require('./routes/produtosRoutes')
const comprasRoutes = require('./routes/comprasRoutes')
const transactionsRoutes = require('./routes/transactionRoutes')
const financasRoutes = require('./routes/financasRoutes')


const PORT = process.env.PORT || 3001

app.use(express.json())
app.use(cors())

app.use('/api', userRoutes, balanceRoutes, boletoRoutes, produtosRoutes, comprasRoutes, transactionsRoutes, financasRoutes)


app.listen(PORT, () =>{
    console.log(`Servidor rodando na porta ${PORT}`)
})

