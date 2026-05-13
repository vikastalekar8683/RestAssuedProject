pipeline {
    agent any

    tools {
        maven 'Maven'  // Assuming Maven is configured in Jenkins as 'Maven'
    }

    triggers {
        githubPush()
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'main', url: 'https://github.com/vikastalekar8683/RestAssuedProject.git', credentialsId: 'github-credentials'
            }
        }

        stage('Build and Test') {
            steps {
                bat 'mvn clean test'
            }
        }

        stage('Archive Test Results') {
            steps {
                junit 'target/surefire-reports/*.xml'
            }
        }
    }

    post {
        always {
            echo 'Pipeline completed.'
            junit 'target/surefire-reports/*.xml'
        }
        success {
            echo 'Tests passed!'
            emailext(
                subject: "Jenkins Build SUCCESS: ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
                body: """
                    Build Status: SUCCESS
                    
                    Job: ${env.JOB_NAME}
                    Build Number: ${env.BUILD_NUMBER}
                    Build URL: ${env.BUILD_URL}
                    
                    All tests passed successfully!
                """,
                to: '${DEFAULT_RECIPIENTS}',
                mimeType: 'text/plain'
            )
        }
        failure {
            echo 'Tests failed!'
            emailext(
                subject: "Jenkins Build FAILURE: ${env.JOB_NAME} - ${env.BUILD_NUMBER}",
                body: """
                    Build Status: FAILURE
                    
                    Job: ${env.JOB_NAME}
                    Build Number: ${env.BUILD_NUMBER}
                    Build URL: ${env.BUILD_URL}
                    
                    Please check the console output for details.
                """,
                to: '${DEFAULT_RECIPIENTS}',
                mimeType: 'text/plain'
            )
        }
    }
}
