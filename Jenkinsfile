def branch = 'master'
def scmUrl = 'https://github.com/mambarek/employees-service.git'
node {

    ansiColor('xterm') {
         stage('Checkout') {
                echo "Checkout employees-service..."
                git branch: branch, url: scmUrl
         }

         stage('Build') {
                echo "Build  employees-service..."
                bat 'mvn package -DskipTests'
         }

        stage('Test') {
            echo "Test  employees-service..."
            bat 'mvn test'
        }

        stage('Cleanup') {
            // Delete workspace when build is done
            cleanWs()
           //cleanWs disableDeferredWipeout: true, deleteDirs: true
        }
    }
}
