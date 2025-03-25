const fecapayDB = require('../db/db.js')
const User = require('../models/User')
const bcrypt = require('bcrypt')

const saltRounds = 10


exports.userSignUp = (req, res) => {
    const {nome, sobrenome, ra, email, senha} = req.body
    

    fecapayDB.query(`SELECT * FROM usuarios WHERE ra = $1 OR email = $2`, [ra, email], (error, result) =>{
        if(error){
            res.status(500).send(error)
            return
        }
        if(result.rows.length === 0){
            bcrypt.hash(senha, saltRounds, (error, hash) =>{
                if(error){
                    res.status(501).send(error)
                    return
                }
                const user = new User(nome, sobrenome, ra, email, hash)
                fecapayDB.query(user.getInsertQuery(), user.getInsertValues(),
                (error) =>{
                    if(error){
                        res.status(502).send(error)
                        return
                    }
                    return res.status(200).send({message: "Cadastrado com sucesso."})
                }
            )
            })
        }else{
            return res.status(400).send({message: "R.A ou E-mail já cadastrados."})
        }
    })
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

            const hash = bcrypt.hashSync(novaSenha, 10)
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