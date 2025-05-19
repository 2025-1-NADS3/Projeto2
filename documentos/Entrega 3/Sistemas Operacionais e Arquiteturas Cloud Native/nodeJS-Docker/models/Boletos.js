class Boleto {
    constructor(ra_aluno, valor, codigo, data_boleto, vencimento, status){
        this.ra_aluno = ra_aluno
        this.valor = valor
        this.codigo = codigo
        this.data_boleto = data_boleto
        this.vencimento = vencimento
        this.status = status
    }

    getInsertQuery(){
        return `INSERT INTO boletos (ra_aluno, valor, codigo, data_boleto, vencimento, status) VALUES ($1, $2, $3, $4, $5, $6)
        `
    }

    getInsertValues(){
        return[
            this.ra_aluno,
            this.valor,
            this.codigo,
            this.data_boleto,
            this.vencimento,
            this.status
        ]
    }
}

module.exports = Boleto