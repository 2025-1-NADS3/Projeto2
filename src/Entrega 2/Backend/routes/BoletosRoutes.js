const express = require('express')
const router = express.Router()

const {getBoletos, pagarBoleto} = require('../controllers/boletosController')

router.get('/boletos/:ra', getBoletos)

router.patch('/boletos/pagar/:boleto_id/:ra', pagarBoleto)

module.exports = router