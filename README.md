DenUp
=====

Small upload image tool for Android, this app is not written with privacy in mind, but rather an easy way to publicly share images. The images are sent unencrypted at this stage, however I might implement SSL at some point.  

Android app is in the root and server application is inside the "Server side" folder 
You will need to create a "keys.php" on the server which should contain the following variables: 

- `$secretkey = "somekey"`
- `$path = '/path/to/imagefolder'`
- `$host = "http://host/imagefolder/"`

On the Android side you need to create a api_keys.xml in DenUp/app/src/main/res/values/ and this should contain the following: 
- `<string name="key">somekey</string>`
