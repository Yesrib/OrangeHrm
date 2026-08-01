pipeline {
    agent any
    tools {
        maven 'Maven_3.9.16'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Start Selenium Grid') {
            steps {
                // Using 'bat' instead of 'sh', and 'timeout' instead of 'sleep'
                bat 'docker-compose up -d'
                sleep(time: 15, unit: 'SECONDS')
            }
        }

        stage('Build & Test') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'app-credentials',
                                                  usernameVariable: 'APP_USERNAME',
                                                  passwordVariable: 'APP_PASSWORD')]) {
                    bat 'mvn clean install -Dexecution=remote -Dbrowser=chrome -Dgrid.url=http://localhost:4444/wd/hub'
                }
            }
        }

//      stage('Generate Allure Report') {
//          steps {
//              bat 'mvn allure:report'
//          }
//      }
    }

    post {
        always {
            // Cleanup on Windows
            bat 'docker-compose down'
            // archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
        }
    }
}