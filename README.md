The technical documentation of CafeBazaar has been summarized and included in this repository
##### Made By javadSarlak (teamtext group)
#### for Usage
##### Step 1 (gradle or maven)
----------------------------
##### if you use Gradle
 Add it in your root build.gradle at the end of repositories:

```java
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

##### Step 2. Add the dependency

```java
dependencies {
			...
	        implementation 'com.github.jazavaja:CafeBazzar:1.0.0'
	}
```
----------------------------------------------------
##### if you use Maven
##### Add the JitPack repository to your build file

```java
<repositories>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>
```
#####  Add the dependency

```java
<dependency>
	    <groupId>com.github.jazavaja</groupId>
	    <artifactId>CafeBazzar</artifactId>
	    <version>1.0.0</version>
	</dependency>
```
------------------------
#### step 2
add 2  permission below :
```java
<uses-permission android:name="com.farsitel.bazaar.permission.PAY_THROUGH_BAZAAR"></uses-permission>
    <uses-permission android:name="android.permission.INTERNET" />
```
#### step 3 
just create a new class Cafebazaar in your Activity (CafeBazaar have 2 constructor )
```java
CafeBazaar c=new CafeBazaar(activity,base64);
OR
CafeBazaar c=new CafeBazaar(activity,base64,CafeBazzarInterface);

```






