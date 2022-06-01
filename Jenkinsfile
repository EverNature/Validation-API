pipeline {
  agent any
  stages {
    stage('Compile') {
      steps {
        sh 'mvn -f evern/ clean compile'
      }
    }

    stage('Integration Test') {
      steps {
        withCredentials([string(credentialsId: 'jasypt-secret', variable: 'JASYPT')]) {
          sh 'mvn -f evern/ clean test -D jasypt.encryptor.password=${JASYPT}'
        }
      }
    }

    stage('Static Analysis') {
      steps {
        withSonarQubeEnv('EvernSonar') {
          withCredentials([string(credentialsId: 'SonarAdminToken', variable: 'SONAR_TOKEN')]) {
            sh 'mvn sonar:sonar -f evern/ -D sonar.projectKey=validationApi -D sonar.host.url=https://sonarqube.evern.eus -D sonar.java.coveragePlugin=jacoco -D sonar.login=${SONAR_TOKEN} -D maven.test.skip=true'
          }
        }
      }
    }

    stage('QualityGate') {
        steps {
            timeout(time: 10, unit: 'SECONDS') {
                waitForQualityGate abortPipeline: true, credentialsId: 'SonarAdminToken'
            }
        }
    }
  }
  
  tools {
    maven 'Maven 3.8.5'
  }
}