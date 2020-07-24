def branch = 'master'
def scmUrl = 'https://github.com/mambarek/employees-service.git'
def mavenVersion = 'maven-3.6.3'
def javaVersion = 'Java11'

def sendSuccessMail(){
    mail to: "mbarek@it-2go.de", bcc: "", cc: "", from: "Jenkins@it-2go.de", replyTo: "",
    subject: "Build  ${env.JOB_NAME} done",
    body: "Your Build  ${env.JOB_NAME} #${env.BUILD_NUMBER} is successfully done."
}

def sendErrorMail(error){
    mail to: "mbarek@it-2go.de", bcc: "", cc: "", from: "Jenkins@it-2go.de", replyTo: "",
    subject: "Build  ${env.JOB_NAME} fails",
    body: """
    Your Build  ${env.JOB_NAME} #${env.BUILD_NUMBER} fails.
    ${error}
    For details check the Job console: ${env.BUILD_URL}console"""
}

def runCommand(command){
    if(isUnix){
        sh command
    } else {
        bat command
    }
}

node {
    ansiColor('xterm') {
         stage('Checkout') {
            echo "Checkout employees-service..."
            git branch: branch, url: scmUrl
         }

         stage('Build') {
            echo "Build  employees-service..."
            //bat 'mvn package -DskipTests'
            withMaven(jdk: javaVersion, maven: mavenVersion) {
                try{
                    //bat 'mvn package -DskipTests'
                    runCommand('mvn package -DskipTests')
                } catch(exception){
                    sendErrorMail("Error occurred while building, error: " + exception.message)
                    warnError(exception.message)
                }
            }
         }

        stage('Test') {
            echo "Test  employees-service..."
            //bat 'mvn test'
            withMaven(jdk: javaVersion, maven: mavenVersion) {
                try{
                    bat 'mvn test'
                } catch(exception){
                    sendErrorMail("Error occurred while testing, error: " + exception.message)
                    warnError(exception.message)
                }
            }
        }

        stage('Notify'){
            echo "Notify contributors ..."
            sendSuccessMail()
        }

        stage('Cleanup') {
            // Delete workspace when build is done
            cleanWs()
        }
    }
}
