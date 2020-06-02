pipeline {
   agent any

   tools {
      maven "Maven3"
   }

   stages {
      stage('Build') {
         steps {
            git 'https://github.com/StepanMelnik/MultiJvmMonitoring.git'

            withMaven(globalMavenSettingsConfig: 'b08fcc34-34b9-4e53-ba93-898a88c8fb20', jdk: 'JDK8u221', maven: 'Maven3'){
                sh "mvn --version"
                
                // Build step
                sh "mvn clean compile -B -X"
                
                // Test step
                sh "mvn test -B -X"
                
                // Verify step
                sh "mvn verify -DskipTests -B -X"

                // Publish step
                sh "mvn deploy -DskipTests -Dcheckstyle.skip -B -X"
            }
         }

         post {
            success {
               junit '**/target/surefire-reports/TEST-*.xml'
               archiveArtifacts '**/target/*.jar'
            }
         }
      }
   }
}
