./mvnw clean install -Dmaven.test.skip
docker rmi -f account &>/dev/null && echo 'Removed old container'
docker build -t account .

