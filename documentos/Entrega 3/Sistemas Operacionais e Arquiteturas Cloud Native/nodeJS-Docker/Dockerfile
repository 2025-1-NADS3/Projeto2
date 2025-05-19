# Instalar versao do node
FROM node:18-alpine

# Definir o diretorio de trabalho do container
WORKDIR /usr/src/app

# Copiar o package.json pra saber quais dependencias instalar
COPY package*.json ./

# Instalar as dependencias do package.json
RUN npm install

# Copiar o arquivos de codigo
COPY . .

# Expor a porta que o servidor roda
EXPOSE 3001

# Comando pra iniciar o servidor
CMD ["npm", "start"]