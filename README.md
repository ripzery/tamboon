# tamboon

To make it works, please run server and then enter endpoint url of the server in file **build.gradle** as following.

```groovy
productFlavors {
        sweet {
            dimension 'tb'
            applicationId "com.ripzery.tamboon"
            buildConfigField 'String', 'tamboonBaseUrl', '"http://localhost:8080/"'
        }
}
```
Assume your server endpoint is 192.168.25.171:8080, then change **tamboonBaseUrl** as following.

```groovy
productFlavors {
        sweet {
            dimension 'tb'
            applicationId "com.ripzery.tamboon"
            buildConfigField 'String', 'tamboonBaseUrl', '"http://192.168.25.171:8080/"'
        }
}
```
