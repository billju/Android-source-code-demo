## Android 原始碼範例
使用sdk版本是android 7.1.1，能在4.1到8.0的版本上成功執行<br/>
為了減少翻資料夾的麻煩，只留了主程式檔和compile須知，有些比較複雜的介面布局檔也會留下來
## Animator
使用ObjectAnimator與ValueAnimator來控制一行文字的動畫格式<br/>
## Broadcast & SharePreference
首先透過SharePreference來記住帳密，登入之後呼叫Broadcast，此時會廣播並要求所有活動完成<br/>
## Camera Intent
使用意圖intent呼叫系統內的相機來拍攝，一開始先確認是否已經取得使用相機的權限，如果沒有事先確認權限可是會閃退的，接著以putExtra(Media.Store.EXTRA_OUTPUT)將完成的相片傳入onActivityResult中，最後確認自訂的requestCode和resultCode為正常後改變UI上的imageView，在7.1.1版之後的SDK使用這個方法會不斷閃退，建議在預設為6.0 marshmallow的SDK版本下寫。<br/>
## Content Provider
Content Provider是安卓可以跨程式存取的一個元件，主活動透過uri將依連串的任務用ContentValue傳遞資料到Content Provider的class中或是用getContentResolver()來取得資料，在這個class中又會執行更改資料庫的動作，資料庫是自定義的MyDatabaseHelper，繼承自SQLiteOpenHelper。<br/>
## Get contacts
取得聯絡人的資料，需要先取得READ_CONTACTS的權限，並使用Cursor去取得Content Provider提供的聯絡人資料，因為資料種類不是單一的，還要用getColumnIndex去細分種類。<br/>
## Get location name via Json
一開始尋找location的provider有哪些可用，而GPS的定位是最準的要優先使用，其次是網路，如果都沒有就會跳出一個AlertDialog，接著也要確定都有取得權限以防閃退，接著建立一個監聽器，當位置在移動或是隨時間過去的時候會刷新現在的位置，在程式關閉後也要注意監聽器需要關閉。<br/>
只要在瀏覽器輸入Google Maps的API網址 (http://maps.google.com/maps/api/geocode/json?latlng=經度,緯度&language=zh-TW&sensor=true) 便會跳出一長串以繁體中文顯示的Json格式資料，開啟一個新執行緒呼叫網址並使用Java內建的BufferedReader來把內容一個個取出放到用來暫存的String，因為有空格的關係所以要個別把這些字串再append到StringBuilder中，接著開啟JsonObject選擇物件來源，轉至JsonArray中來進行讀取資料，最後用Message的方式送到Handler中更改UI上的文字。<br/>
## Google Map
要先到Google API的網站上申請開發者序號，更改Manifest中的序號檔，畢竟安卓開發這東西改來改去的，還是開模板再看看就好，加入相機位置的更動
mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(你的經緯度,放大程度，一般為15));<br/>
## Messager
自製簡易聊天對話框，先設定Msg Class來自定資料格式，主要的運作原理是用List Adapter讓新的資料能後從上而下並等距的出現，藉由ViewHolder控制左介面後右介面的visibility可以讓對話框出現在左邊或是右邊<br/>
## NavigationDrawer
預設的模組，是一個非常主流的介面，但很容易隨著版本的改變就不能使用，其中會把左邊的「抽屜」分成一個menu資源檔，抽屜裡的header又會被分為一個layout檔<br/>
## Notification
製作很煩人的通知，可以設定上面的小圖示(setSmallIcon)、主標題、副標題(ContentText)、震動模式、閃燈的顏色頻率、加入動作圖示BigView(addAction)，要使用PendingIntent來設定當圖是被按下後執行的程式<br/>
## PaperScissorsStone
一個很廢的剪刀石頭布的小遊戲，主介面是用RelativeLayout，按下顯示結果的按鈕會跳出一個新的活動並將目前結果的數值傳入。<br/>
## RandomDice
按下按鈕後讓imageView不斷切換影格，並讓執行緒sleep一段時間，最後會將random的值與圖片以Message傳入handler中更改UI<br/>
## SearchList
先在List中加入所有資料，當List被點下後會讓Snackbar彈起，接著在OptionMenu當中加上搜尋圖示與SearchView，並設定onQueryText監聽器。<br/>
## Send and Receive Sms
發送與攔截簡訊，發送的方面只要呼叫SmsManager就可以發送，而攔截的方面比較麻煩，需要依賴當簡訊寄來時的Broadcast，接者還要剖析Sms的編碼，在SDK19以下的版本需要用pdus數組取得，之後只要靠一行程式碼就可以解決，可以取得簡訊的來源以及字串本體，比較麻煩的是還要一個字慢慢地讀入。<br/>
## Send data to server(Volley)
透過Volley程式庫將資料更簡單的post到伺服器中，首先要確認伺服器的url，接著排隊進入RequestQueue中，以StringRequest的形式Post到伺服器，並且判別結果為何，onResponse可以伺服器端獲得傳來的資訊，Response.ErrorListener的onErrorResponse可以傳來自己設定的錯誤發生後的訊息，getParams則是可以將要Post的內容put進去。<br/>
伺服器端大推Node.js，簡單好用快速不是假的。<br/>
## Sensor & Compass
手機內建感應器的練習檔，先取得Sensor的種類並放到Sensor的物件中，接著呼叫sensorManager來開啟感應器的監聽器，丟到SensorEventListener中處理資訊，接者分析旋轉矩陣並轉為角度後丟到RotateAnimation中讓羅盤動起來。<br/>
## Service
「服務」簡單來講就是在後台執行的程式，要使用intent來啟動service的class，另外還可以利用broadcast來定期呼叫service，當系統時間到了會啟動broadcast呼叫其他軟體，而當這個app接收到的時候會啟用MyService這個class。<br/>
## Sqlite
安卓內建的資料庫，把資料通通塞進去就對了，要使用SQLiteOpenHelper程式庫，並自訂當事件發生時改變的事項。<br/>
## Toolbar
可以自己製作像OptionMenu的介面，大部分的物件資源檔會放在menu裡面。<br/>
## ViewPager & TabLayout
極為主流的介面，可以左右滑動螢幕切換到不同的Fragment中，也可以點選上面的TabLayout直接切換，Tab上可以設定圖示，透過與ViewPager的綁定還可以設定標題，要注意compile的版本。<br/>
## WebView
我註解起來的是一般的WebView，WebView使用的是系統內自帶的瀏覽器，使用shouldOverrideUrlLoading可以讓網頁連結會在本程式內繼續使用，而不是跳脫至其他網頁瀏覽器，而下面的程式則是透過輸入指定的Url去獲得網站所有的原始碼。<br/>
## 把你的程式碼一起分享到github上吧
初始化，加入git的隱藏資料夾<br/>
git init<br/>
有時候會出現Warning LF will be replaced by CRLF，所以要取消git的自動換行才能繼續上加入檔案<br/>
git config --global core.autocrif false<br/>
加入所有東西<br/>
git add .<br/>
提交並建立第一個版本，同樣版本無法被重複建立<br/>
git commit -m "first commit"<br/>
連結到github的repository<br/>
git remote add origin https://github.com/你的名字/你的repository<br/>
上傳檔案囉<br/>
git push origin master<br/>
