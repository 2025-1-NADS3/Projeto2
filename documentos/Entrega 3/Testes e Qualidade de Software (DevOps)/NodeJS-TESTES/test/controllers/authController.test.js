const request = require('supertest')
const app = require('../../server')
const fecapayDB = require('../../db/db')
const bcrypt = require('bcrypt')

const testUser = {
  nome: 'Teste',
  sobrenome: 'Auth',
  ra: '123456',
  email: 'auth@teste.com',
  senha: 'senha123'
}

describe('Controlador de Autenticação', () => {
  beforeAll(async () => {
    console.log('[SETUP] Preparando ambiente de teste - criando usuário teste')
    await fecapayDB.query('DELETE FROM usuarios WHERE ra = $1', [testUser.ra])
    const hash = await bcrypt.hash(testUser.senha, 10)
    await fecapayDB.query(
      'INSERT INTO usuarios (nome, sobrenome, ra, email, senha) VALUES ($1, $2, $3, $4, $5)',
      [testUser.nome, testUser.sobrenome, testUser.ra, testUser.email, hash]
    )
  })

  afterAll(async () => {
    console.log('[CLEANUP] Limpando dados de teste')
    await fecapayDB.query('DELETE FROM usuarios WHERE ra = $1', [testUser.ra])
  })

  describe('POST /api/entrar', () => {
    it('deve autenticar com credenciais válidas', async () => {
      console.log('[TESTE] Testando login com credenciais válidas')
      const response = await request(app)
        .post('/api/entrar')
        .send({ ra: testUser.ra, senha: testUser.senha })
      
      console.log('[RESPOSTA]', {
        status: response.status,
        body: response.body
      })

      expect(response.status).toBe(200)
      expect(response.body).toHaveProperty('message', 'Login realizado.')
      expect(response.body.ra.toString()).toBe(testUser.ra)
    })

    it('deve falhar com senha incorreta', async () => {
      console.log('[TESTE] Testando login com senha incorreta')
      const response = await request(app)
        .post('/api/entrar')
        .send({ ra: testUser.ra, senha: 'senhaerrada' })

      console.log('[RESPOSTA]', {
        status: response.status,
        body: response.body
      })

      expect(response.status).toBe(401)
      expect(response.body).toHaveProperty('message', 'Senha incorreta.')
    })

    it('deve falhar com RA inexistente', async () => {
      console.log('[TESTE] Testando login com RA inexistente')
      const response = await request(app)
        .post('/api/entrar')
        .send({ ra: '000000', senha: testUser.senha })

      console.log('[RESPOSTA]', {
        status: response.status,
        body: response.body
      })

      expect(response.status).toBe(400)
      expect(response.body).toHaveProperty('message', 'Conta não encontrada.')
    })

    it('deve retornar erro 500 em falha no servidor', async () => {
      console.log('[TESTE] Simulando erro no servidor')
      
      // Mock do erro no banco de dados
      const originalQuery = fecapayDB.query
      fecapayDB.query = jest.fn((sql, params, callback) => {
        callback(new Error('Erro simulado no banco de dados'))
      })

      const consoleErrorMock = jest.spyOn(console, 'error').mockImplementation(() => {})

      const response = await request(app)
        .post('/api/entrar')
        .send({ ra: testUser.ra, senha: testUser.senha })

      console.log('[RESPOSTA DE ERRO]', {
        status: response.status,
        body: response.body
      })

      // Restaurações
      fecapayDB.query = originalQuery
      consoleErrorMock.mockRestore()

      expect(response.status).toBe(500)
      expect(response.body).toEqual({
        message: "Erro no servidor."
      })
    }, 10000)
  })
})