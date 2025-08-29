pipeline {
  agent { label 'macOS' }

  options { timestamps() }

  parameters {
    choice(name: 'BROWSER', choices: ['chrome', 'firefox'], description: 'Browser to run tests on')
    booleanParam(name: 'GRID_ENABLED', defaultValue: true, description: 'Run tests on Selenium Grid?')
  }

  stages {
    stage('Checkout') {
      steps {
        git branch: 'main', url: 'https://github.com/YehorYehorychev/Docker-Project-Coffee.git'
      }
    }

    stage('Start Selenium Grid') {
      when { expression { return params.GRID_ENABLED } }
      steps {
        echo "Starting Selenium Grid with docker-compose..."
        sh 'docker compose -f docker-compose.yml up -d'
        sh 'sleep 10'
      }
    }

    stage('Run tests') {
      steps {
        sh 'mvn -version'
        sh "mvn clean test -Dbrowser=${params.BROWSER} -Dselenium.grid.enabled=${params.GRID_ENABLED}"
      }
    }

    stage('Archive & Publish reports') {
      steps {
        archiveArtifacts artifacts: 'target/test-output/*.html', fingerprint: true

        publishHTML(target: [
          reportDir: 'target/test-output',
          reportFiles: 'emailable-report.html',
          reportName: 'TestNG Emailable Report',
          keepAll: true,
          alwaysLinkToLastBuild: true,
          allowMissing: false
        ])
      }
    }
  }

  post {
    always {
      script {
        if (params.GRID_ENABLED) {
          echo "Stopping Selenium Grid..."
          sh 'docker compose -f docker-compose.yml down'
        }
      }
    }
  }
}