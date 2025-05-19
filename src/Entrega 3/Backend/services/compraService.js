const fecapayDB = require('../db/db.js');

exports.compraService = async (ra) => {
    try {
        const query = `
            SELECT 
                c.id,
                c.data,
                c.loja,
                c.quantidade,
                c.preco,
                CASE 
                    WHEN c.loja = 'cardapio' THEN card.nome
                    WHEN c.loja = 'papelaria' THEN pap.nome
                    WHEN c.loja = 'livraria' THEN liv.nome
                    WHEN c.loja = 'atletica' THEN atl.nome
                END as nome_item,
                CASE 
                    WHEN c.loja = 'cardapio' THEN card.imagem_link
                    WHEN c.loja = 'papelaria' THEN pap.imagem_link
                    WHEN c.loja = 'livraria' THEN liv.imagem_link
                    WHEN c.loja = 'atletica' THEN atl.imagem_link
                END as imagem_link
            FROM compras c
            LEFT JOIN cardapio card ON c.loja = 'cardapio' AND c.item_id = card.id
            LEFT JOIN papelaria pap ON c.loja = 'papelaria' AND c.item_id = pap.id
            LEFT JOIN livraria liv ON c.loja = 'livraria' AND c.item_id = liv.id
            LEFT JOIN atletica atl ON c.loja = 'atletica' AND c.item_id = atl.id
            WHERE c.ra = $1
            ORDER BY c.data DESC
        `
        
        const result = await fecapayDB.query(query, [ra])
        return result.rows
    } catch (error) {
        console.error('Erro ao buscar compras:', error)
        throw error
    }
}