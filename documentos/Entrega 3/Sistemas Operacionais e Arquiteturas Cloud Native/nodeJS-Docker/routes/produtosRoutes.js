const express = require('express')
const router = express.Router()

const {getProductsBySection} = require('../controllers/produtosController')

router.get('/produtos/:loja', getProductsBySection)


module.exports = router