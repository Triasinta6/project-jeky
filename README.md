# Jeky Project

Struktur project ini memisahkan backend dan frontend:

```text
jeky-project/
├── jeky-backend/          # Java Spring Boot API
└── jeky-admin-frontend/  # React.js Admin Web
```

## 1. Jalankan backend

Pastikan MySQL sudah jalan dan database `db_jeky` sudah ada.

```bash
cd jeky-backend
./mvnw spring-boot:run
```

Cek API:

```text
http://localhost:8080/api/health
http://localhost:8080/api/layanan
http://localhost:8080/api/orders
http://localhost:8080/api/dashboard/stats
```

## 2. Jalankan frontend React

Buka terminal baru:

```bash
cd jeky-admin-frontend
npm install
npm run dev
```

Buka:

```text
http://localhost:5173
```

## Catatan

- Backend jalan di `http://localhost:8080`.
- Frontend React jalan di `http://localhost:5173`.
- React mengambil data dari Spring Boot lewat endpoint `/api`.
- CORS backend sudah diatur untuk `localhost:5173` dan `127.0.0.1:5173`.
