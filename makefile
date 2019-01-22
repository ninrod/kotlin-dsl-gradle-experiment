.PHONY: db backend build

up:
	docker-compose up -d

down:
	docker-compose down && docker-compose rm

backend:
	docker-compose up -d chess-backend

db:
	docker-compose up -d db

logsdb:
	docker logs -f db

build:
	gradle build
