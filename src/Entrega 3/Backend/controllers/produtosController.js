const fecapayDB = require('../db/db.js')
const produtoService = require('../services/produtosService.js')

exports.getProductsBySection = async(req, res) =>{
    const {loja} = req.params

    if(!loja){
        return res.status(400).json({message: "Parâmetros inválidos."})
    }

    try{
        const itens = await produtoService.getProducts(loja)
        if (!itens || itens.length === 0) {
            return res.status(404).json({ message: "Nenhum produto encontrado na seção especificada." })
        }

        return res.status(200).json({ produtos: itens })

    }catch(error){
        console.error("Erro ao obter produtos:", error)
        return res.status(500).json({ message: "Erro interno no servidor." })
    }
}