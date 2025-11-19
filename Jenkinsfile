pipeline {
    agent {
        kubernetes {
            yaml '''
              apiVersion: v1
              kind: Pod
              metadata:
                name: qlack-observability
                namespace: jenkins
              spec:
                tolerations:
                - key: "jenkins"
                  operator: "Equal"
                  value: "agent"
                  effect: "NoSchedule"
                nodeSelector:
                  jenkins-agent: "true"
                priorityClassName: jenkins-low-priority
                securityContext:
                    runAsUser: 0
                    runAsGroup: 0
                    fsGroup: 0
                containers:
                - name: qlack-observability-builder
                  image: eddevopsd2/maven-java-npm-docker:mvn3.9.6-jdk21-node18-docker-npm8.0.0
                  volumeMounts:
                  - name: maven
                    mountPath: /root/.m2/
                    subPath: qlack-observability
                  tty: true
                  securityContext:
                    privileged: true
                    runAsUser: 0
                imagePullSecrets:
                - name: regcred
                volumes:
                - name: maven
                  persistentVolumeClaim:
                    claimName: maven-nfs-pvc
            '''
            workspaceVolume persistentVolumeClaimWorkspaceVolume(claimName: 'workspace-nfs-pvc', readOnly: false)
        }
    }
    options {
        disableConcurrentBuilds()
        buildDiscarder(logRotator(numToKeepStr: '10'))
    }
    stages {
        stage('Build') {
            steps {
                container (name: 'qlack-observability-builder'){
                    sh 'mvn -f spring-boot/pom.xml clean install'
                }
            }
        }                      
        stage('Sonar Analysis') {
            steps {
                container (name: 'qlack-observability-builder'){
                    withSonarQubeEnv('sonar'){
                        sh 'mvn sonar:sonar -f spring-boot/pom.xml -Dsonar.projectName=QLACK-Observability -Dsonar.host.url=${SONAR_HOST_URL} -Dsonar.token=${SONAR_GLOBAL_KEY} -Dsonar.working.directory="/tmp"'
                    }
                }
            }
        }
        stage('Produce bom.xml'){
            steps{
                container (name: 'qlack-observability-builder'){
                    sh 'mvn org.cyclonedx:cyclonedx-maven-plugin:makeAggregateBom'
                }
            }
        }
        stage('Dependency-Track Analysis'){
            steps{
                container (name: 'qlack-observability-builder'){
                    sh '''
                        echo '{"project": "c7992bff-9e73-4834-a26b-737deba30867", "bom": "'"$(cat target/bom.xml | base64 -w 0)"'"}' > payload.json
                    '''

                    sh '''
                        curl -X "PUT" ${DEPENDENCY_TRACK_URL} -H 'Content-Type: application/json' -H 'X-API-Key: '${DEPENDENCY_TRACK_API_KEY} -d @payload.json
                    '''
                }
            }
        }
    }
    post {
        changed {
            emailext subject: '$DEFAULT_SUBJECT',
                body: '$DEFAULT_CONTENT',
                to: 'qlack@eurodyn.com'
            script {
                if (currentBuild.result == 'SUCCESS') {
                    emailext subject: '$DEFAULT_SUBJECT',
                        body: '$DEFAULT_CONTENT',
                        to: 'dd74bf6f.ed.eurodyn.com@emea.teams.ms'
                }
                if (currentBuild.result == 'FAILURE') {
                    emailext subject: '$DEFAULT_SUBJECT',
                        body: '$DEFAULT_CONTENT',
                        to: 'dd74bf6f.ed.eurodyn.com@emea.teams.ms'
                }
            }
        }
    }
}