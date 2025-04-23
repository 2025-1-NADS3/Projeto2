const fecapayDB = require('../db/db.JS')
const User = require('../models/User')
const bcrypt = require('bcrypt')
const { gerarBoletos } = require('../services/boletoService')


const saltRounds = 10

exports.userSignUp = async (req, res) => {
    const {nome, sobrenome, ra, email, senha} = req.body
    
    try{
        const result = await fecapayDB.query(`SELECT * FROM usuarios WHERE ra = $1 OR email = $2`, [ra, email])

        if(result.rows.length != 0){
            return res.status(400).json({message: "Usuário já cadastrado"})
        }

        const hash = await bcrypt.hash(senha, saltRounds)

        const user = new User(nome, sobrenome, ra, email, hash)
        await fecapayDB.query(user.getInsertQuery(), user.getInsertValues())

        try{
            await gerarBoletos(ra)
        }catch(error){
            console.error("Erro ao gerar boleto, ", error)
            return res.status(500).json({message: "Usuário criado sem boletos."})
        }

        return res.status(200).json({user, message: "Cadastrado com sucesso."})
    }catch(error){
        console.error(error)
        return res.status(500).json({message: "Erro interno."})
    }
}

exports.userChangePassword = (req, res) => {
    const {ra} = req.params
    const {senhaAtual, novaSenha} = req.body

    fecapayDB.query(`SELECT senha FROM usuarios WHERE ra = $1`, [ra],(error, result) =>{
        if(error || result.rows.length === 0){
            return res.status(400).json({message: "Usuário não encontrado."})
        }
        bcrypt.compare(senhaAtual, result.rows[0].senha, (error, match) =>{
            if(!match){
                return res.status(401).json({message:"Senha incorreta."})
            }

            const hash = bcrypt.hashSync(novaSenha, saltRounds)
            fecapayDB.query(`UPDATE usuarios SET senha = $1 WHERE ra = $2`, [hash, ra], (error) =>{
                if(error){
                    return res.status(500).json({message: "Erro ao alterar senha."})
                }
                return res.status(200).json({message: "Senha alterada com sucesso."})
            })
        })
    })

}

exports.getUsers = (req, res) => {
    const getUsers = `SELECT * FROM usuarios`
    fecapayDB.query(getUsers, (error, result) =>{
        if(error){
            res.status(500).json({message: "Erro ao buscar usuários"})
            return
        }
        res.json(result)
    })
}

exports.getUserByRa = (req, res) =>{
    const { ra } = req.params

    fecapayDB.query(`SELECT * FROM usuarios WHERE ra = $1`, [ra], (error, result) =>{
        if(error){
            return res.status(500).json({message: "Erro no servidor"})
        }
        if(result.rows.length > 0){
            return res.status(200).json(result.rows[0])
        }else{
            return res.status(404).json({message: "Usuário não encontrado"})
        }
    })
}


exports.deleteUser = (req, res) => {
    const {ra} = req.params

    fecapayDB.query(`DELETE FROM usuarios WHERE ra = $1 RETURNING *`, [ra], (error, result) =>{
        if(error){
            return res.status(500).json({message: "Erro no servidor"})
        }
        if(result.rows === 0 ){
            return res.status(400).json({message:"Usuário não encontrado"})
        }
        return res.status(200).json({
            message:"Usuário deletado.",
            deleteUser: result.rows[0]
        })
        
    })
}