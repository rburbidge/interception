# interception
Supports wrapping Java code in interceptor pipelines.

# Building and publishing
* `gradle build` - Builds the library
* `gradle bintrayUpload` - Publishes the library to bintray. See [Publishing](#Publishing).

## Publishing
Builds are published using `gradle bintrayUpload`.

Setup these environment variables on your system containing your https://bintray.com/ user name and API key.
* `bintrayUser`
* `bintrayApiKey`

Publishing will fail if bintray contains the same version. Bump up `libraryVersion` in `./ext.gradle` to retry the publish step.