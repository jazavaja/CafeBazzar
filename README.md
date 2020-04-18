این کتابخونه ساده شده کتابخونه کافه بازار است که برای سادگی و سهولت افراد انجام شده اگر مشکلی در استفاده کتابخونه داشتید میتونید ایمیل کنید یا همینجا مطرح کنید ممنون :)
### کتابخانه کافه بازار
##### 
#### راهنمای استفاده
##### مرحله اول 
----------------------------
##### Gradle
 Add it in your root build.gradle at the end of repositories:

```java
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

##### در گردل ماژول

```java
dependencies {
			...
	        implementation 'com.github.jazavaja:CafeBazzar:1.0.0'
	}
```
----------------------------------------------------
##### Maven
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
#### مرحله دوم
این دو پرمیشن رو به بخش منیفست برنامه خود اضافه کنید
```java
<uses-permission android:name="com.farsitel.bazaar.permission.PAY_THROUGH_BAZAAR"></uses-permission>
    <uses-permission android:name="android.permission.INTERNET" />
```
#### مرحله سوم
ساخت کلاس کافه بازار طبق زیر
```java
CafeBazaar c=new CafeBazaar(activity,base64);
OR
CafeBazaar c=new CafeBazaar(activity,base64,CafeBazzarInterface);

```
activity : is your activity
base64 : get from https://pishkhan.cafebazaar.ir/
CafeBazzarInterface : interface set Custom Error When Cafebazaar setup 
fail.or launch purchase fail....

#### step 4
اون قسمت که میخواهید درخواست پرداخت انجام شود مثل دکمه پرداخت










~~
