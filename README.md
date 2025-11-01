<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Secure Payment Gateway Simulator - Project Documentation</title>
</head>
  
<body>
<h1>💳 Secure Payment Gateway Simulator</h1>
<p>
A <b>3-tier finance simulation project</b> inspired by Mastercard’s architecture — built using 
<b>React (frontend)</b>, <b>Spring Boot (backend)</b>, and <b>PostgreSQL (database)</b>.
</p>
<p>
This project demonstrates how secure digital payments between users and merchants are authorized, logged, 
and displayed — with clear separation of layers and real-world error handling.
</p>

<div class="section">
<h2>🏗️ Project Architecture</h2>
<pre>
Frontend (React + Bootstrap + Toastify)
        ↓ REST API Calls (Axios)
Backend (Spring Boot)
        ↓ JPA/Hibernate
Database (PostgreSQL via Railway)
</pre>

<h3>➕ Tech Stack</h3>
<table>
  <tr><th>Layer</th><th>Technology</th></tr>
  <tr><td>Frontend</td><td>React.js, Bootstrap, React-Toastify</td></tr>
  <tr><td>Backend</td><td>Java 22, Spring Boot 3.5, Spring Data JPA</td></tr>
  <tr><td>Database</td><td>PostgreSQL (hosted on Railway)</td></tr>
  <tr><td>Build Tools</td><td>Maven, Docker, Docker Compose</td></tr>
  <tr><td>Deployment</td><td>Render (Frontend + Backend), Railway (Database)</td></tr>
</table>
</div>

<div class="section">
<h2>⚙️ Features (Current Phase — No Login Implementation Yet)</h2>
<ul>
  <li>Simulates Mastercard-style payment processing between users and merchants</li>
  <li>Backend validates and authorizes transactions</li>
  <li>Detects declined payments (e.g., insufficient funds, amount limit, invalid card token)</li>
  <li>Real-time transaction records displayed in frontend</li>
  <li>Toast notifications and modal dialog confirmations</li>
  <li>Connected via RESTful APIs</li>
  <li>Deployed cloud-first using Render + Railway</li>
  <li>Modular 3-tier folder structure (client, server, database)</li>
</ul>
</div>

<div class="section">
<h2>📁 Folder Structure</h2>
<pre>
Secure Payment Gateway Simulator/
│
├── client/               # React Frontend
│   ├── src/
│   ├── public/
│   ├── Dockerfile
│   └── package.json
│
├── server/               # Spring Boot Backend
│   ├── src/
│   ├── pom.xml
│   ├── Dockerfile
│   └── target/
│
├── docker-compose.yml    # Multi-container setup (Frontend, Backend, PostgreSQL)
└── README.md
</pre>
</div>

<div class="section">
<h2>🧩 Backend Overview</h2>
<p><b>Language:</b> Java (Spring Boot 3.5.x)</p>

<h3>Core Packages</h3>
<ul>
  <li><code>controller</code> → REST endpoints for payment and user actions</li>
  <li><code>service</code> → Core business logic (authorization, transaction creation)</li>
  <li><code>repository</code> → JPA interfaces for data persistence</li>
  <li><code>model</code> → Entities (User, Merchant, Transaction, AuditLog)</li>
  <li><code>dto</code> → Data Transfer Objects for clean API communication</li>
</ul>

<h3>Example Payment API</h3>
<h4>POST /api/payments/process</h4>
<b>Request:</b>
<pre>{
  "userId": 1,
  "merchantId": 1,
  "amount": 1500,
  "cardToken": "TOK-1111-2222"
}</pre>

<b>Response:</b>
<pre>{
  "txnId": 6,
  "transactionRef": "TXN-ad5464e1",
  "status": "AUTHORIZED",
  "message": "Payment authorized",
  "timestamp": "2025-10-29T17:22:43.2879943"
}</pre>
</div>

<div class="section">
<h2>🎨 Frontend Overview</h2>

<p><b>Built With:</b></p>
<ul>
  <li>React 18</li>
  <li>Axios</li>
  <li>React-Toastify</li>
  <li>Bootstrap 5</li>
  <li>React-Bootstrap (for modals, layout)</li>
</ul>

<h3>UI Components</h3>
<table>
<tr><th>Component</th><th>Description</th></tr>
<tr><td>PaymentForm.js</td><td>Form to initiate new payments</td></tr>
<tr><td>TransactionTable.js</td><td>Displays recent transactions</td></tr>
<tr><td>App.js</td><td>Main page with toasts, modals, and layout</td></tr>
</table>

