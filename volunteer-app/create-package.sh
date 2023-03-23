./mvnw clean install -Dmaven.test.skip
docker rmi -f library &>/dev/null && echo 'Removed old container'
docker build -t library .

