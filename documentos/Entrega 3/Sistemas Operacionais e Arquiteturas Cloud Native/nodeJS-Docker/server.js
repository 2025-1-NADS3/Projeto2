const express = require('express')
require('dotenv').config()
const cors = require('cors')
const app = express()
const userRoutes = require('./routes/userRoutes')
const balanceRoutes = require('./routes/balanceRoutes')
const boletoRoutes = require('./routes/boletosRoutes')
const produtosRoutes = require('./routes/produtosRoutes')


const PORT = process.env.PORT || 3001

app.use(express.json())
app.use(cors())

app.use('/api', userRoutes, balanceRoutes, boletoRoutes, produtosRoutes)

app.get('/', (req, res) => {
    const html = `
    <!DOCTYPE html>
    <html lang="pt-br">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Servidor Funcionando</title>
        <style>
            body {
                font-family: Arial, sans-serif;
                background-color: #f0f0f0;
                display: flex;
                justify-content: center;
                align-items: center;
                height: 100vh;
                margin: 0;
            }
            
            .message-container {
                background-color: #ffffff;
                padding: 30px;
                border-radius: 10px;
                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                text-align: center;
                max-width: 80%;
            }
            
            h1 {
                color: #2c3e50;
                margin-bottom: 20px;
            }
            
            p {
                color: #7f8c8d;
                font-size: 18px;
            }
            
            .highlight {
                color: #e74c3c;
                font-weight: bold;
            }
        </style>
    </head>
    <body>
        <div class="message-container">
            <h1>Servidor funcionando!</h1>
            <p>Obrigado pelas aulas, <span class="highlight">JEFF</span>!</p>
        </div>
    </body>
    </html>
    `;
    
    res.send(html);
});
app.listen(PORT, () =>{
    console.log(`Servidor rodando na porta ${PORT}`)
})

