FROM node:18.17.1
WORKDIR /app
COPY package*.json ./
COPY . .
RUN npm install
EXPOSE 9000
CMD ["npm", "start"]
