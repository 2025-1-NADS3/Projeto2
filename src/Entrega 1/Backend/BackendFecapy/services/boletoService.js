const Boleto = require('../models/Boletos.js')
const fecapayDB  = require('../db/db.js')

exports.gerarBoletos = async (ra) => {
    try {
        const boletos = []
        

        for (let i = 0; i < 12; i++) {
            const valor = (Math.random() * 1000 + 1300).toFixed(2)
            const data_boleto = new Date(2025, i, 10)
            const codigo = Math.floor(Math.random() * 999999)
            const vencimento = new Date(data_boleto)
            vencimento.setDate(vencimento.getDate() + 15)

            const boleto = new Boleto(
                ra,
                valor,
                codigo,
                data_boleto.toISOString().split('T')[0],
                vencimento.toISOString().split('T')[0], 
                'pendente'
            )
            boletos.push(boleto)
        }

        const query = `
        INSERT INTO boletos (ra_aluno, valor, codigo, data_boleto, vencimento, status)
        VALUES ${boletos.map((_, i) => `($${i * 6 + 1}, $${i * 6 + 2}, $${i * 6 + 3}, $${i * 6 + 4}, $${i * 6 + 5}, $${i * 6 + 6})`).join(', ')}
        `

        const values = boletos.flatMap(boleto => boleto.getInsertValues())
        await fecapayDB.query(query, values);
    
        console.log('Boletos gerados.')
    } catch (error) {
        console.error('Erro ao gerar boletos:', error)
        throw error
    }
}

