# Project1-Back

## Getting Started

### Prerequisites
1. Have Docker-Compose installed.
2. Have Maven installed.

### Installation
1. Clone this repository.
   ```shell
   https://gitlab.com/profile-sevilla/practicas-22-23/project1-back.git
   ```
2. Move to the new directory.
   ```shell
   cd ./project1-back
   ```
3. Execute the command line below to set up the database docker container using docker compose.
   ```shell
   docker-compose up -d
   ```
4. Run the application.
   ```shell
   mvn spring-boot:run
   ```
   
## Database schema
We'll use a database to store users' information as well as their tasks. This information will be stored following the schema of the examples below.

### User schema
   ``` json
   {
      "_id": <ObjectId1>,
      "name": "Full Name",
      "password": "SecurePsswrd123",
      "email": "fake@example.com",
      "birthday": "31-12-2000" //Date in format 'dd-MM-yyyy'
   }
   ```

### Task schema
   ``` json
   {
      "_id": <ObjectId2>,
      "user_id": <ObjectId1>,
      "title": "Task Title",
      "description": "Description of what to do in the task.",
      "isDone": false
   }
   ```
