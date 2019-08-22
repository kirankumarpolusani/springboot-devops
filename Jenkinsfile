node {

   stage('Clone Repository') {
       withMaven(maven: 'Maven 3') {
             dir('app') {
                git 'https://github.com/kirankumarpolusani/springboot-devops.git'
               sh 'mvn clean package -DskipTests'
               dockerCmd 'build --tag kirankumarpolusani/repo:v2.0.0 .'
             }
       }
   }

   stage('Push to Repository') {
        dir('app') {
          sh 'mvn clean package -DskipTests'
          dockerCmd 'build --tag kirankumarpolusani/repo:v2.0.0 .'
          dockerCmd 'login --username=kirankumarpolusani --password=1D@ntKn@w'
          dockerCmd 'push kirankumarpolusani/repo:v2.0.0 .'
        }
   }

}
