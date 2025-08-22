📚 Library Project

This is a simple library management system built using Java Servlets, JDBC, and PostgreSQL. 
The project allows adding, viewing, updating, and deleting books, as well as managing genres and book availability.

📌 **Features**

* Add new books to the library (Create)
* View books by ID or list all books (Read)
* Update existing book information (Update)
* Remove book records (Delete)
* Manage genres and availability
* Filter books by genre or availability

 🏗 **Architecture**

The project follows a Repository-Servlet architecture, ensuring a clean separation of concerns:

* Servlets (Controller): Handle HTTP requests and responses (SaveServlet, ViewByIDServlet, etc.)
* Repository (Data Access): Perform CRUD operations on the database (BookRepository.java)
* Model: Represents the Book entity and its attributes
* Interceptor / Logger: Logs key operations for debugging (@Logged)

🛠️ **Technologies**

* Language: Java 21
* Build Tool: Maven
* Database: PostgreSQL
* JDBC: Direct database interaction
* Logging: SLF4J + Lombok

🧪 **Testing**

The project can be tested manually using a web browser or tools like Postman.

🗄️ **Database Configuration**

Make sure PostgreSQL is installed and running. Create the database and tables by using initial.sql file

The application uses the following default connection settings (defined in code):

**URL**: jdbc:postgresql://localhost:5432/library

**Username**: postgres

**Password**: postgres

⚙️ **Build & Run**

The project is built with Maven.

Build
```
mvn clean install
```

Run

Deploy the WAR file to a Servlet container like Tomcat:

1. Copy the generated WAR to webapps/ of Tomcat.
2. Start the Tomcat server.
3. Access the endpoints:

/saveBookServlet — add a book

/viewByIDServlet?id={id} — view a book by ID

/updateBookServlet — update a book

/deleteBookServlet?id={id} — delete a book

🔧 **Notes**
* All SQL operations use prepared statements for security.
* Logging is done via SLF4J with @Logged interceptor.
* Book availability and genres are managed separately in book_info table.
