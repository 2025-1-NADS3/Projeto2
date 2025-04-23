import request from 'supertest';
import { server } from '../index.js';
import pool from '../config/db.js';
import { faker } from '@faker-js/faker';

describe('Testes de Integração da API de Boletos', () => {
  const existingRA = '50921490'; // RA que existe no banco de dados
  let createdBoletoId;

  // Limpa o banco de dados antes e depois dos testes
  beforeAll(async () => {
    await pool.query('DELETE FROM boletos WHERE ra_aluno = $1', [existingRA]);
  });

  afterAll(async () => {
    await pool.query('DELETE FROM boletos WHERE ra_aluno = $1', [existingRA]);
    await pool.end();
    server.close();
  });

  describe('POST /api/boletos', () => {
    it('deve criar um novo boleto com dados válidos', async () => {
      // Gera um código numérico válido com 48 caracteres
      const validCodigo = Array.from({length: 48}, () => Math.floor(Math.random() * 10)).join('');
      
      const boletoData = {
        ra_aluno: existingRA,
        valor: 150.50, // Valor fixo para previsibilidade
        codigo: validCodigo,
        vencimento: faker.date.future().toISOString().split('T')[0]
      };

      const response = await request(server)
        .post('/api/boletos')
        .send(boletoData);

      console.log('Dados da Requisição de Criação:', boletoData);
      console.log('Resposta da Criação:', response.body);

      expect(response.status).toBe(201);
      expect(response.body.status).toBe(201);
      expect(response.body.message).toBe('Boleto criado com sucesso');
      
      // Atualizado para corresponder ao formato real da resposta da API
      expect(response.body.data).toMatchObject({
        ra_aluno: Number(boletoData.ra_aluno), // API retorna RA como número
        valor: boletoData.valor.toFixed(2), // API retorna valor como string com 2 decimais
        codigo: boletoData.codigo
      });

      createdBoletoId = response.body.data.boleto_id;
    });

    it('deve retornar 400 para RA inválido (tamanho errado)', async () => {
      const validCodigo = Array.from({length: 48}, () => Math.floor(Math.random() * 10)).join('');
      
      const response = await request(server)
        .post('/api/boletos')
        .send({
          ra_aluno: '123', // Tamanho inválido
          valor: 100,
          codigo: validCodigo,
          vencimento: faker.date.future().toISOString().split('T')[0]
        });

      expect(response.status).toBe(400);
      expect(response.body.message).toMatch(/RA do aluno deve ter 8 dígitos/i);
    });

    it('deve retornar 400 para código inválido (tamanho errado)', async () => {
      const response = await request(server)
        .post('/api/boletos')
        .send({
          ra_aluno: existingRA,
          valor: 100,
          codigo: '123', // Tamanho inválido
          vencimento: faker.date.future().toISOString().split('T')[0]
        });

      expect(response.status).toBe(400);
      expect(response.body.message).toMatch(/Código deve ter 48 caracteres/i);
    });

    it('deve retornar 400 para data de vencimento no passado', async () => {
      const validCodigo = Array.from({length: 48}, () => Math.floor(Math.random() * 10)).join('');
      
      const response = await request(server)
        .post('/api/boletos')
        .send({
          ra_aluno: existingRA,
          valor: 100,
          codigo: validCodigo,
          vencimento: '2000-01-01' // Data no passado
        });

      console.log('Resposta para Data Passada:', response.body);

      expect(response.status).toBe(400);
      // Atualizado para corresponder à mensagem de erro real da API
      expect(response.body.message).toMatch(/Data de vencimento deve ser no futuro/i);
    });
  });

  describe('GET /api/boletos/:raAluno', () => {
    it('deve retornar boletos para um RA existente', async () => {
      const response = await request(server)
        .get(`/api/boletos/${existingRA}`);

      expect(response.status).toBe(200);
      expect(response.body.status).toBe(200);
      expect(response.body.message).toBe('Boletos encontrados');
      expect(Array.isArray(response.body.data)).toBe(true);
    });

    it('deve retornar array vazio para RA inexistente', async () => {
      const nonExistentRA = '99999999';
      const response = await request(server)
        .get(`/api/boletos/${nonExistentRA}`);

      expect(response.status).toBe(200);
      expect(response.body.data).toEqual([]);
    });
  });
});