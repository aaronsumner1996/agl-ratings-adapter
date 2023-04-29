kubectl delete deployment agl-ratings-adapter -n agl-dev

kubectl delete service agl-ratings-adapter -n agl-dev

./gradlew clean build

docker build . -t agl-ratings-adapter

kubectl create -f ./k8s -n agl-dev