<p>✅ Beautified UI with modals and toast notifications<br>
✅ Real-time refresh after each transaction<br>
✅ Mastercard-inspired theme (blue accents, rounded cards)</p>
</div>

<div class="section">
<h2>🗃️ Database Schema (PostgreSQL)</h2>

<table>
<tr><th>Table</th><th>Key Columns</th><th>Description</th></tr>
<tr><td>users</td><td>id, name, balance, card_token</td><td>End-user accounts</td></tr>
<tr><td>merchants</td><td>id, name, balance</td><td>Payment receivers</td></tr>
<tr><td>transactions</td><td>id, transaction_ref, user_id, merchant_id, amount, status, reason, timestamp</td><td>All transactions</td></tr>
<tr><td>audit_log</td><td>id, txn_id, event, created_at</td><td>Security audit entries</td></tr>
</table>
<p>Database hosted on <b>Railway</b>.</p>
</div>

<div class="section">
<h2>🧰 Running Locally (via Docker)</h2>

<h3>Prerequisites</h3>
<ul>
  <li>Docker & Docker Compose</li>
  <li>8 GB free disk space</li>
  <li>Port 3000 (frontend), 8080 (backend), 5433 (database)</li>
</ul>

<h3>Build & Start All Services</h3>
<pre>docker compose up -d --build</pre>

<h3>Verify Running Containers</h3>
<pre>docker compose ps</pre>

Expected:
<pre>
securepayment_postgres     Up (healthy)
securepayment_backend      Up (healthy)
securepayment_frontend     Up (healthy)
</pre>

<h3>Access:</h3>
<ul>
  <li>🌐 Frontend → <a href="https://secure-payment-gateway-simulator.onrender.com">http://localhost:3000</a></li>
  <li>⚙️ Backend → <a href="https://secure-payment-gateway-simulator-backend.onrender.com">http://localhost:8080/api/payments/transactions/all</a></li>
  <li>🗄️ Database → PostgreSQL on port <b>5433</b></li>
</ul>
</div>

<div class="section">
<h2>🌍 Deployment Setup</h2>

<h3>Backend (Render)</h3>
<ul>
<li><b>Environment:</b> Java</li>
<li><b>Build Command:</b><pre>./mvnw clean package -DskipTests</pre></li>
<li><b>Start Command:</b><pre>java -jar target/*.jar</pre></li>
<li><b>Environment Variables:</b></li>
</ul>

<pre>
SPRING_DATASOURCE_URL=jdbc:postgresql://trolley.proxy.rlwy.net:29374/railway
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=wcBdwiiONBdWHJYpQloBZEaeugyVGrrM
PORT=8080
</pre>

<h3>Frontend (Render)</h3>
<ul>
<li><b>Environment:</b> Node</li>
<li><b>Build Command:</b><pre>npm install && npm run build</pre></li>
<li><b>Start Command:</b><pre>npm start</pre></li>
<li><b>Environment Variables:</b></li>
</ul>

<pre>
REACT_APP_API_BASE_URL=https://securepaymentgateway-backend.onrender.com/api
</pre>
</div>

<div class="section">
<h2>🚫 Current Limitations</h2>
<p class="note">
🚧 <b>No Login or Authentication yet</b><br>
Users and merchants are pre-seeded in <code>data.sql</code>.<br>
No session management or JWT implemented yet.<br>
Planned future addition: role-based login and authorization.
</p>
</div>

<div class="section">
<h2>🚀 Planned Next Phase</h2>
<ul>
  <li>🔐 Implement secure login system (JWT-based)</li>
  <li>🧾 Add payment receipts and merchant dashboards</li>
  <li>📈 Visualize transaction analytics with charts</li>
  <li>🌐 Enable SSL for Railway–Render communication</li>
</ul>
</div>

<div class="section">
<h2>🧑‍💻 Author</h2>
<p>
<b>K. Surya Teja</b><br>
B.Tech CSE (AI & ML)<br>
Anil Neerukonda Institute of Technology & Sciences<br>
<i>Project under the Mastercard Finance Simulation Series</i>
</p>
</div>

<div class="section">
<h2>📜 License</h2>
<p>
This project is for <b>academic and educational purposes only</b> 
and is not intended for real financial transactions.
</p>
</div>

</body>
</html>
