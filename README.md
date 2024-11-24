# **Project Management Application**

This is a full-stack project built with Spring Boot for the backend and React for the frontend. It provides a robust system for managing projects and tasks (ToDos), including the ability to export tasks to GitHub Gists.

---

## **Features**

### **Backend (Spring Boot)**
1. CRUD operations for projects and ToDos.
2. Export tasks to GitHub Gists.
3. Secure login functionality using Spring Security.
4. JWT-based session management for authentication.
5. Session expiration is configurable via `application.properties`.

### **Frontend (React)**
1. Modern UI for managing projects and tasks.
2. Integration with the backend API.
3. User-friendly forms for adding/updating projects and tasks.

---

## **Setup Instructions**

### **Backend Setup**

1. **Clone the repository:**

    ```bash
    git clone https://github.com/subin125vk/Project_Management_App.git
    cd backend
    ```

2. **Build the backend:**

    ```bash
    mvn clean install
    ```

3. **Configure application properties:**
    - Update the `application.properties` file with the following details:

    ```properties
    spring.application.name=todo-app
    server.port=8090

    # MySQL Configuration
    spring.datasource.url=jdbc:mysql://localhost:3306/todoapp?useSSL=false&serverTimezone=UTC
    spring.datasource.username=root
    spring.datasource.password=root

    # Hibernate Configuration
    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true

    # Frontend URL
    react.server.url=http://localhost:3000

    # GitHub API
    github.api.url=https://api.github.com/gists
    github.token=<YOUR_PERSONAL_ACCESS_TOKEN>

    # JWT Configuration
    jwt.expiration=86400000
    ```

4. **Run the backend:**

    ```bash
    mvn spring-boot:run
    ```

---

### **Frontend Setup**

1. **Navigate to the frontend directory:**

    ```bash
    cd frontend
    ```

2. **Install dependencies:**

    ```bash
    npm install
    ```

3. **Set up environment variables:**
    - Create a `.env` file in the frontend directory and add the following:

    ```env
    REACT_APP_API_BASE_URL=http://localhost:8090
    ```

4. **Start the React development server:**

    ```bash
    npm start
    ```

---

## **Accessing the Application**

### **Frontend**
- Visit the React application in your browser at:

    ```bash
    http://localhost:3000
    ```

### **Backend**
- Access backend APIs at:

    ```bash
    http://localhost:8090
    ```

---

## **Login Details**

### **Default Credentials**
- By default, user credentials are stored in the database. Use your registered credentials to log in.  
- No hardcoded user credentials are stored in `application.properties`.

---

### **JWT Session Management**
- JWT tokens are used for authentication, with expiration configurable in `application.properties`:

    ```properties
    jwt.expiration=86400000
    ```

- Expired sessions will require re-authentication.

---

## **Export ToDos to GitHub Gist**

1. Ensure the GitHub Personal Access Token is configured in `application.properties`.
2. Use the `/projects/{projectId}/export` endpoint to export a project's ToDos to a Gist.
3. The API will return the URL of the created Gist upon successful export.

---

## **System Requirements**

### **Backend:**
- Java 17+
- Maven
- MySQL

### **Frontend:**
- Node.js and npm

---

## **Project Structure**

### **Backend**
```bash
src/
├── main/
│   ├── java/
│   │   └── com.todo_app/
│   │       ├── config/             # Configuration files
│   │       ├── controller/         # REST Controllers
│   │       ├── entity/             # Entity classes
│   │       ├── repository/         # JPA Repositories
│   │       └── service/            # Business logic
│   └── resources/
│       ├── application.properties  # Configuration
└── test/                           # Unit and Integration tests
