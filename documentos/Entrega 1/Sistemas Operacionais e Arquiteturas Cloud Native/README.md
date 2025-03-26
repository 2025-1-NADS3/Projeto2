# ENTREGA 1 - Sistemas Operacionais e Arquiteturas Cloud Native

Para acessar a pasta com a primeira entrega, basta seguir o seguinte caminho:

`
src > Entrega 1 > Backend > BackendFecaPayPlus
`

Nesta pasta estará todo o conteúdo relacionado ao Backend do projeto, referente a primeira entrega.

# Sobre a entrega

Na primeira entrega referente a matéria de **Sistemas Operacionais e Arquiteturas Cloud Native**, foi desenvolvido um sistema de **CRUD** básico para gerenciar usuários. Ele foi integrado ao frontend, permitindo que os usuários consigam `criar uma conta, acessá-la, atualizar a senha e deletar a conta`. O backend foi construído visando aplicar boas práticas de desenvolvimento, para permitir que ele seja escalável e facilite a adição de novas funcionalidades para as próximas entregas. 

### Rotas

- **userSignup**

```
POST /api/cadastro
```

  - Ela permite que seja feito o cadastro de um usuário inserindo no banco de dados os parâmetros:
    
    *RA*
    
    *Nome*
    
    *Sobrenome*
    
    *E-mail*
    
    *Senha*


- **authLogin**

```
POST /api/entrar
```

  - Ela permite que o usário acesse a conta fazendo a leitura dos dados dados enviados pelo Frontend e validadando-os com os parâmetros registrados:
    
    *RA*
    
    *Senha*

- **userChangePassword**

```
PATCH /api/alterar-senha/:ra
```

  - Ela permite que o usário altere a sua senha atualizando os dados registrados:
    
    *Senha atual*
    
    *Nova senha*

- **deleteUser**

```
DELETE /api/deletar/:ra
```

  - Ela permite que os dados do usuário sejam deletados do banco de dados:
    
    *RA*

Rotas auxíliares para encontrar os usuários:

  - **getUserByRa**

```
GET /api/user/:ra
```

    - Permite a busca de um usuário no banco de dados com base o RA recebido como parâmetro:
      
      *RA*

  - **getUsers**

```
GET /api/users
```

    - Permite extrair todos os usuários cadastrados no banco de dados.

## Acessando o projeto

É necessário clonar o repositório para o seu computador e conseguir ter acesso aos arquivos. 

Para isso, basta realizar os seguintes passos:

- Criar uma pasta na área de trabalho
  
**Você pode criar pelo CMD:**

```
mkdir nome_da_pasta
```
Ou pelo modo convencional:

- Botão direito na área de trabalho
- Nova pasta
##

- Dentro dela, clique com o botão direito do mouse e abra o terminal
  
- Execute o comando:
  
```bash
git clone https://github.com/2025-1-NADS3/Projeto2 && code .
```
Ele irá clonar o repositório e abrir o Visual Studio Code.

## Inicializando o projeto

Para acessar a pasta referente ao Backend, basta abrir o terminal dentro do Visual Studio Code e digitar o comando:
```
cd ".\src\Entrega 1\Backend\BackendFecaPayPlus"
```

 


