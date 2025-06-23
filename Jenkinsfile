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
                    sh '''
                            chmod +x ./gradlew
                            ./gradlew sonar \
                            -Dsonar.projectKey=jobHunter \
                            -Dsonar.host.url=http://192.168.11.125:9000 \
                            -Dsonar.login=sqa_3d6aa516d3aa48b250e921b06ef7342e9a22e692
                    '''
                
            }
        }

        stage('Docker build') {
            steps {
                sh ''' 
                    docker build -t 192.168.11.137:8082/repository/image-jobhunter:v1.0 .

                    docker push 192.168.11.137:8082/repository/image-jobhunter:v1.0
                '''
            }
        } 

        // stage('Deploy') {
        //     steps {
        //         sh 'nohup java -jar build/libs/jobhunter-0.0.1-SNAPSHOT.jar > log.txt 2>&1 &'
        //     }
        // }
    }
}

