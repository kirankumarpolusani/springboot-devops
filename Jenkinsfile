pipeline {
    stage("Package") {
         steps {
              sh "mvn clean package -DskipTests"
         }
    }stage("Docker build") {
         steps {
              sh "docker build -t kirankumarpolusani/repo:v2.0.0 ."
         }
    }stage("Docker push") {
         steps {
            sh "docker login -u kirankumarpolusani -p 1D@ntKn@w"
            sh "docker push kirankumarpolusani/repo:v2.0.0"
         }
    }
    stage("Deploy to staging") {
         steps {
              sh "docker run -d --rm -p 9999:9999 --name springboot kirankumarpolusani/repo:v2.0.0"
         }
    }
}
