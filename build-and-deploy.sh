#!/bin/bash
# ================================================================
#  Chaghaf Backend · Build & Deploy Script
# ================================================================
set -e

echo "════════════════════════════════════════"
echo "  Chaghaf Backend — Build & Deploy"
echo "════════════════════════════════════════"

# Check prerequisites
command -v mvn  >/dev/null 2>&1 || { echo "Maven not found"; exit 1; }
command -v docker >/dev/null 2>&1 || { echo "Docker not found"; exit 1; }
command -v docker-compose >/dev/null 2>&1 || { echo "docker-compose not found"; exit 1; }

# Copy env file
if [ ! -f .env ]; then
  cp .env.example .env
  echo "⚠️  Created .env from .env.example — review before production!"
fi

# Build all services with Maven
echo ""
echo "📦 Building all services with Maven..."
mvn clean package -DskipTests --batch-mode

echo ""
echo "🐳 Building Docker images..."
docker-compose build --parallel

echo ""
echo "🚀 Starting infrastructure (SQL Server + Redis + Kafka)..."
docker-compose up -d sqlserver redis zookeeper kafka

echo ""
echo "⏳ Waiting for SQL Server to be ready (30s)..."
sleep 30

echo ""
echo "🚀 Starting discovery service..."
docker-compose up -d discovery-service
sleep 15

echo ""
echo "🚀 Starting all microservices..."
docker-compose up -d

echo ""
echo "════════════════════════════════════════"
echo "  ✅ Chaghaf Backend is running!"
echo "════════════════════════════════════════"
echo ""
echo "  API Gateway:      http://localhost:8080"
echo "  Eureka Dashboard: http://localhost:8761"
echo "  Auth Service:     http://localhost:8081"
echo ""
echo "  Check status: docker-compose ps"
echo "  View logs:    docker-compose logs -f [service]"
echo "════════════════════════════════════════"
