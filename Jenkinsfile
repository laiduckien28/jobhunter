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
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh '''
                        gradle sonarqube \
                            -Dsonar.projectKey=jobHunter \
                            -Dsonar.host.url=http://192.168.11.125:9000 \
                            -Dsonar.login=tyler \
                            -Dsonar.password=123456
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

