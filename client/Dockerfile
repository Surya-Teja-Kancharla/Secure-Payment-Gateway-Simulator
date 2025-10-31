# ---- Stage 1: Build React app ----
FROM node:20-alpine AS builder
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

# ---- Stage 2: Serve using lightweight NGINX ----
FROM nginx:alpine
WORKDIR /usr/share/nginx/html
COPY --from=builder /app/build .
EXPOSE 3000
CMD ["nginx", "-g", "daemon off;"]
