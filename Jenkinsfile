pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/kirankumarpolusani/springboot-devops.git'
            }
        }
        stage('Package') {
            steps {
                sh 'mvn clean install -DskipTests'
            }
        }
        stage('Docker Build') {
            steps {
                sh 'docker build -t kirankumarpolusani/repo:v3.0.0 .'
            }
        }
        stage('Docker push') {
            steps {
                sh "docker login -u kirankumarpolusani -p 1D@ntKn@w"
                sh "docker push kirankumarpolusani/repo:v3.0.0"
            }
        }
        stage('Deploy to staging') {
            steps {
                sh "docker run -d --rm -p 8181:8181 --name springboot kirankumarpolusani/repo:v3.0.0"
            }
        }
    }
}