API := job-portal
UI  := job-portal-ui

.PHONY: help db-up db-down db-logs api-build api-run api-test ui-install ui-build ui-dev build

.DEFAULT_GOAL := help

help:
	@echo "Database (Docker Compose in $(API)):"
	@echo "  make db-up      Start Postgres"
	@echo "  make db-down    Stop Postgres"
	@echo "  make db-logs    Tail Postgres logs"
	@echo ""
	@echo "Backend (Gradle in $(API)):"
	@echo "  make api-build  Build JAR"
	@echo "  make api-run    Run Spring Boot (port 8082)"
	@echo "  make api-test   Run tests"
	@echo ""
	@echo "Frontend (npm in $(UI)):"
	@echo "  make ui-install Install dependencies"
	@echo "  make ui-build   Production build"
	@echo "  make ui-dev     Dev server (Vite)"
	@echo ""
	@echo "  make build      Build backend + UI"

db-up:
	docker compose -f $(API)/compose.yaml up -d

db-down:
	docker compose -f $(API)/compose.yaml down -v

db-logs:
	docker compose -f $(API)/compose.yaml logs -f

api-build:
	cd $(API) && ./gradlew build

api-run:
	cd $(API) && ./gradlew bootRun

api-test:
	cd $(API) && ./gradlew test

ui-install:
	cd $(UI) && npm install

ui-build:
	cd $(UI) && npm run build

ui-dev:
	cd $(UI) && npm run dev

build: api-build ui-build
