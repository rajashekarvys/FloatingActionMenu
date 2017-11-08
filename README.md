# FloatingActionMenu

![Alt text](https://github.com/rajashekarvys/FloatingActionMenu/tree/master/sampleImages/GIF_1510119671120.gif)
![Alt text](https://github.com/rajashekarvys/FloatingActionMenu/tree/master/sampleImages/Screenshot_floatingactionmenu.png)

##Installation
Grab the AAR from Maven Central by adding it as a dependency in your build.gradle file:

```
dependencies {
compile 'com.github.rajashekarvys:FloatingActionMenu:v1.0'
}
```
add jitpack to project bulid.gradle

```
allprojects {
    repositories {
        maven { url 'https://maven.google.com' }
        maven { url 'https://jitpack.io' }
        jcenter()
    }
}
```

##Usage
Declare Folating action menu in xml or create it in code

```
    <tinny.com.fabmenulib.FloatingMenuLib
        android:id="@+id/fab"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" />
        
    
     val fab: FloatingMenuLib = findViewById(R.id.fab) as FloatingMenuLib
```

Construct a ImagePojo with required parameters fab images, fab images background color, text to display and text color finally set imagePojo to fab menu.

```
    val imagesPojo: ArrayList<ImagesPojo> = ArrayList();
        imagesPojo.add(ImagesPojo(R.mipmap.message, getString(R.string.sms),0,resources.getColor(R.color.white)))
        imagesPojo.add(ImagesPojo(R.mipmap.call, getString(R.string.call),0,resources.getColor(R.color.white)))
        imagesPojo.add(ImagesPojo(R.mipmap.whatsapp, getString(R.string.whatsapp),0,resources.getColor(R.color.white)))

        fab.setUpFabMenu(imagesPojo)
```

define fab item click listener
 ```
    fab.setOnFabItemClick(OnFabItemClick { pos ->
            when (pos) {
                0 ->
                    onSMSScheduler()
                1 ->
                    onCallScheduler()
                2 ->
                    onWhatsAppScheduler()
            }
        })
```

##Customizations

You can set background color to screen when fab menu opens
```
fab.setBackgroundScreenColor(int color)
```
Define background transparency
```
fab.setBackGroundTransparent(int transparentValue)
```
You can use bitmap or drawable or resource to set fab home icon
```
setFabIcon(Bitmap bitmap)
```
To close or open fab menu programmatically
```
closeFabMenu() and openFabMenu()
```
To listen fab menu open and close use
```
setOnFabOpenCloseListener(OnFabOpenCloseListener onFabOpenCloseListener)
```