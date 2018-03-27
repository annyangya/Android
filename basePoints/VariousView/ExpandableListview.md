### ExpandableListView

#### ExpandableListView 的总体概述
ExpandableListView 是 android 中可以实现下拉 list 的一个控件，是一个垂直滚动的心事两个级别列表项手风琴试图，列表项是来自 ExpandableListViewaAdapter，组可以单独展开。

##### 重要的方法：
1. expandGroup (int groupPos) ;//在分组列表视图中 展开一组，
2. setSelectedGroup (int groupPosition) ;//设置选择指定的组。
3. setSelectedChild (int groupPosition, int childPosition, boolean shouldExpandGroup);
   getPackedPositionGroup (long packedPosition);//返回所选择的组
4. isGroupExpanded (int groupPosition);//判断此组是否展开
5. expandableListView.setDivider();//这个是设定每个 Group 之间的分割线。
6. expandableListView.setGroupIndicator();//这个是设定每个 Group 之前的那个图标。
7. expandableListView.collapseGroup(int group); //将第 group 组收起

##### 适配器的介绍：

ExpandableListAdapter，一个接口，将基础数据链接到一个 ExpandableListView。 此接口的实施将提供访问 Child 的数据(由组分类)，并实例化的 Child 和 Group。适配器中常用的重要方法:
1. getChildId (int groupPosition, int childPosition) 获取与在给定组给予孩子相关的数据。
2. getChildrenCount (int groupPosition) 返回在指定 Group 的 Child 数目

##### 属性和事件：

1. 在 Android 中对子条目的点击事件是通过 onChildClick()来实现
2. 对组的点击事件是通过 onGroupClick()来实现的

##### Gson 框架:它是谷歌推出的一个请求网络数据的一个框架，常用的用法如下:
GSON 的两个重要方法

在 GSON 的 API 中，提供了两个重要的方法:toJson()和 fromJson()方法。其中，toJson()方法用来实现将 Java 对象转换为相应的 JSON 数据，fromJson()方法则用来实现将 JSON 数据转换为
相应的 Java 对象。

###### toJson()方法
toJson()方法用于将 Java 对象转换为相应的 JSON 数据，主要有以下几种形式:

1. String toJson(JsonElement jsonElement);//用于将 JsonElement 对象(可以是 JsonObject、JsonArray 等)转换成 JSON数据
2. String toJson(Object src);//用于将指定的 Object 对象序列化成相应的 JSON 数据
3. String toJson(Object src, Type typeOfSrc);//用于将指定的 Object 对象(可以包括泛型类型)序列化成相应的 JSON 数据

###### fromJson()方法
fromJson()方法用于将 JSON 数据转换为相应的 Java 对象，主要有以下几种形式:

1. <T> T fromJson(JsonElement json, Class<T> classOfT);

2. <T> T fromJson(JsonElement json, Type typeOfT);

3. <T> T fromJson(JsonReader reader, Type typeOfT);

4. <T> T fromJson(Reader reader, Type typeOfT);

5. <T> T fromJson(String json, Class<T> classOfT);

6. <T> T fromJson(String json, Type typeOfT);

以上的方法用于将不同形式的 JSON 数据解析成 Java 对象。所以说 gson 是一个很好的请求网络数据的框架，既可以在服务器端生成一个 json 字符串，然后所以说 gson 是一个很好的请求网络数据的框架，既可以在服务器端生成一个 json 字符串，然后客户端通过发送请求向服务器，进行数据解析。

##### Picasso框架

这也是本课程中涉及的一个第三方的框架，它主要是用于网络请求图片时的一种框架，它的代码量少，自带缓存，是一个值得使用的框架。
首先 Picasso 也是 Afinal 这个框架的一种，Afinal 是一个 android 的 ioc，orm 框架，内置了四大模块功能:FinalAcitivity,FinalBitmap,FinalDb,FinalHttp。
1. 通过 finalActivity，我们可以通过注解的方式进行绑定 ui 和事件。
2. 通过 finalBitmap，我们可以方便的加载 bitmap 图片，而无需考虑 oom 等问题。
3. 通过 finalDB 模块，我们一行代码就可以对 android 的 sqlite 数据库进行增删改查。
4. 通过FinalHttp 模块，我们可以以 ajax 形式请求 http 数据

				

			

		

	
