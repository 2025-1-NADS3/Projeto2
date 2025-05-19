const fecapayDB = require('../db/db.js')
const transactionService = require('../services/transactionService.js')

exports.getTransactions = async(req, res) =>{
    const {ra} = req.params
    try{
        const result = await transactionService.getUserTransactions(ra)
        return res.status(200).json(result)

    }catch(error){
        console.error(error)
        return res.status(500).json({message:"Erro interno.", error: error.message})
    }
}