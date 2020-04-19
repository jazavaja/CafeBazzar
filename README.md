این کتابخونه ساده شده کتابخونه کافه بازار است که برای سادگی و سهولت افراد انجام شده اگر مشکلی در استفاده کتابخونه داشتید میتونید ایمیل کنید یا همینجا مطرح کنید ممنون :)
### کتابخانه کافه بازار
##### 
#### راهنمای استفاده
##### مرحله اول 
----------------------------
##### Gradle
   کد زیر در گردل پروژه اضافه کنید

```java
allprojects {
	repositories 
	{
		maven { url 'https://jitpack.io' }
	}
}
```

در گردل پروژه کد زیر را اضافه کنید 

```java
dependencies {
	 implementation 'com.github.jazavaja:CafeBazzar:1.4'
}
```
----------------------------------------------------
##### Maven
##### اضافه کردن این کد در بیلد پروژه

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

#### اضافه کردن کد زیر در متد Ondestroy actvity

    cafeBazaar.OnDestroyCafeBazaar();

#### متد OnActivityResult رو به شکل زیر در بیارید

    
    @Override  
	protected void onActivityResult(final int requestCode, final 		int resultCode, @Nullable final Intent data) {  
    super.onActivityResult(requestCode, resultCode, data);  
	  cafeBazaar.onActivityResultCafeBazaar(requestCode, resultCode, data, new CafeBazaar.OnResult() {  
        @Override  
	  public void OnResultCustom() {  
            MainActivity.super.onActivityResult(requestCode,resultCode,data);  
	  }  
    });  
	}
در کد بالا MainActivity رو به اکتیویتی خود که در ان میخواهید پرداخت انجام دهید تغییر دهید

#### مرحله چهارم
خب در جایی از پروژه خود که میخواهید درخواست خرید به کافه بازار را انجام دهید کد زیر را قراخوانی کنید مثلا دکمه خرید

    cafeBazaar.launchForPay(activity,SKU,requestCode, IabHelper.OnIabPurchaseFinishedListener);
   
یا

    cafeBazaar.launchForPay(activity,SKU,requestCode, IabHelper.OnIabPurchaseFinishedListener,extraCode);

متد های دیگری وجود دارد مثل  متد زیر که تفاوت آن اینترفیس برای گرفتن خطا در درخواست به کافه بازار است در صورت نیاز از ان استفاده کنید

    launchForPayWithErrorListener(Activity activity, String SKU, int requestCode, String extra, IabHelper.OnIabPurchaseFinishedListener listener);
    launchForPayWithErrorListener(Activity activity, String SKU, int requestCode, IabHelper.OnIabPurchaseFinishedListener listener);

ورودی های متد بالا 
Activity : که نام کلاس اکتیویتی که در آن میخواهید کافه بازار را راه اندازی کنید
String SKU : نام محصول که در پنل مدیریت کافه بازار بخش محصولات درون برنامه ای درست کردید
requestcode : برای درخواست به اکتویتی هست که یک عدد وارد کنید


### تا به اینجا پیاده سازی پرداخت درون برنامه ای بصورت کامل انجام شده است
---------------
متد های دیگر کتابخانه :
در صورتی که محصول شما مصرفی میباشد در متد زیراین کد را فراخوانی کنید که مصرف شود در فراخوانی تابع  زیر 
launchForPay
به این صورت تکمیل کنید

    @Override  
	public void onIabPurchaseFinished(IabResult result, Purchase info) {  
    cafeBazaar.consumeProduct(info, new CafeBazaar.OnConsumeCafeBazaarFinishedListener() {  
        @Override  
	  public void OnConsumeFinished(Purchase purchase, IabResult result) {  
  
        }  
    });  
	}
خب مقدار result اگر true بود میتوان مطمئن شوید که محصول شما مصرف شده است
در صورتی که محصول شما مصرفی نیست از این تابع استفاده نکنید

----------------------
-----------------
برای گرفتن جزییات محصول یا محصولات از تابع زیر استفاده کنید

    cafebazaar.getDetailProduct(boolean showDetail, List<String> products, final OnGetProductDetailFinished detailFinished)


--------------
-------------
---------------
 در صورت داشتن مشکل یا مشاهده باگ لطفا ایمیل دهید یا در گیت هاپ بفرستید 
 موفق باشید :)
