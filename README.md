# 🏢 Journal Distribution Information System

Journal Distribution Information System is a system designed to **manage journal subscriptions, payments, and deliveries** efficiently. The platform supports **individual and corporate subscribers**, ensuring automated invoicing, reporting, and secure payment handling.

---

## 🎨 Key Features

- 🟢 **Individual and Corporate Subscription**: Supports both individual and corporate users with distinct billing models.
- 💳 **Automated Payment Processing**: Handles secure transactions and monitors subscription payments.
- 📊 **Multithreaded Report Generation**: Generates real-time reports on journal distributions and pending payments.
- 📚 **Subscription Management**: Allows users to create, modify, and track their subscriptions.
- 🌐 **GUI-Based System**: Built with Java Swing for an interactive and user-friendly interface.

---

## 🌐 Modularity and Project Structure

This project follows a **modular architecture**, ensuring scalability and maintainability.

### 📝 Easy Maintenance and Scalability
- Each component is structured independently, making future updates seamless.
- New features can be integrated without affecting existing functionality.

### 🔄 Code Reusability
- **Encapsulated logic** allows reusable methods across different modules.
- **Serializable classes** ensure persistence and efficient data handling.

### 🌍 Suitable for Team Collaboration
- The modular design supports simultaneous work on different parts of the system.
- **Separate GUI, business logic, and data handling** allows teams to focus on their respective components.

### 🎨 Cleaner and More Readable Code
- Structured package management enhances readability and debugging.
- Each class serves a **single responsibility**, making the system easy to navigate.

This approach makes the application **efficient, scalable, and well-organized**.

## 📂 Project Directory Structure

```
/src
  ├── project
  │   ├── Corporation.java           # Corporate subscriber management
  │   ├── DateInfo.java               # Subscription date handling
  │   ├── Distributor.java            # Core business logic for journal distribution
  │   ├── Individual.java             # Individual subscriber management
  │   ├── Journal.java                # Journal attributes and subscriptions
  │   ├── PaymentInfo.java            # Payment processing and tracking
  │   ├── ProjectGUI.java             # Java Swing-based user interface
  │   ├── Subscriber.java             # Abstract class for subscriber management
  │   ├── Subscription.java           # Subscription details and invoice handling
  │   └── tests
  │       ├── TestAll.java            # Runs all test cases
  │       ├── TestCorporation.java    # Unit tests for corporate subscribers
  │       ├── TestDateInfo.java       # Unit tests for date management
  │       ├── TestDistributor.java    # Unit tests for journal distributor logic
  │       ├── TestIndividual.java     # Unit tests for individual subscribers
  │       ├── TestJournal.java        # Unit tests for journal management
  │       ├── TestPaymentInfo.java   # Unit tests for payment processing
  │       └── TestSubscription.java  # Unit tests for subscriptions

.gitignore
README.md
```

---

## 💪 Installation and Usage

1. **Clone the Repository:**

   ```
   git clone https://github.com/yusufyilmaz00/Journal-Distribution-Information-System.git
   cd Journal-Distribution-Information-System
   ```

2. **Requirements:**
   - Java 8+
   - JUnit
   - Swing (GUI)

3. **Compile and Run the Application:**

   ```
   javac -d bin -sourcepath src src/project/ProjectGUI.java
   java -cp bin project.ProjectGUI
   ```

4. **Run Unit Tests:**

   ```
   javac -d bin -cp .:junit.jar -sourcepath src src/project/tests/TestAll.java
   java -cp bin:junit.jar org.junit.runner.JUnitCore project.tests.TestAll
   ```

## 👥 Contributions

If you find any issues or have suggestions for improvements, feel free to open an issue or submit a pull request (PR). Any feedback and contributions are welcome!

