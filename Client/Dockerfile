FROM node:18.13-alpine AS build

WORKDIR /app

COPY package.json package-lock.json ./
RUN npm install

COPY . .

RUN npm run build --prod

FROM httpd:alpine

COPY --from=build /app/dist/client /usr/local/apache2/htdocs/

EXPOSE 80
