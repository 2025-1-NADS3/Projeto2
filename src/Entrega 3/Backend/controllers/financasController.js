const fecapayDB = require('../db/db.js')
const transactionService = require('../services/transactionService.js')
const { getSaldoUser, atualizarSaldo } = require('../services/UserService.js')


exports.transferirSaldo = async (req, res) => {
    try {
        const { raOrigem, raDestino, valor } = req.body
        
        const saldoOrigem = await getSaldoUser(raOrigem)
        
        if (saldoOrigem < valor) {
            return res.status(400).json({ error: 'Saldo insuficiente' })
        }
        
        await fecapayDB.query('BEGIN')
        
        const debitarQuery = 'UPDATE creditos SET saldo = saldo - $1 WHERE ra_aluno = $2'
        const creditarQuery = 'UPDATE creditos SET saldo = saldo + $1 WHERE ra_aluno = $2'

        await fecapayDB.query(debitarQuery, [valor, raOrigem])
        await fecapayDB.query(creditarQuery, [valor, raDestino])
        
        await transactionService.insertTransaction(raOrigem, "TRANSFERENCIA", valor, new Date())
        
        await fecapayDB.query('COMMIT')
        
        res.status(200).json({ 
            message: 'Transferência realizada com sucesso'
        })
    } catch (error) {
        await fecapayDB.query('ROLLBACK')
        console.error('Erro na transferência:', error)
        res.status(500).json({ error: 'Erro ao realizar transferência' })
    }
}
