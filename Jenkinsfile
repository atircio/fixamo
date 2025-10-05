pipeline {
    agent any

    tools {
        jdk "jdk-17"
        maven "maven-3.9"
    }

    environment {
        // Safe, non-sensitive values from .env
        DOCKER_COMPOSE_FILE = 'docker-compose.yml'
    }

    stages {
        // 1️⃣ Checkout code
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/atircio/fixmystreet'
            }
        }

        // 2️⃣ Load safe variables from .env
        stage('Load Env File') {
            steps {
                script {
                    def props = readProperties file: '.env'
                    env.DB_NAME = props['DB_NAME']
                    env.DB_HOST = props['DB_HOST']
                    env.DB_PORT = props['DB_PORT']
                    env.GITHUB_ENDPOINT = props['GITHUB_ENDPOINT']
                    env.APP_PORT = props['APP_PORT']
                    env.APP_NAME = props['APP_NAME']
                }
            }
        }

        // 3️⃣ Build Maven project
        stage('Build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Run Docker Compose') {
            steps {
                withCredentials([
                    usernamePassword(credentialsId: 'docker-hub-credentials', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS'),
                    usernamePassword(credentialsId: 'postgres-credentials', usernameVariable: 'DB_USERNAME', passwordVariable: 'DB_PASSWORD'),
                    string(credentialsId: 'github-token', variable: 'GITHUB_TOKEN'),
                    string(credentialsId: 'openrouter-key', variable: 'OPENROUTER_API_KEY')
                ]) {
                    sh """
                        # Build and run containers
                        docker-compose -f ${DOCKER_COMPOSE_FILE} up --build -d

                        # Pass secrets as environment variables
                        export DB_USERNAME=$DB_USERNAME
                        export DB_PASSWORD=$DB_PASSWORD
                        export DOCKER_USER=$DOCKER_USER
                        export DOCKER_PASS=$DOCKER_PASS
                        export GITHUB_TOKEN=$GITHUB_TOKEN
                        export OPENROUTER_API_KEY=$OPENROUTER_API_KEY
                    """
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    docker.build("${env.DOCKER_HUB_REPO}:${env.BUILD_NUMBER}")
                }
            }
        }

        stage('Push to Docker Hub') {
            steps {
                withCredentials([usernamePassword(
                    credentialsId: 'docker-hub-credentials',
                    usernameVariable: 'DOCKER_USER',
                    passwordVariable: 'DOCKER_PASS')]) {

                    sh '''
                        echo $DOCKER_PASS | docker login -u $DOCKER_USER --password-stdin
                        docker push ${DOCKER_HUB_REPO}:${BUILD_NUMBER}
                        docker tag ${DOCKER_HUB_REPO}:${BUILD_NUMBER} ${DOCKER_HUB_REPO}:latest
                        docker push ${DOCKER_HUB_REPO}:latest
                    '''
                }
            }
        }
    }

    post {
        always {
            // Stop and remove containers after pipeline
            sh 'docker-compose down'
        }
    }
}
