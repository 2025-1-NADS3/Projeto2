const bcrypt = require('bcrypt')
const fecapayDB = require('../db/db.js')

exports.authLogin = (req, res) =>{
    const { ra, senha} = req.body

    fecapayDB.query(`SELECT * FROM usuarios WHERE ra = $1`, [ra], (error, result) => {
        if(error){
            return res.status(500).json({message: "Erro no servidor."})
        }
        if(result.rows.length > 0){
            bcrypt.compare(senha, result.rows[0].senha, (error, match) =>{
                if(error){
                    return res.status(501).json({message: "Erro ao comparar senha."})
                }

                const user = result.rows[0]

                if(match){
                    return res.status(200).json({
                        message: "Login realizado.", 
                        ra: user.ra,
                        nome: user.nome,
                        sobrenome: user.sobrenome,
                        email: user.email
                    })
                }else{
                    return res.status(401).json({message: "Senha incorreta."})
                }
            })
        }else{
            return res.status(400).json({message: "Conta não encontrada."})
        }
    })
}