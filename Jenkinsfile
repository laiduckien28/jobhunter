pipeline {
    agent { label 'Jenkins-Agent-01' }

    tools {
        gradle 'Gradle 7.5.1'
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
                environment {
                SONAR_TOKEN = credentials('7c8788b1-34f1-4533-807c-4f4cc32b362a	')
            }
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh '''
                    gradle sonar -Dsonar.login=$SONAR_TOKEN
                    '''
                }
            }
        }

        stage('Deploy') {
            steps {
                sh 'nohup java -jar build/libs/jobhunter-0.0.1-SNAPSHOT.jar > log.txt 2>&1 &'
            }
        }
    }
}

