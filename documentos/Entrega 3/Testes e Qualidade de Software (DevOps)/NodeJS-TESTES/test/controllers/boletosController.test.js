const request = require('supertest')
const app = require('../../server')
const fecapayDB = require('../../db/db')
const bcrypt = require('bcrypt')
const { gerarBoletos } = require('../../services/boletoService')

const testUser = {
  nome: 'Boleto',
  sobrenome: 'Test',
  ra: '789012',
  email: 'boleto@teste.com',
  senha: 'senha123',
  saldo: '2000.00'
}

describe('Controlador de Boletos', () => {
  let boletoId

  beforeAll(async () => {
    console.log('[SETUP] Configurando ambiente para testes de boletos')
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
    
    await fecapayDB.query('DELETE FROM boletos WHERE ra_aluno = $1', [testUser.ra])
    await gerarBoletos(testUser.ra)
    
    const result = await fecapayDB.query(
      'SELECT boleto_id FROM boletos WHERE ra_aluno = $1 LIMIT 1',
      [testUser.ra]
    )
    boletoId = result.rows[0].boleto_id

    // Marca o primeiro boleto como pago para teste
    await fecapayDB.query(
      'UPDATE boletos SET status = $1 WHERE boleto_id = $2',
      ['pago', boletoId]
    )
  })

  afterAll(async () => {
    console.log('[CLEANUP] Limpando dados de teste de boletos')
    await fecapayDB.query('DELETE FROM boletos WHERE ra_aluno = $1', [testUser.ra])
    await fecapayDB.query('DELETE FROM creditos WHERE ra_aluno = $1', [testUser.ra])
    await fecapayDB.query('DELETE FROM usuarios WHERE ra = $1', [testUser.ra])
  })

  describe('GET /api/boletos/:ra', () => {
    it('deve listar boletos do usuário', async () => {
      console.log('[TESTE] Listando boletos do usuário')
      const response = await request(app)
        .get(`/api/boletos/${testUser.ra}`)

      console.log('[RESPOSTA]', {
        status: response.status,
        quantidade: response.body.length
      })

      expect(response.status).toBe(200)
      expect(Array.isArray(response.body)).toBe(true)
      expect(response.body.length).toBeGreaterThan(0)
    })

    it('deve retornar vazio para RA sem boletos', async () => {
      console.log('[TESTE] Testando RA sem boletos')
      const response = await request(app)
        .get('/api/boletos/000000')

      console.log('[RESPOSTA]', {
        status: response.status,
        mensagem: response.body.message
      })

      expect(response.status).toBe(404)
      expect(response.body).toHaveProperty('message', 'Dados não encontrados.')
    })
  })

  describe('PATCH /api/boletos/pagar/:boleto_id/:ra', () => {
    it('deve pagar boleto com saldo suficiente', async () => {
      console.log('[TESTE] Pagando boleto com saldo suficiente')
      const novoBoleto = await fecapayDB.query(
        'INSERT INTO boletos (ra_aluno, valor, codigo, data_boleto, vencimento, status) VALUES ($1, $2, $3, $4, $5, $6) RETURNING *',
        [testUser.ra, 500, '123456', '2025-01-01', '2025-01-16', 'pendente']
      )
      const boletoParaPagar = novoBoleto.rows[0]

      const response = await request(app)
        .patch(`/api/boletos/pagar/${boletoParaPagar.boleto_id}/${testUser.ra}`)

      console.log('[RESPOSTA]', {
        status: response.status,
        mensagem: response.body.message,
        novoSaldo: response.body.novoSaldo
      })

      expect(response.status).toBe(200)
      expect(response.body).toHaveProperty('message', 'Boleto pago com sucesso.')
    })

    it('deve recusar boleto já pago', async () => {
      console.log('[TESTE] Tentando pagar boleto já pago')
      const response = await request(app)
        .patch(`/api/boletos/pagar/${boletoId}/${testUser.ra}`)

      console.log('[RESPOSTA]', {
        status: response.status,
        mensagem: response.body.message
      })

      expect(response.status).toBe(400)
      expect(response.body).toHaveProperty('message', 'Boleto já foi pago')
    })

    it('deve recusar por saldo insuficiente', async () => {
      console.log('[TESTE] Tentando pagar boleto com saldo insuficiente')
      const novoBoleto = await fecapayDB.query(
        'INSERT INTO boletos (ra_aluno, valor, codigo, data_boleto, vencimento, status) VALUES ($1, $2, $3, $4, $5, $6) RETURNING *',
        [testUser.ra, parseFloat(testUser.saldo) + 1000, '654321', '2025-01-01', '2025-01-16', 'pendente']
      )
      const boletoIdInsuficiente = novoBoleto.rows[0].boleto_id

      const response = await request(app)
        .patch(`/api/boletos/pagar/${boletoIdInsuficiente}/${testUser.ra}`)

      console.log('[RESPOSTA]', {
        status: response.status,
        mensagem: response.body.message
      })

      expect(response.status).toBe(400)
      expect(response.body).toHaveProperty('message', 'Saldo insuficiente')
    })
  })
})