#!/usr/bin/env groovy
def mavenVersion = 'maven-3.6.3'
def javaVersion = 'Java11'

// send mail when job was successful
def sendSuccessMail(){
    mail to: "mbarek@it-2go.de", bcc: "", cc: "", from: "Jenkins@it-2go.de", replyTo: "",
    subject: "Build  ${env.JOB_NAME} done",
    body: "Your Build  ${env.JOB_NAME} #${env.BUILD_NUMBER} is successfully done."
}

// send mail when job failed
def sendErrorMail(error){
    mail to: "mbarek@it-2go.de", bcc: "", cc: "", from: "Jenkins@it-2go.de", replyTo: "",
    subject: "Build  ${env.JOB_NAME} fails",
    body: """
    Your Build  ${env.JOB_NAME} #${env.BUILD_NUMBER} fails.
    ${error}
    For details check the Job console: ${env.BUILD_URL}console"""
}

def runCommand(command){
    if(isUnix()){
        sh command
    } else {
        bat command
    }
}

node {
    ansiColor('xterm') {
         stage('Checkout') {
            echo "Checkout employees-service..."
            checkout scm
         }

         stage('Build') {
            echo "Build employees-service..."
            withMaven(jdk: javaVersion, maven: mavenVersion) {
                try{
                    runCommand('mvn clean package -DskipTests')
                } catch(exception){
                    sendErrorMail("Error occurred while building, error: " + exception.message)
                    warnError(exception.message)
                }
            }
         }

        stage('Test') {
            echo "Test employees-service..."
            withMaven(jdk: javaVersion, maven: mavenVersion) {
                try{
                    runCommand('mvn verify jacoco:report')
                } catch(exception){
                    sendErrorMail("Error occurred while testing, error: " + exception.message)
                    warnError(exception.message)
                }
            }
        }

        stage('Docker_build') {
            echo "Docker build employees-service image..."
            withMaven(jdk: javaVersion, maven: mavenVersion) {
                try{
                    runCommand('mvn docker:build')
                } catch(exception){
                    sendErrorMail("Error occurred while building docker image, error: " + exception.message)
                    warnError(exception.message)
                }
            }
        }

        stage('Docker_push') {
            echo "Docker push employees-service image..."
            withMaven(jdk: javaVersion, maven: mavenVersion) {
                try{
                    runCommand('mvn docker:push')
                } catch(exception){
                    sendErrorMail("Error occurred while pushing docker image, error: " + exception.message)
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
