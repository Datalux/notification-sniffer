# notification-sniffer
An Android Library that implements a Notificaion Sniffer

## Gradle Dependency
- gradle project level
 ```gradle 
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
- gradle application level
```gradle 
dependencies {
  implementation 'com.github.Datalux:notification-sniffer:0.1'
}
 ```
 
 
## Example

 ```kotlin
if(!NotificationSniffer.checkForPermission(this))
    NotificationSniffer.askForPermission(this)
    

NotificationSniffer.startSniffing(this)

NotificationSniffer.listener = object : SniffListener {
    override fun onResult(extractedNotification: ExtractedNotification) {
        Log.d("onMain", extractedNotification.toString())
    }
}
```

