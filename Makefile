.PHONY: build

build:
	@mvn clean package;

upload:
	@$(MAKE) build;
	@docker build -t abonnin33/aircraft-stream-analytics:latest .;
	@/bin/bash -c 'set -a; source .envrc; set +a; echo "$$DOCKER_PASSWORD" | docker login -u "$$DOCKER_USERNAME" --password-stdin';
	@docker push abonnin33/aircraft-stream-analytics:latest;