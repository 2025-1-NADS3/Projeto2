const express = require('express')
const router = express.Router()

const {getBalance, addBalance} = require('../controllers/balanceController.js')

router.get('/saldo/:ra', getBalance)
router.post('/adicionar-saldo/:ra', addBalance)

module.exports = router