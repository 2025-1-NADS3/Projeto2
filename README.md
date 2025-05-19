# FECAP - Fundação Escola de Comércio Álvares Penteado

<p align="center">
<a href= "https://www.fecap.br/"><img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRhZPrRa89Kma0ZZogxm0pi-tCn_TLKeHGVxywp-LXAFGR3B1DPouAJYHgKZGV0XTEf4AE&usqp=CAU" alt="FECAP - Fundação de Comércio Álvares Penteado" border="0"></a>
</p>

# FecaPay+

## Equipe Rocket

## Integrantes: 
- [Felipe Oluwaseun Santos Ojo](https://www.linkedin.com/in/felipeosantosojo/)  
- [Gustavo de Souza Castro](https://www.linkedin.com/in/gustavocastro01/)
- [João Pedro Brosselin de Albuquerque Souza](https://www.linkedin.com/in/brosselindev//)
- [Marcella Santana Gonçalves Diniz Rocha](https://www.linkedin.com/in/marcella-santana-b76883262/)  
- [Thays Helyda da Silva Pontes](https://www.linkedin.com/in/thays-pontes-14663822b/)  

## Professores orientadores: 
- [Vincius Heltai Pacheco](https://www.linkedin.com/in/vheltai/)  
- [Aimar Martins Lopes](https://www.linkedin.com/in/aimarlopes/)
- [Francisco de Souza Escobar](https://www.linkedin.com/in/francisco-escobar/)
- [Jefferson de Oliveira Silva](https://www.linkedin.com/in/jefferson-o-silva/)
- [Victor Bruno Alexander Rosetti de Quiroz](https://www.linkedin.com/in/victorbarq/)  

## Descrição

<p align="center">
<img src="https://i.imgur.com/RKqzRKQ.png" alt="NOME DO JOGO" border="0">
  
 O FecaPay+ é um aplicativo inovador desenvolvido para facilitar a gestão financeira e acadêmica dos estudantes da FECAP, em parceria com a NEON. Criado para oferecer mais praticidade no dia a dia universitário, o app centraliza diversas funcionalidades em um só lugar, permitindo que os alunos tenham mais controle sobre seus gastos e compromissos acadêmicos.
<br></br>
Com o FecaPay+, os usuários podem:
- Consultar e pagar boletos diretamente pela plataforma, acompanhando seus pagamentos de maneira organizada.
- Gerenciar gastos acadêmicos, com um sistema de monitoramento que facilita diretamente na administração financeira.
- Visualizar o cardápio das cantinas da faculdade e reservar refeições antecipadamente.
- Organizar serviços e dependências da instituição, facilitando seu acesso.

 Com essa praticidade, a rotina acadêmica se torna mais fluida ao permitir que os alunos foquem no que realmente importa: seus estudos e experiências universitárias.

## 🛠 Estrutura de pastas

📁 Raiz  
├── 📁 documentos  
│   ├── 📁 Entrega 1  
│   │   ├── 📁 Programação Mobile  
│   │   ├── 📁 Sistemas Operacionais e Arquiteturas Cloud Native  
│   │   ├── 📁 Testes e Qualidade de Software (DevOps)  
│   │   └── 📁 User Experience Digital  
│   ├── 📁 Entrega 2  
│   │   ├── 📁 Programação Mobile  
│   │   ├── 📁 Sistemas Operacionais e Arquiteturas Cloud Native  
│   │   ├── 📁 Testes e Qualidade de Software (DevOps)  
│   │   └── 📁 User Experience Digital  
│   └── 📁 Entrega 3  
│       ├── 📁 Programação Mobile  
│       ├── 📁 Sistemas Operacionais e Arquiteturas Cloud Native  
│       ├── 📁 Testes e Qualidade de Software (DevOps)  
│       └── 📁 User Experience Digital  
├── 📁 imagens  
├── 📁 src  
│   ├── 📁 Entrega 1  
│   ├── 📁 Entrega 2  
│   └── 📁 Entrega 3  
└── 📄 README.md

<b>README.MD</b>: Arquivo que serve como guia e explicação geral sobre seu projeto. O mesmo que você está lendo agora.

Há 3 pastas que seguem da seguinte forma:

<b>documentos</b>: Toda a documentação estará nesta pasta.

<b>imagens</b>: Imagens do sistema.

<b>src</b>: Pasta que contém o código fonte.

## 🖥 Tecnologias utilizadas 

<b>Linguagens da programação</b>

* JavaScript
* Java

<b>Banco de dados</b>

* PostgreSQL

<b>Testes e requisições da API</b>

* Postman

<b>Frameworks e bibliotecas back-end</b>

* Node.js
* Express.js
  
<b>Design e modelagem de planejamento</b>

* Figma
* LucidChart
* StoryTribe

<b>Hospedagem</b>

* Azure Microsoft

## 📋 Pré-requisitos

Antes de começar o projeto, você deve ter instalado:

- [Android Studio](https://developer.android.com/studio) para o desenvolvimento do aplicativo.
- Um banco de dados PostgreSQL:
  - Caso utilize localmente, baixe e instale o [PostgreSQL](https://www.postgresql.org/download/).
  - Caso utilize um banco online, tenha as credenciais de acesso configuradas.
- [Postman](https://www.postman.com/) para testar a API e realizar requisições ao banco de dados.
- Um editor de código como [VSCode](https://code.visualstudio.com/) (opcional, mas recomendado para editar o backend).
- [Azure Microsoft](https://azure.microsoft.com/) para hospedar.


## 🛠 Instalação e Configuração

Este projeto consiste em uma aplicação web com foco em operações financeiras como consulta de saldo e geração de boletos, dividida entre frontend e backend. A estrutura atual prioriza o aprofundamento na explicação da API (backend) durante a instalação e configuração, pois o frontend já está completo, disponível no código-fonte (pasta 'src'), e por ter sido desenvolvido no Android Studio, segue um padrão que facilita sua adaptação e execução em diferentes máquinas, além do backend ser uma parte que exige maior refinamento no ambiente de desenvolvimento.

### 1. Criação do diretório do projeto

Ao clonar o repositório presente no Github do projeto, note que o código-fonte está presente na pasta 'src', enquanto a documentação complementar, como detalhes específicos sobre modelos de dados, rotas e estruturas estará presente em 'documentos'. Levando isso em consideração, extraia ou mova os arquivos da pasta 'src' para dentro do diretório raiz do projeto. A estrutura incial deverá seguir este padrão:
```bash
|--> fecaPay
 |--> backend
 |--> frontend
 .gitignore
 package.json
 package-lock.json
 server.js
```

### 2. Inicializando o backend

O backend está estruturado em:
* routes/ - Define as rotas da API.
* controllers/ - Gerencia fluxo de dados.
* services/ - Comunicação com o banco.
* models/ - Define a estrutura dos objetos utilizados.
* db/ - Contém a configuração da conexão com o banco de dados.

Com a estrutura de pastas já pronta, acesse a pasta 'backend' e instale as dependências necessárias a partir do seguinte comando:
```bash
cd backend
npm install
```

E então, com as dependências necessárias já instaladas, inicie o servidor local:
```bash
npm start
```

### 3. Configuração do banco de dados (PostgreSQL)

Para configurar corretamente o banco de dados usado no projeto, envolve:
1. A instalação do PostgreSQL.
2. Criação de um banco de dados (nome de exemplo para a explicação: 'fecapaydb').
3. Execução de scripts de tabelas criadas presente na pasta 'documentos/'.

Importante ressaltar que se deve ter o registro das credenciais do banco de dados no arquivo de conexão ao banco, 'db.js'.
```bash
const { Pool } = require('pg');

const fecapayDB = new Pool({
  user: 'usuariox',
  host: 'localhost',
  database: 'fecapaydb',
  password: 'senhay',
  port: 1234
});

module.exports = fecapayDB;
```

### 4. Teste da API com o Postman

As rotas estão divididas em:
* userRoutes.js - Cadastro, login e manipulação de usuários.
* balanceRoutes.js - Consulta para saldo.
* boletosRoutes.js - Geração e pagamento de boletos.

Utilizando o Postman, é possível enviar requisições de cada rota. A documentação necessária para cada uma delas varia, documentada na pasta 'documentos/'.

### 5. Hospedagem na nuvem com o Azure

Para disponibilizar a API online, foi usado o Azure Microsoft (Azure CLI). Ao instalar o programa, e realizar o login deve-se:
1. Criar um App Service.
2. Realizar um deploy do código por meio do Github.
3. Configurar credenciais de ambiente pelo portal do Azure.

Com isso, a API pode ser acessada pela internet, e o projeto passa a utilizar infraestrutura em nuvem.

## 🖼 Figma do projeto

<p><a href="https://www.figma.com/design/3EUICH2gmXXtA9upUI7JB2/Mobile-Project?node-id=0-1&p=f" target="_blank">Link das telas prototipadas no Figma</a></p>
<p><a href="https://www.figma.com/design/I34gstvHDYJQAMmjKTVHtq/Prot%C3%B3tipo-FecaPay--Mockup?node-id=2-2" target="_blank">Link do Mockup</a></p>

## 🗃 Histórico de lançamentos
A cada atualização os detalhes devem ser lançados aqui.
* 0.3.2 - 18/05/2025
  * Entrega final do projeto.
* 0.3.1 - 16/05/2025
  * Demonstração de produtos disponibilizados na faculdade inserida.
  * Banner da apresentação concluído.
  * Último requerimento do relatório de UX.
* 0.3.0 - 14/05/2025
  * Funcionalidades do app funcionando.
  * Tela de adição de novo crédito.
* 0.2.4 - 22/04/2025
  * Testes realizados.
  * Funcionalidade de crédito implementada.
  * Função Pagar Boleto completa.
* 0.2.3 - 21/04/2025
  * Menu feito.
  * Página principal (Home) feita.
* 0.2.2 - 16/04/2025
  * Banco de dados na Azure.
* 0.2.1 - 11/04/2025
  * Testes unitários e testes de integração a serem feitos definidos.
  * Mockup realizado.
* 0.2.0 - 04/04/2025
  * Diagrama entidade-relacionamento definindo principais funcionalidades feito.
* 0.1.7 - 24/03/2025
  * Diagrama do processo de qualidade realizado.
  * Storyboard realizado.
  * Fluxograma de processos realizado.
  * Figma do projeto e telas prototipadas feitos.
  * Tela de perfil implementada ao projeto.
  * Rotas de login, cadastro, autenticação e info realizadas.
* 0.1.6 - 22/03/2025
  * Backend do projeto concluído.
  * Ligação do backend com o frontend determinada, junto do banco de dados.
* 0.1.5 - 18/03/2025
  * Frontend de login e cadastro do app realizados.
  * Começo da comunicação do app com o banco de dados.
* 0.1.4 - 16/03/2025
  * Primeira logo do projeto definida.
  * Primeiras telas a serem prototipadas no figma.    
* 0.1.3 - 02/03/2025
  * Primeiro esboço do Wireframe do projeto.
  * Definição da principal paleta de cores. 
* 0.1.2 - 26/02/2025.
  * Entrevista com o público-alvo do projeto.
  * Criação da persona.
  * Começo do banco de dados PostgreSQL.
  * Jornada do usuário realizada.
* 0.1.1 - 12/02/2025.
  * Crazy Eights realizado. 
* 0.1.0 - 10/02/2025.
  * Início do projeto. 

## 📋 Licença/License
<p xmlns:cc="http://creativecommons.org/ns#" xmlns:dct="http://purl.org/dc/terms/"><a property="dct:title" rel="cc:attributionURL" href="https://github.com/2025-1-NADS3/Projeto2">FecaPay+</a> by <a rel="cc:attributionURL dct:creator" property="cc:attributionName" href="https://www.linkedin.com/in/felipeosantosojo/, https://www.linkedin.com/in/gustavocastro01/, https://www.linkedin.com/in/brosselindev/, https://www.linkedin.com/in/marcella-santana-b76883262/, https://www.linkedin.com/in/thays-pontes-14663822b/, https://www.fecap.br/">Felipe Oluwaseun Santos Ojo, Gustavo de Souza Castro, João Pedro Brosselin de Albuquerque Souza, Marcella Santana Gonçalves Diniz Rocha, Thays Helyda da Silva Pontes, FECAP</a> is licensed under <a href="https://creativecommons.org/licenses/by/4.0/?ref=chooser-v1" target="_blank" rel="license noopener noreferrer" style="display:inline-block;">CC BY 4.0<img style="height:22px!important;margin-left:3px;vertical-align:text-bottom;" src="https://mirrors.creativecommons.org/presskit/icons/cc.svg?ref=chooser-v1" alt=""><img style="height:22px!important;margin-left:3px;vertical-align:text-bottom;" src="https://mirrors.creativecommons.org/presskit/icons/by.svg?ref=chooser-v1" alt=""></a></p>

## 🎓 Referências

Aqui estão as referências usadas no projeto.

1. <https://source.android.com/docs>
2. <https://nodejs.org/docs/latest/api>
3. <https://expressjs.com>
4. <https://neon.com.br/>
5. <https://nubank.com.br/>
