const fecapayDB = require('../db/db.js')
const produtoService = require('../services/produtosService.js')
const { getSaldoUser, atualizarSaldo } = require('../services/UserService.js')
const transactionService = require('../services/transactionService.js')
const { compraService } = require('../services/compraService')


exports.getComprasPorRA = async (req, res) => {
    try {
        const { ra } = req.params
        
        if (!ra || isNaN(ra)) {
            return res.status(400).json({ error: 'RA inválido' })
        }
        
        const compras = await compraService(parseInt(ra))
        
        if (!compras || compras.length === 0) {
            return res.status(404).json({ message: 'Nenhuma compra encontrada para este RA' })
        }
        
        res.status(200).json(compras)
    } catch (error) {
        console.error('Erro no controller:', error)
        res.status(500).json({ error: 'Erro interno ao buscar compras' })
    }
}

const lojasValidas = ['cardapio', 'papelaria', 'livraria', 'atletica']

exports.payment = async (req, res) => {
    const { ra } = req.params
    const { itens } = req.body

    
    if (!ra) {
        return res.status(400).json({ message: "RA não fornecido." })
    }

    if (!Array.isArray(itens) || itens.length === 0) {
        return res.status(400).json({ message: "Nenhum item fornecido." })
    }

    try {
        
        const saldoAtual = await getSaldoUser(parseInt(ra))
        if (saldoAtual === undefined) {
            return res.status(404).json({ message: "Usuário não encontrado." })
        }

        
        let totalCompra = 0
        const itensValidados = []

        for (const item of itens) {
            const { item_id, loja, quantidade } = item

            if (!lojasValidas.includes(loja)) {
                return res.status(400).json({ message: `Loja inválida: ${loja}` })
            }

            const result = await fecapayDB.query(`SELECT * FROM ${loja} WHERE id = $1`, [item_id])
            if (result.rowCount === 0) {
                return res.status(404).json({ message: `Item ${item_id} não encontrado na loja ${loja}` })
            }

            const produto = result.rows[0]
            const precoTotalItem = parseFloat(produto.preco) * quantidade
            
            if (produto.estoque !== undefined && quantidade > produto.estoque) {
                return res.status(400).json({ message: `Estoque insuficiente para o item ${produto.nome}` })
            }

            itensValidados.push({
                item_id,
                loja,
                quantidade,
                preco_unitario: parseFloat(produto.preco)
            })

            totalCompra += precoTotalItem
        }

        
        if (totalCompra > saldoAtual) {
            return res.status(400).json({ message: "Saldo insuficiente." })
        }

    
        await fecapayDB.query('BEGIN')

        try {
            
            for (const item of itensValidados) {
                await fecapayDB.query(
                    `INSERT INTO compras(item_id, ra, loja, quantidade, preco) VALUES ($1, $2, $3, $4, $5)`,
                    [item.item_id, ra, item.loja, item.quantidade, item.preco_unitario]
                )
                
                await fecapayDB.query(
                    `UPDATE ${item.loja} SET estoque = estoque - $1 WHERE id = $2`,
                    [item.quantidade, item.item_id]
                )
            }

            
            const novoSaldo = saldoAtual - totalCompra
            await atualizarSaldo(ra, novoSaldo)

            
            await fecapayDB.query('COMMIT')

            await transactionService.insertTransaction(ra, "COMPRA", totalCompra, new Date())

            return res.status(200).json({
                message: "Pagamento realizado com sucesso.",
                novoSaldo
            });

        } catch (error) {
            await fecapayDB.query('ROLLBACK')
            throw error
        }

    } catch (error) {
        console.error("Erro no pagamento:", error)
        return res.status(500).json({ 
            message: "Erro interno ao processar pagamento.",
            error: error.message 
        })
    }
}