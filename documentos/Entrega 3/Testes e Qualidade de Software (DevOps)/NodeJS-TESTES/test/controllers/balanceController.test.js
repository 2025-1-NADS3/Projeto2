const request = require('supertest')
const app = require('../../server')
const fecapayDB = require('../../db/db')
const bcrypt = require('bcrypt')

const testUser = {
  nome: 'Balance',
  sobrenome: 'Test',
  ra: '654321',
  email: 'balance@teste.com',
  senha: 'senha123',
  saldo: '1000.00'
}

describe('Controlador de Saldo', () => {
  beforeAll(async () => {
    console.log('[SETUP] Configurando usuário e saldo para testes')
    await fecapayDB.query('DELETE FROM usuarios WHERE ra = $1', [testUser.ra])
    const hash = await bcrypt.hash(testUser.senha, 10)
    await fecapayDB.query(
      'INSERT INTO usuarios (nome, sobrenome, ra, email, senha) VALUES ($1, $2, $3, $4, $5)',
      [testUser.nome, testUser.sobrenome, testUser.ra, testUser.email, hash]
    )
    
    await fecapayDB.query('DELETE FROM creditos WHERE ra_aluno = $1', [testUser.ra])
    await fecapayDB.query(
      'INSERT INTO creditos (ra_aluno, saldo) VALUES ($1, $2)',
      [testUser.ra, testUser.saldo]
    )
  })

  afterAll(async () => {
    console.log('[CLEANUP] Removendo dados de teste')
    await fecapayDB.query('DELETE FROM creditos WHERE ra_aluno = $1', [testUser.ra])
    await fecapayDB.query('DELETE FROM usuarios WHERE ra = $1', [testUser.ra])
  })

  describe('GET /api/saldo/:ra', () => {
    it('deve retornar saldo do usuário', async () => {
      console.log('[TESTE] Buscando saldo do usuário')
      const response = await request(app)
        .get(`/api/saldo/${testUser.ra}`)

      console.log('[RESPOSTA]', {
        status: response.status,
        saldo: response.body.saldo
      })

      expect(response.status).toBe(200)
      expect(response.body.saldo).toBe(testUser.saldo)
    })

    it('deve retornar 404 para RA inválido', async () => {
      console.log('[TESTE] Testando RA inválido')
      const response = await request(app)
        .get('/api/saldo/000000')

      console.log('[RESPOSTA]', {
        status: response.status,
        mensagem: response.body.message
      })

      expect(response.status).toBe(404)
      expect(response.body).toHaveProperty('message', 'Dados não encontrados.')
    })
  })

  describe('POST /api/adicionar-saldo/:ra', () => {
    it('deve adicionar saldo corretamente', async () => {
      const valorAdicionar = 500
      console.log(`[TESTE] Adicionando R$${valorAdicionar} ao saldo`)
      
      const response = await request(app)
        .post(`/api/adicionar-saldo/${testUser.ra}`)
        .send({ saldo: valorAdicionar })

      console.log('[RESPOSTA]', {
        status: response.status,
        novoSaldo: response.body.novoSaldo
      })

      expect(response.status).toBe(200)
      expect(response.body).toHaveProperty('message', 'Saldo adicionado com sucesso.')
      expect(parseFloat(response.body.novoSaldo)).toBe(parseFloat(testUser.saldo) + valorAdicionar)
    })

    it('não deve adicionar saldo com valores inválidos', async () => {
      console.log('[TESTE] Testando valores inválidos')
      const testes = [
        { saldo: -100, desc: 'valor negativo' },
        { saldo: 'abc', desc: 'string inválida' },
        { saldo: 0, desc: 'zero' }
      ]

      for (const teste of testes) {
        console.log(`[TESTE] Tentando adicionar ${teste.desc}: ${teste.saldo}`)
        const response = await request(app)
          .post(`/api/adicionar-saldo/${testUser.ra}`)
          .send({ saldo: teste.saldo })

        console.log('[RESPOSTA]', {
          status: response.status,
          mensagem: response.body.message
        })

        expect(response.status).toBe(400)
        expect(response.body).toHaveProperty('message', 'Parâmetros inválidos.')
      }
    })

    it('deve retornar 404 para RA inexistente', async () => {
      console.log('[TESTE] Tentando adicionar saldo para RA inexistente')
      const response = await request(app)
        .post('/api/adicionar-saldo/000000')
        .send({ saldo: 100 })

      console.log('[RESPOSTA]', {
        status: response.status,
        mensagem: response.body.message
      })

      expect(response.status).toBe(404)
      expect(response.body).toHaveProperty('message', 'Usuário não encontrado.')
    })
  })
})