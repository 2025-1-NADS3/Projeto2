const express = require('express')
const router = express.Router()

const {getTransactions} = require('../controllers/transactionController')

router.get('/transacoes/:ra', getTransactions)

module.exports = router