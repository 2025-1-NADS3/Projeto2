const request = require('supertest')
const app = require('../../server')
const fecapayDB = require('../../db/db')
const bcrypt = require('bcrypt')

describe('Testes de Integração da API', () => {
  const testUser = {
    nome: 'Integracao',
    sobrenome: 'Teste',
    ra: '999999',
    email: 'integracao@teste.com',
    senha: 'senha123'
  }

  afterAll(async () => {
    console.log('[CLEANUP] Limpando dados do teste de integração')
    await fecapayDB.query('DELETE FROM boletos WHERE ra_aluno = $1', [testUser.ra])
    await fecapayDB.query('DELETE FROM creditos WHERE ra_aluno = $1', [testUser.ra])
    await fecapayDB.query('DELETE FROM usuarios WHERE ra = $1', [testUser.ra])
  })

  it('deve completar fluxo completo: cadastro -> login -> operações', async () => {
    console.log('[TESTE] Iniciando teste de fluxo completo')

    // 1. Cadastro
    console.log('[ETAPA 1] Cadastrando novo usuário')
    const cadastroResponse = await request(app)
      .post('/api/cadastro')
      .send(testUser)
    
    console.log('[RESPOSTA CADASTRO]', {
      status: cadastroResponse.status,
      body: cadastroResponse.body
    })
    expect(cadastroResponse.status).toBe(200)

    // 2. Login
    console.log('[ETAPA 2] Realizando login')
    const loginResponse = await request(app)
      .post('/api/entrar')
      .send({ ra: testUser.ra, senha: testUser.senha })
    
    console.log('[RESPOSTA LOGIN]', {
      status: loginResponse.status,
      body: loginResponse.body
    })
    expect(loginResponse.status).toBe(200)

    // 3. Adicionar saldo
    console.log('[ETAPA 3] Adicionando saldo')
    const saldoResponse = await request(app)
      .post(`/api/adicionar-saldo/${testUser.ra}`)
      .send({ saldo: 1000 })
    
    console.log('[RESPOSTA SALDO]', {
      status: saldoResponse.status,
      body: saldoResponse.body
    })
    expect(saldoResponse.status).toBe(200)

    // 4. Verificar saldo
    console.log('[ETAPA 4] Consultando saldo')
    const getSaldoResponse = await request(app)
      .get(`/api/saldo/${testUser.ra}`)
    
    console.log('[RESPOSTA CONSULTA SALDO]', {
      status: getSaldoResponse.status,
      saldo: getSaldoResponse.body.saldo
    })
    expect(getSaldoResponse.status).toBe(200)
    expect(getSaldoResponse.body.saldo).toBe('1000.00')

    // 5. Ver boletos
    console.log('[ETAPA 5] Listando boletos')
    const boletosResponse = await request(app)
      .get(`/api/boletos/${testUser.ra}`)
    
    console.log('[RESPOSTA BOLETOS]', {
      status: boletosResponse.status,
      quantidade: boletosResponse.body.length
    })
    expect(boletosResponse.status).toBe(200)
    expect(boletosResponse.body.length).toBeGreaterThan(0)
  })
})