DenUp
=====

Small upload image tool for Android 

Android app is in the root and server application is inside the "Server side" folder 
You will need to create a "keys.php" on the server which should contain the following variables: 

- $secretkey = "somekey"
- $path = '/path/to/imagefolder'
- $host = "http://host/imagefolder/"

On the Android side you need to create a api_keys.xml in DenUp/app/src/main/res/values/ and this should contain the following: 
- <string name="key">somekey</string>
