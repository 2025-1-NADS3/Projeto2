const fecapayDB  = require('../db/db.js')

const tabelas = ['cardapio', 'livraria', 'papelaria', 'atletica'];



exports.getProducts = async(table) =>{
    try{
        if (!tabelas.includes(table)) {
            throw new Error('Nome de tabela inválido.');
        }
    
        const query = `SELECT * FROM ${table}`
        const result = await fecapayDB.query(query)
        return result.rows
    }catch(error){
        console.error('Erro ao buscar produtos:', error);
        throw new Error('Erro ao acessar o banco de dados.');
    }
}