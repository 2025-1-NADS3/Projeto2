const express = require('express')
const router = express.Router()

const { transferirSaldo } = require('../controllers/financasController')

router.post('/financas/transferir', transferirSaldo)

module.exports = router