.PHONY: db backend build

build:
	gradle build

up:
	docker-compose up -d

down:
	docker-compose down && docker-compose rm

backend:
	docker-compose up -d backend

db:
	docker-compose up -d db

logsdb:
	docker logs -f db

pristine:
	git clean -fdx

run:
	DATABASE_ADDRESS=db java -jar build/libs/backend-0.0.1.jar
