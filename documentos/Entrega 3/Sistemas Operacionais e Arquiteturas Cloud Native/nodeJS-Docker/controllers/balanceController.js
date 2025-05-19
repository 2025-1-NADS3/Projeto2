const fecapayDB = require('../db/db.js')
const userService = require('../services/UserService.js')

exports.getBalance = async (req, res) =>{
    const ra = req.params.ra

    try{
        const result = await fecapayDB.query(`SELECT saldo FROM creditos WHERE ra_aluno = $1`, [parseInt(ra)])

        if(result.rows.length == 0){
            return res.status(404).json({
                message: "Dados não encontrados."
            })
        }
        return res.status(200).json(result.rows[0])

    }catch(error){
        console.error(error)
        return res.status(500).json({message:"Erro interno.", error: error.message})
    }
}

exports.addBalance = async (req, res) =>{
    const ra = req.params.ra
    const {saldo} = req.body

    if(!ra || !saldo || isNaN(saldo) || parseFloat(saldo) <=0){
        return res.status(400).json({message: "Parâmetros inválidos."})
    }
    try{
        const saldoAtual = await userService.getSaldoUser(ra)
        if(!saldoAtual){
            return res.status(404).json({message: "Usuário não encontrado."})   
        }
        const novoSaldo = parseFloat(saldoAtual) + parseFloat(saldo)
        
        await userService.atualizarSaldo(ra, novoSaldo)

        return res.status(200).json({message: "Saldo adicionado com sucesso.", novoSaldo})
    }catch(error){
        console.error("Erro ao adicionar saldo.", error)
        return res.status(500).json({message: "Erro interno."})
    }

}