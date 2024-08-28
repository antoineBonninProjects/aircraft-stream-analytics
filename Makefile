.PHONY: build

build:
	@mvn clean package;
	@docker build -t abonnin33/aircraft-stream-analytics:latest .;

upload:
	@$(MAKE) build;
	@/bin/bash -c 'set -a; source .envrc; set +a; echo "$$DOCKER_PASSWORD" | docker login -u "$$DOCKER_USERNAME" --password-stdin';
	@docker push abonnin33/aircraft-stream-analytics:latest;