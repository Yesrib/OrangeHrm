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
                sh 'docker-compose up -d'
                sh 'sleep 10' // Wait for grid to be ready
            }
        }

        stage('Build & Test') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'app-credentials',
                                                  usernameVariable: 'APP_USERNAME',
                                                   passwordVariable: 'APP_PASSWORD')]) {
                sh 'mvn clean install -Dexecution=remote -Dbrowser=chrome -Dgrid.url=http://localhost:4444/wd/hub'
                }
            }
        }

//         stage('Generate Allure Report') {
//             steps {
//                 sh 'mvn allure:report'
//             }
//         }
    }

    post {
        always {
            // Archive test results
            junit '**/target/surefire-reports/*.xml'

//             // Publish Allure report
//             allure([
//                 reportBuildPolicy: 'ALWAYS',
//                 results: [[path: 'allure-results']]
//             ])

            // Cleanup
            sh 'docker-compose down'
            //archiveArtifacts artifacts: '**/target/*.jar', allowEmptyArchive: true
        }

        failure {
            echo 'Tests failed - Check Allure reports'
        }
    }
}