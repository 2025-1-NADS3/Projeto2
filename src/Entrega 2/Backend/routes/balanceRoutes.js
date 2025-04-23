const express = require('express')
const router = express.Router()

const {getBalance, insertBalance} = require('../controllers/balanceController.js')

router.get('/saldo/:ra', getBalance)

module.exports = router