JavaFx Project using Maven
==========================
In this repo we will explain how to setup simple/fxml (without/with fxml) JavaFx application using IntelliJ.

# Configuration
- Open IntelliJ > New > Project
- Select Maven in the left pane
- Select (check) `Create from archetype` in the right pane and click `Add archetype`
- In the pop dialog box add the following and click ok
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
    Version: 1.0-SNAPSHOT # tar file name
```
- In the properties we need to change one and add one more properties
    - Click archetypeArtifactId and change value to `javafx-archetype-simpe` OR  `javafx-archetype-fxml` (for fxml support) and click OK.
    - Click + button and type name: javafx-version and value: 15.0.1/latest and click OK.
- Click Next and Finish.

# Run configuration
- In the toolbar click `Add configrations`
- Select + > Maven > and then type a name `Run`
- Expand `Before lanuch` and click + and select `Run Maven Goal` and type `javafx:run` in the Command line, and click Ok.
- In the window also type `javafx:run` in the command line, click apply, ok.
- Now you will see Run button along with play button in the toolbar.
## Using Maven
- `mvn clean javafx:run`

# Distribution builds
- [Build](https://github.com/dlemmermann/JPackageScriptFX)

## Verify
- Simple: You can expand the External Libraries in the left pane of IntelliJ and notice that it has `javafx-base/controls/graphics` jar files downloaded.
- Fxml: You can expand the External Libraries in the left pane of IntelliJ and notice that it has `javafx-base/controls/graphics/fxml` jar files downloaded.

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