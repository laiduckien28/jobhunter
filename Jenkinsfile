pipeline {
    agent {
        node {
            label 'Jenkins-Agent-01'
        }
    }

    tools {
        gradle 'Gradle 7.5.1'
    }

    stages {
        stage('Cleanup Workspace') {
            steps {
                cleanWs()
            }
        }

        stage('Checkout from SCM') {
            steps {
                checkout scmGit(
                    branches: [[name: 'main']],
                    userRemoteConfigs: [[url: 'https://github.com/laiduckien28/jobhunter.git']]
                )
            }
        }

        stage('Gradle Build') {
            steps {
                sh '''
                    chmod +x ./gradlew
                    ./gradlew clean build
                '''
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh '''
                        chmod +x ./gradlew
                        whoami
                        ./gradlew sonarqube
                    '''
                }
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                    nohup java -jar build/libs/jobhunter-0.0.1-SNAPSHOT.jar > log.txt 2>&1 &
                '''
            }
        }
    }
}
