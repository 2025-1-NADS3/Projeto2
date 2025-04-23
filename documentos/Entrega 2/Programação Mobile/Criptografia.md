## Criptografia

Foi implementado um sistema de **criptografia de senha** para aumentar a segurança da conta dos usuários.

<details>
  <summary>Requisição feita utilizando a ferramenta de testes Postman.</summary>
  
![](https://i.imgur.com/X0AL47p.png)

</details>

**Método:** `POST`

**Endpoint:** 
`
api/cadastro
`

**Corpo da requisição:**

```
{
    "nome":"Felipe",
    "sobrenome": "Santos",
    "ra": "87654321",
    "email": "felipeteste@email.com",
    "senha": "lipe12345"
}
```
**Resposta da API:**

```
{
    "user": {
        "nome": "Felipe",
        "sobrenome": "Santos",
        "ra": "87654321",
        "email": "felipeteste@email.com",
        "senha": "$2b$10$rPtDxfTGi470LVpgn9zF7OndoMhC5/NBpJ86g3rQpP2yF7b.Er7iG"
    },
    "message": "Cadastrado com sucesso."
}
```
>[!NOTE]
>A senha criptografada foi incluída na resposta da API apenas para fins ilustrativos.

