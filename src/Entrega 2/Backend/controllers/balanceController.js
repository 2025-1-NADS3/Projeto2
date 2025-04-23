const fecapayDB = require('../db/db.js')

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

exports.insertBalance