const express = require('express')
const router = express.Router()

const { getComprasPorRA, payment } = require('../controllers/comprasController')

router.post('/pagamento/:ra', payment)
router.get('/compras/:ra', getComprasPorRA)

module.exports = router