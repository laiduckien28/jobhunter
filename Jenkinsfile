pipeline {
    agent { label 'Jenkins-Agent-01' }
    environment {
        IMAGE_NAME = 'jobhunter-frontend'
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
                    
                    docker rm -f jobhunter-frontend || true
                    
                    docker run --name jobhunter-frontend -d -p 3000:3000 $FULL_IMAGE
                '''
            }
        }

        // stage('Deploy') {
        //     steps {
        //         nodejs(nodeJSInstallationName: 'NodeJS 16.20.0') {
        //             sh 'npm install'
        //             sh 'npm run dev'
        //         }
        //     }
        // }
    }
}

