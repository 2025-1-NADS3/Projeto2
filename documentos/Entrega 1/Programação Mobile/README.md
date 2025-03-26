# Entrega 1 - Programação Mobile 

Para acessar a pasta com a primeira entrega basta seguir o seguinte caminho.

`
src > Entrega 1 > Frontend > FecapayPlus
`

Nesta pasta estará todo o conteúdo relacionado ao Frontend do projeto, referente a primeira entrega.

## Sobre a entrega

Na primeira entrega de **Programação Mobile**, foram desenvolvidas 3 telas para interação do usuário no aplicativo.

- Tela de Login
  
  Nela o usuário insere o seu `RA e senha` para realizar o login no aplicativo, ou seguir para a página de cadastro. Ela conta com validação do preenchimento dos campos e verificação dos mesmos para liberar o botão de login e permitir o acesso.

- Tela de cadastro

  Similar a tela de login, a tela de cadastro conta com mais campos de preenchimento de informações como, `Nome, Sobrenome, RA, E-mail, Senha`. Todas as informações passam por validação para liberar o botão de cadastro.

- Tela de perfil

  Nessa tela há a apresentação de duas funcionalidades:
  
  - Modal para apagar a conta.
    
    Aqui o usuário pode realizar a operação de deletar a sua conta.
    
  - Modal para alterar a senha.
 
    Aqui o usuário preenche 3 campos para realizar a alteração de senha: `Senha atual > Nova senha > Confirmar nova senha`. Todos os campos passam por uma verificação de preenchimento.

Também foi inserido um sistema de autenticação que armazena, no **cache** da aplicação, o token fornecido pelo backend e o **RA** do usuário, permitindo que a **sessão do usuário** seja mantida e garante que a operação de **deletar** conta afete apenas o usuário atual.
    
  

## Acessando o projeto

É necessário clonar o repositório para o seu computador e conseguir ter acesso aos arquivos. 

Para isso, basta realizar os seguintes passos:

- Criar uma pasta na área de trabalho
- Dentro dela, clique com o botão direito do mouse e abra o terminal
- Execute o comando: 
```bash
git clone https://github.com/2025-1-NADS3/Projeto2
```

Após isso, todo o repositório será clonado.

- Abra o Android Studio


- Clique em **Open**


![](https://i.imgur.com/57QFNyH.png)

E procure pelo diretório onde você clonou o repositório.

*Ex: `...\Área de Trabalho\Projeto2\src\Entrega 1\Frontend\FecapayPlus`*

![](https://i.imgur.com/YqNwB6O.png)

Selecione a pasta raíz do projeto, onde há o ícone do android com o título FecapayPlus, clique em **Ok** e o projeto irá abrir.
