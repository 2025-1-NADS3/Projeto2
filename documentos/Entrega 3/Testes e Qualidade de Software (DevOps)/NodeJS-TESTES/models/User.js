class User {
    constructor(nome, sobrenome, ra, email, senha){
        this.nome = nome
        this.sobrenome = sobrenome
        this.ra = ra
        this.email = email
        this.senha = senha
    }

    getNome(){
        return this.nome
    }
    getSobrenome(){
        return this.sobrenome
    }
    getRa(){
        return this.ra
    }
    getEmail(){
        return this.email
    }
    getSenha(){
        return this.senha
    }

    getInsertQuery(){
        return `INSERT INTO usuarios
                (nome, sobrenome, ra, email, senha)
                VALUES($1, $2, $3, $4, $5)
        `
    }
    getInsertValues(){
        return[
            this.nome,
            this.sobrenome,
            this.ra,
            this.email,
            this.senha
        ]

    }

    
}


module.exports = User