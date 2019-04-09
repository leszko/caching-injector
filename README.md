# Hazelcast Caching Injector

## Step 1: Build sample web service

	cd greet
	./gradlew build
	docker build -t <username>/greet .
	docker push <username>/greet

Make sure your Docker Hub repository is public

## Step 2: Build networking initializer

	cd init-networking
	docker build -t <username>/init-networking .
	docker push <username>/init-networking

Make sure your Docker Hub repository is public

## Step 3: Deploy without caching

Change in `deployment-initial.yaml` the Docker Hub account `leszko` to your Docker Hub account and then apply the deployment.

	kubectl apply -f deployment-initial.yaml


## Step 4: Deploy with caching

Change in `deployment-caching.yaml` the Docker Hub account `leszko` to your Docker Hub account and then apply the deployment.

	kubectl apply -f deployment-caching.yaml

