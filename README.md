JavaFx Project using Maven
==========================
This is a demo app for hotel booking build in Java, JavaFx, Fxml, H2 Database, Tomcat.
In this repo we will explain how to setup simple/fxml (without/with fxml) JavaFx application using IntelliJ.
![demo](https://raw.githubusercontent.com/iloveyii/hotel_frontend_maven/master/src/main/resources/org/hotel/images/demo.gif)

# Installations
- You only need to follow this section if you are cloning from git repo
- You need the rest of the sections only IF you want to create such project from scratch
- Clone the repo `git clone https://github.com/iloveyii/hotel_frontend_maven.git`
- Either use command line `mvn clean javafx:run` or follow the section "Run configuration" below if you want to run from IntelliJ
- The default root credentials are `email: root@localhost & password: root`, once logged in you can create more users.
- This JavaFx application works with `hotel_backend` that you can clone `https://github.com/iloveyii/hotel_backend.git` and follow instructions in the README.md

## Pre Requisite
- You need to install Git, Maven, Docker, IntelliJ for your platform.

# Configuration
- Open IntelliJ > New > Project
- Select Maven in the left pane
- Select (check) `Create from archetype` in the right pane and click `Add archetype`
- In the pop dialog box add the following and click ok and then next.
```yaml
    GroupId: org.openjfx
    ArtifactId: javafx-maven-archetypes
    Version: 0.0.5
```
- Type a name for the directory of your project
- Expand `Artifacts Coordinates` and enter the following and click Next
```yaml
    GroupId: org.javafx # package name
    ArtifactId: javafx-maven   # Project Name
    Version: 1.0-SNAPSHOT # jar file name
```
- In the properties we need to change one and add one more properties
    - Double Click archetypeArtifactId and change value to `javafx-archetype-simpe` OR  `javafx-archetype-fxml` (for fxml support) and click OK.
    - Click + button and type name: javafx-version and value: 15.0.1(or latest version) and click OK.
- Click Next and Finish.
## Verify
- Simple: You can expand the External Libraries in the left pane of IntelliJ and notice that it has `javafx-base/controls/graphics` jar files downloaded.
- Fxml: You can expand the External Libraries in the left pane of IntelliJ and notice that it has `javafx-base/controls/graphics/fxml` jar files downloaded.

# Run configuration
- In the toolbar click `Add configrations`
- Select + > Maven > and then type a name `Run`
- Expand `Before lanuch` and click + , Add New Task, and select `Run Maven Goal` and type `javafx:run` in the Command line, and click Ok.
- In the window also type `javafx:run` in the command line, click apply, ok.
- Now you will see Run button along with play button in the toolbar.
## Using Maven
- `mvn clean javafx:run`

# Distribution builds
- [Build](https://github.com/dlemmermann/JPackageScriptFX)


# Latest
- [DOCS](https://openjfx.io/openjfx-docs/), and in the left menu click `JavaFX and IntelliJ` > `Non-modular with Maven`
- Latest Open JavaFX 17 [link](https://gluonhq.com/products/javafx/)
- Latest Open JavaFX Maven 0.0.6 Plugin [link](https://mvnrepository.com/artifact/org.openjfx/javafx-maven-plugin/0.0.6)

# Issues
- Binding observable list to tableview does not show data but error `becuase module does not open org.hotel.models to javafx.base`
- Solution: the module-info.java should look like:
```java
module org.hotel {
    requires javafx.base;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;
    opens org.hotel to javafx.fxml;
    opens org.hotel.models to javafx.base;
    exports org.hotel;
}
```

- WARNING: Illegal reflective access by com.google.inject.internal.cglib.core
- Solution:
```bash
  # Download maven 3.6.3
  wget https://www-us.apache.org/dist/maven/maven-3/3.6.3/binaries/apache-maven-3.6.3-bin.tar.gz -P /tmp
  # Untar downloaded file to /opt
  sudo tar xf /tmp/apache-maven-*.tar.gz -C /opt
  # Install the alternative version for the mvn in your system
  sudo update-alternatives --install /usr/bin/mvn mvn /opt/apache-maven-3.6.3/bin/mvn 363
  # Check if your configuration is ok. You may use your current or the 3.6.3 whenever you wish, running the command below.
  sudo update-alternatives --config mvn
```