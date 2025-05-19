const request = require('supertest')
const app = require('../../server')
const fecapayDB = require('../../db/db')
const bcrypt = require('bcrypt')

const testUser = {
  nome: 'Teste',
  sobrenome: 'Usuario',
  ra: '112233',
  email: 'usuario@teste.com',
  senha: 'senha123'
}

describe('Controlador de Usuários', () => {
  beforeEach(async () => {
    console.log('[SETUP] Limpando dados antes do teste')
    await fecapayDB.query('DELETE FROM usuarios WHERE ra = $1', [testUser.ra])
  })

  afterAll(async () => {
    console.log('[CLEANUP] Limpeza final após todos os testes')
    await fecapayDB.query('DELETE FROM usuarios WHERE ra = $1', [testUser.ra])
  })

  describe('POST /api/cadastro', () => {
    it('deve cadastrar novo usuário', async () => {
      console.log('[TESTE] Cadastrando novo usuário')
      const response = await request(app)
        .post('/api/cadastro')
        .send(testUser)

      console.log('[RESPOSTA]', {
        status: response.status,
        mensagem: response.body.message,
        usuario: response.body.user
      })

      expect(response.status).toBe(200)
      expect(response.body).toHaveProperty('message', 'Cadastrado com sucesso.')
      expect(response.body.user.ra.toString()).toBe(testUser.ra)
    })

    it('deve recusar cadastro duplicado', async () => {
      console.log('[TESTE] Testando cadastro duplicado')
      await request(app).post('/api/cadastro').send(testUser)

      // Teste com RA duplicado
      console.log('[TESTE] Tentando cadastrar com RA duplicado')
      const responseRA = await request(app)
        .post('/api/cadastro')
        .send({ ...testUser, email: 'outro@email.com' })

      console.log('[RESPOSTA]', {
        status: responseRA.status,
        mensagem: responseRA.body.message
      })

      expect(responseRA.status).toBe(400)
      expect(responseRA.body).toHaveProperty('message', 'Usuário já cadastrado')

      // Teste com email duplicado
      console.log('[TESTE] Tentando cadastrar com email duplicado')
      const responseEmail = await request(app)
        .post('/api/cadastro')
        .send({ ...testUser, ra: '445566' })

      console.log('[RESPOSTA]', {
        status: responseEmail.status,
        mensagem: responseEmail.body.message
      })

      expect(responseEmail.status).toBe(400)
      expect(responseEmail.body).toHaveProperty('message', 'Usuário já cadastrado')
    })
  })

  describe('PATCH /api/alterar-senha/:ra', () => {
    beforeEach(async () => {
      console.log('[SETUP] Criando usuário para teste de senha')
      const hash = await bcrypt.hash(testUser.senha, 10)
      await fecapayDB.query(
        'INSERT INTO usuarios (nome, sobrenome, ra, email, senha) VALUES ($1, $2, $3, $4, $5)',
        [testUser.nome, testUser.sobrenome, testUser.ra, testUser.email, hash]
      )
    })

    it('deve alterar senha com credenciais corretas', async () => {
      console.log('[TESTE] Alterando senha com credenciais válidas')
      const novaSenha = 'novaSenha123'
      const response = await request(app)
        .patch(`/api/alterar-senha/${testUser.ra}`)
        .send({ senhaAtual: testUser.senha, novaSenha })

      console.log('[RESPOSTA]', {
        status: response.status,
        mensagem: response.body.message
      })

      expect(response.status).toBe(200)
      expect(response.body).toHaveProperty('message', 'Senha alterada com sucesso.')
    })

    it('deve recusar alteração com senha atual incorreta', async () => {
      console.log('[TESTE] Tentando alterar senha com credencial incorreta')
      const response = await request(app)
        .patch(`/api/alterar-senha/${testUser.ra}`)
        .send({ senhaAtual: 'senhaerrada', novaSenha: 'novaSenha123' })

      console.log('[RESPOSTA]', {
        status: response.status,
        mensagem: response.body.message
      })

      expect(response.status).toBe(401)
      expect(response.body).toHaveProperty('message', 'Senha incorreta.')
    })

    it('deve recusar alteração para usuário não encontrado', async () => {
      console.log('[TESTE] Tentando alterar senha de usuário inexistente')
      const response = await request(app)
        .patch('/api/alterar-senha/000000')
        .send({ senhaAtual: 'qualquer', novaSenha: 'novaSenha123' })

      console.log('[RESPOSTA]', {
        status: response.status,
        mensagem: response.body.message
      })

      expect(response.status).toBe(400)
      expect(response.body).toHaveProperty('message', 'Usuário não encontrado.')
    })
  })

  describe('GET /api/user/:ra', () => {
    beforeEach(async () => {
      console.log('[SETUP] Criando usuário para teste de consulta')
      const hash = await bcrypt.hash(testUser.senha, 10)
      await fecapayDB.query(
        'INSERT INTO usuarios (nome, sobrenome, ra, email, senha) VALUES ($1, $2, $3, $4, $5)',
        [testUser.nome, testUser.sobrenome, testUser.ra, testUser.email, hash]
      )
    })

    it('deve retornar dados do usuário', async () => {
      console.log('[TESTE] Consultando dados do usuário')
      const response = await request(app)
        .get(`/api/user/${testUser.ra}`)

      console.log('[RESPOSTA]', {
        status: response.status,
        usuario: response.body
      })

      expect(response.status).toBe(200)
      expect(response.body.ra.toString()).toBe(testUser.ra)
      expect(response.body).toHaveProperty('nome', testUser.nome)
    })

    it('deve retornar 404 para usuário não encontrado', async () => {
      console.log('[TESTE] Consultando usuário inexistente')
      const response = await request(app)
        .get('/api/user/000000')

      console.log('[RESPOSTA]', {
        status: response.status,
        mensagem: response.body.message
      })

      expect(response.status).toBe(404)
      expect(response.body).toHaveProperty('message', 'Usuário não encontrado')
    })
  })

  describe('DELETE /api/deletar/:ra', () => {
    beforeEach(async () => {
      console.log('[SETUP] Criando usuário para teste de exclusão')
      const hash = await bcrypt.hash(testUser.senha, 10)
      await fecapayDB.query(
        'INSERT INTO usuarios (nome, sobrenome, ra, email, senha) VALUES ($1, $2, $3, $4, $5)',
        [testUser.nome, testUser.sobrenome, testUser.ra, testUser.email, hash]
      )
    })

    it('deve deletar usuário existente', async () => {
      console.log('[TESTE] Excluindo usuário existente')
      const response = await request(app)
        .delete(`/api/deletar/${testUser.ra}`)

      console.log('[RESPOSTA]', {
        status: response.status,
        mensagem: response.body.message,
        usuarioExcluido: response.body.deleteUser
      })

      expect(response.status).toBe(200)
      expect(response.body).toHaveProperty('message', 'Usuário deletado.')
      expect(response.body.deleteUser.ra.toString()).toBe(testUser.ra)
    })

    it('deve retornar erro para usuário não encontrado', async () => {
      console.log('[TESTE] Tentando excluir usuário inexistente')
      const response = await request(app)
        .delete('/api/deletar/000000')

      console.log('[RESPOSTA]', {
        status: response.status,
        mensagem: response.body.message
      })

      expect(response.status).toBe(400)
      expect(response.body).toHaveProperty('message', 'Usuário não encontrado')
    })
  })

  describe('GET /api/users', () => {
    beforeEach(async () => {
      console.log('[SETUP] Preparando dados para teste de listagem')
      const hash = await bcrypt.hash(testUser.senha, 10)
      await fecapayDB.query(
        'INSERT INTO usuarios (nome, sobrenome, ra, email, senha) VALUES ($1, $2, $3, $4, $5)',
        [testUser.nome, testUser.sobrenome, testUser.ra, testUser.email, hash]
      )
    })

    it('deve listar todos os usuários', async () => {
      console.log('[TESTE] Listando todos os usuários')
      const response = await request(app)
        .get('/api/users')

      console.log('[RESPOSTA]', {
        status: response.status,
        dados: response.body
      })

      expect(response.status).toBe(200)
      expect(Array.isArray(response.body.rows)).toBe(true) // Corrigido para verificar response.body.rows
      expect(response.body.rows.length).toBeGreaterThan(0)
    })
  })
})