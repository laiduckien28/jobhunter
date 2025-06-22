pipeline {
    agent { label 'Jenkins-Agent-02' }


    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Deploy') {
            steps {
                nodejs(nodeJSInstallationName: 'NodeJS 16.20.0') {
                    sh 'npm install'
                    sh 'npm run dev'
                }
            }
        }
    }
}

