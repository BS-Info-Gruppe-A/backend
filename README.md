<!-- ABOUT THE PROJECT -->
## About The Project

This project is a simple application built as part of our school assignment. It allows users to check the current status of the in-house counters.

<!-- GETTING STARTED -->
## Getting Started

If you want to setup the project locally, you need the following dockers.
Configs can be set in the /src/main/resources/application.local-properties.
Also you have to create a /src/main/resources/database.properties file. There is an example.
Postgres (recommended):
We are using the image: postgres:alpine with the normal postgres port 5432.
Pgadmin (recommended):
We are using the image: pgadmin4:8 

### Prerequisites

This is a list of tools you need to use and how to install them.
- [JDK 23](https://www.oracle.com/java/technologies/downloads/#java23)
- [Docker](https://docs.docker.com/get-started/get-docker/)
- [Docker Compose (Linux only)](https://docs.docker.com/compose/install/#scenario-two-install-the-docker-compose-plugin)
  
### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/BS-Info-Gruppe-A/backend.git
   cd backend
   ```
2. Generate `~/.m2/toolchains.xml` (manually copy the xml from the below command into the file)
   ```sh
   ./mvnw toolchains:generate-jdk-toolchains-xml
   ```
3. Start database server )if needed)
   ```sh
   docker compose -f dev.docker-compose.yaml up -d
   ```
   
4. Change config of service (replace `VornameNachname` with your OS username in the copied file)
   ```sh
   mkdir -p ~/bsinfo-projekt/
   cp src/main/resources/database.properties.example ~/bsinfo-projekt/database.properties
   ```

# Tasks

## Running
In order to run this project, use your [IDE](https://jetbrains.com/idea) to run the [Launcher.java](https://github.com/BS-Info-Gruppe-A/backend/blob/a543ee282f013d849f92932dcdc0e5b25a5f18a8/src/main/java/Launcher.java#L5) file.
Please note that the only supported Java Version is Java 23 with preview features enabled (`--enable-preview`)

## Running tests

To run tests execute `./mvnw test` in a POSIX compliant shell (cmd is not supported, use [PowerShell](https://learn.microsoft.com/powershell/scripting/install/installing-powershell-on-windows))

## Generating project report

To run tests execute `./mvnw site` in a POSIX compliant shell (cmd is not supported, use [PowerShell](https://learn.microsoft.com/powershell/scripting/install/installing-powershell-on-windows))
The generated report will be in `target/site/index.html`


<!-- USAGE EXAMPLES -->
## Usage

This is only a project for school requirements. There is no productive use yet.

<!-- ROADMAP -->
## Roadmap

- [ ] JDBC 
- [ ] JUnit Test 
- [ ] Rest

See the [open issues](https://github.com/BS-Info-Gruppe-A/backend/issues) for a full list of proposed features (and known issues).

Thanks for checking out the project!
