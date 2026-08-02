pipeline {
    agent any
    tools {
        maven 'Maven_3.9.16'
        allure 'Allure_2.35.2'
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

    }

    post {
        always {
            // Stop Selenium Grid even if tests fail
            bat(returnStatus: true, script: 'docker compose down')

            // Publish Allure report
            allure(
                commandline: 'Allure_2.35.2',
                includeProperties: false,
                jdk: '',
                results: [[path: 'target/allure-results']]
            )
        }
    }
}