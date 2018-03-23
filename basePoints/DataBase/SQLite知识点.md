## SQLite数据库存储
使用SQLiteOpenHelper帮助类对数据库进行管理与存储


### SQLiteOpenHelper相关操作
写一个类继承SQLiteOpenHelper,并重写其onCreate(),toUpgrade()方法，并写出构造方法，在该类里面写出建表，删除，修改，查询，更新等方法

#### create table
以下代码是建表过程：
1. id Integer primary key autoincrement，primary key表示其为主键，id为Integer类型，autoincrement表示其自动增长
2. name 为字符串类型，用varchar表示
3. content为文本类型，用text表示
4. 将建表语句定义为一个字符串常量，方便在onCreate()方法中使用
```java
    public static final String CRATER_BOOK_INFO="create table Book_info(" +
            "id Integer primary key autoincrement," +
            "name varchar," +
            "pages Integer," +
            "content Text," +
            "price double)";

    public static final String CRATER_PERSON_INFO="create table Person_info(" +
            "id Integer primary key autoincrement" +
            "name varchar," +
            "age Integer," +
            "height double," +
            "address varchar)";

```

#### onCreate()方法
调用SQLiteDataBase的execSQL()方法执行建表语句
 ```java
 private Context context;
    public DataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }
 ```
 
 #### onUpgrade()方法
 更新操作，下面代码表示如果表存在就将其删除并重新建立该表;在程序中，如果运行一次后建立表，那么再次运行就不会建立表，因为已经存在；
 如果还要进行建表操作，需要将其删除，再重新建立
 ```java
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Book_info");
        db.execSQL("drop table if exists Person_info");
        onCreate(db);
    }

 ```
 
 接下来就是在MainActivity中执行，对dataBaseHelper初始化，
 dataBaseHelper=new DataBaseHelper(this,"database.db",null,2)，dataBaseHelper.getWritableDatabase()。
 
 #### 添加数据
 insert()方法用于添加数据，有三个参数
 1. 表的名字
 2. 第二个参数用于在未指定添加数据的情况下给某些可为空的列自动赋值为null
 3. 第三个参数是一个ContentValues对象，其中put()方法用于向contentvalues中添加数据，最后将contentvalues传递给要添加的表
 ```java
                SQLiteDatabase sb=dataBaseHelper.getWritableDatabase();
                ContentValues contentValues=new ContentValues();
                contentValues.put("pages",200);
                contentValues.put("price",10);
                contentValues.put("name","java");
                sb.insert("book_info",null,contentValues);

                contentValues.clear();
                //组装第二条数据
                contentValues.put("name","android");
                contentValues.put("pages",600);
                contentValues.put("price",30);
                sb.insert("book_info",null,contentValues);
 ```

#### 更新数据
upgrade()方法用于更新数据，有四个参数
1. 表的名字
2. contentvalues
3.
4. 第3，4个参数用于约束更新某一行或某一列的数据
以下代码表示，将price价格改为30，并且制定是书的名字为java的书
```java
                SQLiteDatabase sb2=dataBaseHelper.getWritableDatabase();
                ContentValues contentValues2=new ContentValues();
                contentValues2.put("price",30);
                sb2.update("book_info",contentValues2,"name=?",new String[]{"java"});
```

#### 删除数据
delete()方法，有三个参数
1. 表的名字
2.
3. 第2，3个参数用于约束更新某一行或某一列的数据，不指定的话就是默认删除所有行
以下代码表示删除表中页码数超过500的书
```java
                SQLiteDatabase sb3=dataBaseHelper.getWritableDatabase();
                sb3.delete("book_info","pages>?",new String[]{"500"});
```

#### 查询数据
query()方法，有七个参数
1. 表名
2. 指定查询哪几列，不指定默认查询所有列
3. 
4. 用于约束查询某一行或某几行，不指定则查询所有行
5. 指定需要去group by的列，不指定则表示不对查询结果进行group by操作
6. 用于对group by之后的数据进行过滤操作，不指定则不过滤
7. 指定查询结果的排序方式，不指定则表示用默认的排序方式，asc是指定列按升序排列,desc则是指定列按降序排列

查询完之后返回cursor对象：
1. moveToFirst()方法：从第一行开始扫描
2. getColumnIndex()方法获取某一列在表中对应的位置索引，将这个索引传入到相应的取值方法中，就可以获取数据，最后需要调用cursor.close()方法关闭cursor

```java
                SQLiteDatabase sb4=dataBaseHelper.getWritableDatabase();
                Cursor cursor=sb4.query("book_info",null,null,null,null,null,null);
                if(cursor.moveToFirst())
                {
                    do
                    {
                        String name=cursor.getString(cursor.getColumnIndex("name"));
                        double price=cursor.getDouble(cursor.getColumnIndex("price"));
                        int pages=cursor.getInt(cursor.getColumnIndex("pages"));

                        //在控制台打印输出
                        Log.d("MainACtivity","book name is"+name);
                        Log.d("MainActivity","book price is"+price);
                        Log.d("MainActivity","book pages is"+pages);
                    }while(cursor.moveToNext());
                }

                cursor.close();
```

