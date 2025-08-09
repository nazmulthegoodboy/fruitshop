Fruit Shop Ordering System
==========================

What is included:
- JavaFX project (package `fruitshop`) with:
  - Main.java
  - Controllers and model/DAO classes
  - FXML files and CSS (placed in the same package folder)
- SQL script to create the database and sample data: sql/init.sql

Notes to run:
1. Create MySQL database using the `sql/init.sql` script (use XAMPP's phpMyAdmin or mysql CLI).
2. Make sure MySQL Connector/J is added to your NetBeans project's libraries.
3. Open the project folder in NetBeans as a Java project, run Main.java.
4. Default sample accounts:
   - Admin: username `admin`, password `admin`
   - Customer: username `user`, password `user`