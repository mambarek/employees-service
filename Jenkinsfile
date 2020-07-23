def branch = 'master'
def scmUrl = 'https://github.com/mambarek/employees-service.git'

def sendSuccessMail(){
    mail bcc: "",
    body: "Your Build  ${env.JOB_NAME} #${env.BUILD_NUMBER} is successfuly done.",
    cc: '', from: '', replyTo: '',
    subject: "Build  ${env.JOB_NAME} done",
    to: 'mbarek@it-2go.de'
}

node {
    ansiColor('xterm'){echo "It should launch the build in jenkins"}
    ansiColor('xterm') {
         stage('Checkout') {
                echo "Checkout employees-service..."
                git branch: branch, url: scmUrl
         }

         stage('Build') {
                echo "Build  employees-service..."
                //bat 'mvn package -DskipTests'
                withMaven(jdk: 'Java11', maven: 'maven-3.6.3') {
                    bat 'mvn package -DskipTests'
                }
         }

        stage('Test') {
            echo "Test  employees-service..."
            //bat 'mvn test'
            withMaven(jdk: 'Java11', maven: 'maven-3.6.3') {
                bat 'mvn test'
            }
        }

        stage('Notify'){
            echo "Notify contributer ..."
            sendSuccessMail()
        }

        stage('Cleanup') {
            // Delete workspace when build is done
            cleanWs()
        }
    }
}
