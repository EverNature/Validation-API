pipeline {
  agent any
  stages {
    stage('Build Project') {
      steps {
        sh 'mvn clean install'
      }
    }

    stage('Static Analysis') {
      steps {
        withSonarQubeEnv('sonar-server') {
          sh 'mvn clean verify sonar:sonar -Dsonar.projectKey=evern_validation_ap'
        }
      }
    }

    stage('Quality Gate') {
      steps {
        timeout(time: 10, unit: 'SECONDS') {
          waitForQualityGate true
        }
      }
    }

    stage('Unit Test') {
      steps {
        sh 'mvn clean test'
      }
    }
  }
  
  tools {
    maven 'maven'
  }
}