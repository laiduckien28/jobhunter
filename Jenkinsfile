pipeline {

    agent {
    node {
        label 'Jenkins-Agent-01'
    }

} 
    tools {
        gradle "Gradle 7.5.1"
    } 
    
    stages {
        stage('Cleanup WorkSpace') {
            steps {
                cleanWs()
            }
        }
        stage("Checkout from SCM") {
            steps{
                checkout scmGit(
                    branches: [[name: 'main']],
                    userRemoteConfigs: [[url: 'https://github.com/laiduckien28/jobhunter.git']])
            }
        }
        stage('Gradle Build') {
            steps {
                sh 'gradle clean build'
            }
        }
        stage(('Deploy')) {
            steps {
                sh 'java -jar jobhunter-0.0.1-SNAPSHOT.jar'
            }
        }


    }
}