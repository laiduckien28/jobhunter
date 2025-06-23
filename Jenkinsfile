pipeline {
    agent { label 'Jenkins-Agent-01' }

    tools {
        gradle 'Gradle 7.5.1'
    }
    environment {
        IMAGE_NAME = 'jobhunter-backend'
        IMAGE_VERSION = "${BUILD_NUMBER}"
        REGISTRY_URL = '192.168.11.137:8082'
        REPO_PATH = 'repository/image-jobhunter'
        FULL_IMAGE = "${REGISTRY_URL}/${REPO_PATH}/${IMAGE_NAME}:${IMAGE_VERSION}"
    }
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh ''' 
                    whoami 
                    gradle clean build
                '''
            }
        }

        stage('SonarQube Analysis') {
            steps {
                    sh '''
                            chmod +x ./gradlew
                            ./gradlew sonar \
                            -Dsonar.projectKey=jobHunter \
                            -Dsonar.host.url=http://192.168.11.125:9000 \
                            -Dsonar.login=sqa_3d6aa516d3aa48b250e921b06ef7342e9a22e692
                    '''
                
            }
        }

        stage('Docker Build & Push') {
            steps {
                sh '''
                    echo "Building image: $FULL_IMAGE"
                    docker build -t $FULL_IMAGE .
                    
                    echo "Logging in to registry..."
                    echo 123456 | docker login $REGISTRY_URL -u admin --password-stdin
                    
                    echo "Pushing image..."
                    docker push $FULL_IMAGE
                    
                    docker rmi $FULL_IMAGE
                '''
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                    echo "Deploying image: $FULL_IMAGE"
                    docker pull $FULL_IMAGE
                    
                    docker rm -f jobhunter-backend || true
                    
                    docker run --name jobhunter-backend -d -p 8080:8080 $FULL_IMAGE
                '''
            }
        }
        
    }
}

