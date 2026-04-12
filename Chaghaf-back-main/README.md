# Chaghaf Backend — Microservices Architecture

> Spring Boot 3.3.5 · Java 21 · SQL Server · Docker · Firebase FCM

## 🏗️ Architecture

```
React Native App (Port 8080 uniquement)
         │
         ▼
┌─────────────────────────────────────┐
│          API GATEWAY :8080          │  ← JWT Auth + Routing
└─────────────────────────────────────┘
         │
         ▼
┌─────────────────────────────────────┐
│       EUREKA DISCOVERY :8761        │  ← Service Registry
└─────────────────────────────────────┘
         │
    ┌────┴──────────────────────────┐
    │                               │
    ▼                               ▼
┌──────────┐  ┌──────────────┐  ┌──────────────┐
│  Auth    │  │ Subscription │  │  Reservation │
│  :8081   │  │    :8082     │  │    :8083     │
└──────────┘  └──────────────┘  └──────────────┘
┌──────────┐  ┌──────────────┐  ┌──────────────┐
│ Boisson  │  │    Snack     │  │    Social    │
│  :8084   │  │    :8085     │  │    :8086     │
└──────────┘  └──────────────┘  └──────────────┘
                 ┌──────────────┐
                 │ Notification │
                 │    :8087     │  ← Firebase FCM Push
                 └──────────────┘

Infrastructure:
  SQL Server :1433   Redis :6379   Kafka :9092
```

## 📡 API Reference

All requests go through **`http://localhost:8080`** (API Gateway).

### Auth Service `/api/auth`
| Method | Endpoint | Auth | Description |
|--------|----------|------|-------------|
| POST | `/register` | ❌ | Créer un compte |
| POST | `/login` | ❌ | Connexion → JWT |
| POST | `/refresh` | ❌ | Renouveler le token |
| GET  | `/me` | ✅ | Profil utilisateur |
| PUT  | `/fcm-token` | ✅ | Mettre à jour token FCM |

### Subscription Service `/api/subscriptions`
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET  | `/active` | Abonnement actif |
| POST | `/` | Souscrire (packType + duration) |
| POST | `/renew` | Renouveler |
| POST | `/day-access` | Acheter accès journée |
| GET  | `/packs` | Liste des packs disponibles |

### Reservation Service `/api/reservations`
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET  | `/salles` | Liste des salles |
| GET  | `/` | Mes réservations |
| POST | `/` | Créer une réservation |
| DELETE | `/{id}` | Annuler |

### Boisson Service `/api/boissons`
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET  | `/` | Boissons disponibles |
| POST | `/consume` | Consommer une boisson |
| GET  | `/history` | Historique |
| GET  | `/cafe-guide` | Guide machine à café |

### Snack Service `/api/snacks`
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET  | `/catalog` | Menu snacks |
| POST | `/orders` | Passer une commande |
| GET  | `/orders` | Mes commandes |
| GET  | `/orders/{id}` | Détail commande |
| PATCH | `/orders/{id}/status` | Mettre à jour statut |

### Social Service
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET  | `/api/posts` | Fil d'actualité |
| POST | `/api/posts` | Créer un post |
| POST | `/api/posts/{id}/like` | Liker/unliker |
| POST | `/api/messages` | Envoyer un message |
| GET  | `/api/messages/{otherId}` | Conversation |

### Notification Service `/api/notifications`
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET  | `/` | Mes notifications |
| GET  | `/unread-count` | Nombre non lus |
| POST | `/mark-read` | Marquer tout comme lu |
| POST | `/send` | Envoyer (interne) |
| POST | `/broadcast` | Broadcast (admin) |

## 🚀 Installation & Déploiement

### Prérequis
- Java 21+
- Maven 3.9+
- Docker Desktop
- (Optionnel) Firebase project pour les push notifications

### Démarrage rapide

```bash
# 1. Cloner et naviguer
cd chaghaf-backend

# 2. Configurer les variables d'environnement
cp .env.example .env
# Éditer .env si nécessaire

# 3. Build + Deploy en une commande
chmod +x build-and-deploy.sh
./build-and-deploy.sh
```

### Démarrage manuel (développement)

```bash
# Infrastructure seulement
docker-compose up -d sqlserver redis kafka zookeeper

# Eureka Discovery
docker-compose up -d discovery-service

# Puis un service spécifique (ex: auth)
cd auth-service && mvn spring-boot:run
```

### Vérification
```bash
# Status de tous les services
docker-compose ps

# Logs en temps réel
docker-compose logs -f auth-service

# Eureka dashboard
open http://localhost:8761  # admin / chaghaf2025

# Test auth rapide
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"fullName":"Ahmed Benali","email":"ahmed@chaghaf.ma","password":"secret123"}'
```

## 🔥 Firebase Push Notifications

1. Créer un projet sur [Firebase Console](https://console.firebase.google.com)
2. Aller dans **Project Settings → Service Accounts**
3. Cliquer **Generate new private key**
4. Remplacer `config/firebase-service-account.json` avec le JSON téléchargé
5. Dans l'app React Native, enregistrer le token FCM via `PUT /api/auth/fcm-token`

## 🔐 Authentification

```
POST /api/auth/login → { accessToken, refreshToken }

Ajouter dans chaque requête:
Authorization: Bearer <accessToken>
```

Le Gateway valide le JWT et forward automatiquement :
- `X-User-Id` → ID utilisateur
- `X-User-Email` → Email
- `X-User-Role` → MEMBER | STAFF | ADMIN

## 📦 Variables d'environnement

| Variable | Default | Description |
|----------|---------|-------------|
| `DB_HOST` | `sqlserver` | Host SQL Server |
| `DB_PASS` | `Chaghaf@2025!` | Mot de passe SA |
| `JWT_SECRET` | (voir .env) | Clé secrète JWT |
| `EUREKA_USER` | `admin` | Login Eureka |
| `EUREKA_PASS` | `chaghaf2025` | Password Eureka |
| `FIREBASE_CREDENTIALS` | `/app/config/...` | Path JSON Firebase |

## 🗂️ Structure du projet

```
chaghaf-backend/
├── pom.xml                        # Parent POM
├── docker-compose.yml             # Stack complète
├── build-and-deploy.sh            # Script de déploiement
├── .env.example                   # Variables d'environnement
├── scripts/
│   └── init-db.sql               # Initialisation SQL Server
├── config/
│   └── firebase-service-account.json
├── discovery-service/             # Eureka Server :8761
├── api-gateway/                   # Gateway + JWT Filter :8080
├── auth-service/                  # Auth + JWT :8081
├── subscription-service/          # Packs + Day Access :8082
├── reservation-service/           # Salles :8083
├── boisson-service/               # Boissons + Guide :8084
├── snack-service/                 # Commandes food :8085
├── social-service/                # Posts + Messages :8086
└── notification-service/          # Push FCM :8087
```

---
**Chaghaf Community** · Agadir, Maroc · chaghaf.community
