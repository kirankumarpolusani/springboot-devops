pipeline {
   agent any

   tools {
      // Install the Maven version configured as "M3" and add it to the path.
      maven "M3"
   }

   stages {
        stage('Checkout') {
           steps{
              git 'https://github.com/kirankumarpolusani/springboot-devops.git'
           }
         }stage('Package') {
           steps{
               sh "mvn clean package -DskipTests"
           }
         }stage('Docker build') {
           steps{
               sh "docker build -t kirankumarpolusani/repo:v2.0.0 ."
           }
         }stage('Docker push') {
           steps {
               sh "docker login -u kirankumarpolusani -p 1D@ntKn@w"
               sh "docker push kirankumarpolusani/repo:v2.0.0"
           }
         }
         stage('Deploy to staging') {
             sh "docker run -d --rm -p 9999:9999 --name springboot kirankumarpolusani/repo:v2.0.0"
         }
   }
}





