import request from 'supertest';
import { server } from '../index';
import pool from '../config/db'; 
import { faker } from '@faker-js/faker';

describe('Teste de autenticação e criação de usuário', () => {
  let userData;
  let loginData;

  beforeAll(() => {
    userData = {
      ra: faker.string.numeric({ length: 8, allowLeadingZeros: false }),
      name: faker.person.firstName(),
      surname: faker.person.lastName(),
      email: faker.internet.email(),
      password: faker.internet.password({ length: 10 }),
    };

    loginData = {
      ra: userData.ra,
      password: userData.password,
    };
  });

  it('Deve criar se um usuário novo', async () => {
    const response = await request(server)
      .post('/api/user')
      .send({
        ra: userData.ra,
        name: userData.name,
        surname: userData.surname,
        email: userData.email,
        password: userData.password,
        repeat_password: userData.password,
      });

    expect(response.status).toBe(201);
    expect(response.body.message).toBe('User created successfully!');
    expect(response.body.data.toString()).toBe(userData.ra.toString());
  });

  it('deve logar um usuário com as infos corretas', async () => {
    const response = await request(server)
      .get('/api/user/login')
      .send({
        ra: loginData.ra,
        password: loginData.password,
      });

    expect(response.status).toBe(201);
    expect(response.body.message).toBe('Usuario logado com sucesso');
  });

  it('deve falhar ao criar um usuário com a senha errada', async () => {
    const incorrectLoginData = {
      ra: loginData.ra,
      password: 'incorrectPassword',
    };

    const response = await request(server)
      .get('/api/user/login')
      .send(incorrectLoginData);

    expect(response.status).toBe(200);
    expect(response.body.detail).toBe('ra ou senha incorretos');
  });

  it('deve retornar um erro se o email não for valido', async () => {
    const invalidEmailUser = {
      ra: userData.ra,
      name: userData.name,
      surname: userData.surname,
      email: 'invalid-email',
      password: userData.password,
      repeat_password: userData.password,
    };

    const response = await request(server)
      .post('/api/user')
      .send(invalidEmailUser);

    expect(response.status).toBe(400);
    expect(response.body.message).toBe('"email" must be a valid email');
  });

  it('deve retornar um erro se faltar um campo para ser preenchido', async () => {
    const missingFieldsUser = {
      ra: userData.ra,
      name: userData.name,
      password: userData.password,
      repeat_password: userData.password,
    };

    const response = await request(server)
      .post('/api/user')
      .send(missingFieldsUser);

    expect(response.status).toBe(400);
    expect(response.body.message).toBe('"surname" is required');
  });

  afterAll(async () => {
    // Close database connection
    await pool.end();

    // Close the Express server
    server.close();
  });
});
