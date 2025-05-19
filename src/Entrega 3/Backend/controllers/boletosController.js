const fecapayDB = require('../db/db.js')
const boletoService = require('../services/boletoService.js')
const userService = require('../services/UserService.js')
const transactionService = require('../services/transactionService.js')

exports.getBoletos = async(req, res) =>{
    const ra = req.params.ra

    try{
        const result = await fecapayDB.query(`SELECT * FROM boletos WHERE ra_aluno = $1`, [parseInt(ra)])

        if(result.rows.length == 0){
            return res.status(404).json({message: "Dados não encontrados."})
        }
        return res.status(200).json(result.rows)

    }catch(error){
        console.error(error)
        return res.status(500).json({message: "Erro interno.", error: error.message})
    }
}



exports.pagarBoleto = async (req, res) => {
    const { boleto_id, ra } = req.params

    if (!boleto_id || !ra) {
        return res.status(400).json({ message: "Parâmetros inválidos" })
    }

    try {
        const boleto = await boletoService.getBoletoByIdRa(boleto_id, ra)
        const saldoAtual = await userService.getSaldoUser(ra);

        if (!boleto) {
            return res.status(404).json({ message: "Boleto não encontrado" })
        }

        if (boleto.status === 'pago') {
            return res.status(400).json({ message: "Boleto já foi pago" })
        }

        if (parseFloat(saldoAtual) < parseFloat(boleto.valor)) {
            return res.status(400).json({ message: "Saldo insuficiente" })
        }

        const novoSaldo = parseFloat(saldoAtual) - parseFloat(boleto.valor)

        await boletoService.atualizarStatusBoleto(boleto_id)
        await userService.atualizarSaldo(ra, novoSaldo)
        await transactionService.insertTransaction(ra, "BOLETO", boleto.valor, new Date())

        return res.status(200).json({ message: "Boleto pago com sucesso.", novoSaldo })
    } catch (error) {
        console.error("Erro ao pagar boleto:", error)
        return res.status(500).json({ message: "Erro interno ao processar pagamento" })
    }
}
