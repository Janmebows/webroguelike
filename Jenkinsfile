pipeline {
    agent any
    tools { 
        maven 'maven-3.6.3' 
        jdk 'jdk-14' 
    }
    stages {
        stage ('Initialize') {
            steps {
                bat '''
                    echo "PATH = %PATH%"
                    echo "M2_HOME = %M2_HOME"
                ''' 
            }
        }

        stage ('Build-Maven') {
            steps {
                echo 'Building.'
                try {
                    sh 'mvn -B -DskipTests clean package'
                }
                catch(Exception e){
                    bat 'mvn -B -DskipTests clean package'
                }
            }
        }
        stage ('Test-Maven'){
            steps{
                try{
                    sh 'mvn test'
                }
                catch(Exception e){
                    bat 'mvn test'
                }
                post{
                    always{
                        junit 'target/surefire-reports/*.xml' 
                    }
                }
            }
        }
    }
}