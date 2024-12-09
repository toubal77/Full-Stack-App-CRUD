FROM node:18.13-alpine AS build

WORKDIR /app
COPY package.json package-lock.json ./
RUN npm install
COPY . .
RUN npm run build
FROM nginx:alpine
COPY --from=build /app/dist/client /usr/share/nginx/html
CMD ["nginx", "-g", "daemon off;"]
EXPOSE 80
