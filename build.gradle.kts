plugins{

}
buildscript {
  repositories {
    google()
    mavenCentral()
  }

  dependencies {
    classpath (BuildPlugins.androidGradlePlugin)
    classpath (BuildPlugins.kotlinGradlePlugin)
    classpath (BuildPlugins.hiltGradlePlugin)
  }
}

allprojects {
  repositories {
    google()
    mavenCentral()
  }
}
