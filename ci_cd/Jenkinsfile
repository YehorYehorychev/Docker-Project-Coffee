pipeline {
  agent any

  environment {
    NETLIFY_SITE_ID = 'c9bf685c-a128-49ab-bb82-701e368414a4'
    NETLIFY_AUTH_TOKEN = credentials('netlify_token')
  }

  stages {
    stage('Build with Node 18') {
      agent {
        docker {
          image 'node:18-alpine'
          reuseNode true
          args '-u root'
        }
      }
      steps {
        sh '''
          ls -la
          node --version
          npm --version
          npm ci
          npm run build
        '''
      }
    }

    stage('Run Tests') {
      parallel {
        stage('Unit Test') {
          agent {
            docker {
              image 'node:18-alpine'
              reuseNode true
              args '-u root'
            }
          }
          steps {
            sh '''
              test -f build/index.html
              npm test
            '''
          }
          post {
            always {
              junit 'jest-results/junit.xml'
            }
          }
        }

        stage('E2E Tests - Local') {
          agent {
            docker {
              image 'mcr.microsoft.com/playwright:v1.55.0-jammy'
              reuseNode true
              args '-u root'
            }
          }
          steps {
            sh '''
              ./node_modules/.bin/serve -s build -l 3000 &
              SERVER_PID=$!
              echo "Server PID=$SERVER_PID"
              sleep 5

              npx playwright test

              rm -rf playwright-report/local
              mkdir -p playwright-report/local
              cp -r playwright-report/* playwright-report/local/ || true

              kill $SERVER_PID
            '''
          }
          post {
            always {
              publishHTML([
                allowMissing: false,
                alwaysLinkToLastBuild: true,
                keepAll: true,
                reportDir: 'playwright-report/local',
                reportFiles: 'index.html',
                reportName: 'Playwright - Local Report',
                useWrapperFileDirectly: false
              ])
              archiveArtifacts artifacts: 'playwright-report/local/**'
            }
          }
        }
      }
    }

    stage('Deploy to Staging') {
      agent {
        docker {
          image 'node:18-alpine'
          reuseNode true
          args '-u root'
        }
      }
      steps {
        sh '''
          npm install netlify-cli@20.1.1
          ./node_modules/.bin/netlify --version
          echo "Deploying to Staging. Site ID: $NETLIFY_SITE_ID"
          ./node_modules/.bin/netlify status
          ./node_modules/.bin/netlify deploy --dir=build
        '''
      }
    }
     
    stage('Deploy to Prod') {
      agent {
        docker {
          image 'node:18-alpine'
          reuseNode true
          args '-u root'
        }
      }
      steps {
        sh '''
          npm install netlify-cli@20.1.1
          ./node_modules/.bin/netlify --version
          echo "Deploying to Production. Site ID: $NETLIFY_SITE_ID"
          ./node_modules/.bin/netlify status
          ./node_modules/.bin/netlify deploy --dir=build --prod
        '''
      }
    }

    stage('Post-Deployment E2E Tests') {
      agent {
        docker {
          image 'mcr.microsoft.com/playwright:v1.55.0-jammy'
          reuseNode true
          args '-u root'
        }
      }
      environment {
        CI_ENVIRONMENT_URL = 'https://voluble-stroopwafel-6730a7.netlify.app'
      }
      steps {
        sh '''
          npx playwright test
          rm -rf playwright-report/prod
          mkdir -p playwright-report/prod
          cp -r playwright-report/* playwright-report/prod/ || true
        '''
      }
      post {
        always {
          publishHTML([
            allowMissing: false,
            alwaysLinkToLastBuild: true,
            keepAll: true,
            reportDir: 'playwright-report/prod',
            reportFiles: 'index.html',
            reportName: 'Playwright - Prod Report',
            useWrapperFileDirectly: false
          ])
          archiveArtifacts artifacts: 'playwright-report/prod/**'
        }
      }
    }
  }

  post {
    success {
      archiveArtifacts artifacts: 'build/**'
    }
  }
}