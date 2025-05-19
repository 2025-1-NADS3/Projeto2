// Configurações para ambiente de teste
process.env.NODE_ENV = 'test'
process.env.PORT = 0 // Usa porta aleatória para evitar conflitos

// Configura timeout maior para testes assíncronos
jest.setTimeout(15000)

// Limpa todos os mocks após cada teste
afterEach(() => {
  jest.restoreAllMocks()
  console.log('[CLEANUP] Mocks restaurados após o teste')
})

console.log('[SETUP] Configuração do ambiente de teste concluída')