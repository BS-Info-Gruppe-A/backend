<!-- ABOUT THE PROJECT -->
## About The Project

Here's a blank template to get started. To avoid retyping too much info, do a search and replace with your text editor for the following: `github_username`, `repo_name`, `twitter_handle`, `linkedin_username`, `email_client`, `email`, `project_title`, `project_description`, `project_license`

<p align="right">(<a href="#readme-top">back to top</a>)</p>



### Built With

* [![Java][java.java]][https://www.java.com/de/]
* [![Docker][docker]][https://www.docker.com/]


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
* maven
  ```sh
  sudo apt-get install maven
  ```
* docker
  ```sh
  sudo apt-get install docker
  ```
  
### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/BS-Info-Gruppe-A/backend.git
   ```
2. Install all packages
   ```sh
   apt-get install docker maven
   ```
3. Setup your dockers (cli or dockerDesktop)
   ```sh
   docker run...
   ```
   
4. Change config of dockers
   ```sh
   vim /src/main/resources/database.properties
   ```


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