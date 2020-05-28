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
                script{
                    try {
                        sh 'mvn -B -DskipTests clean package'
                    }
                    catch(Exception e){
                        bat 'mvn -B -DskipTests clean package'
                    }
                }
            }
        }
        stage ('Test-Maven'){
            steps{
                script{
                    try{
                        sh 'mvn test'
                    }
                    catch(Exception e){
                        bat 'mvn test'
                    }
                }

            }
        }
    }
}