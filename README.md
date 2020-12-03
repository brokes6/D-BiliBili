# DaliDali<br>
[![](https://img.shields.io/badge/个人博客-Android笔记-green.svg)](https://brokes6.github.io)<br>
个人学习，练习制作的软件
## 项目介绍<br>
Apk在Releases附件里<br>
这是一款仿造B站的软件，现在正在与 "chenzijia12300"的 仿哔哩哔哩的后台系统 对接（ https://github.com/chenzijia12300/dalidali ） <br>
目前使用的数据，都来自chenzijia12300的 仿哔哩哔哩的后台系统 <br>
<br>
## 使用了自己写的工具类<br>
[![](https://jitpack.io/v/brokes6/CustomControl.svg)](https://jitpack.io/#brokes6/CustomControl)<br>
一些小的效果，我都写成了自定义控件，方便直接使用，欢迎使用和star<br>
CustomControl: https://github.com/brokes6/CustomControl<br>
```Java
implementation 'com.github.brokes6:CustomControl:1.1.7'
```
## 细节介绍<br>
```HTML
1.全局（任何页面）都极少使用 NestedScrollView 嵌套 RecyclerView（除了明确RecyclerView子类个数较少）
2.所有的Fragment都采取的懒加载
3.全片都采用DataBinding
4.基于AndroidX
5.屏幕完全适配
6.使用了RxJava+Retrofit2
7.使用了依赖注入（Dagger2）
8.RecyclerView已尽力优化...
9.可以适配大多数异性屏
10.内存占用很小
11.采取MVP模式来进行的开发
12.软件可在线更新
13.保存数据采用了Room+LiveDate
```

## 视频介绍:（GIF的效果会没这么好，实际效果更棒）<br>
### 主页:<br>
![image](https://github.com/brokes6/DaliDali/blob/master/app/src/showresources/login.gif)<br>
![image](https://github.com/brokes6/DaliDali/blob/master/app/src/showresources/home.gif)<br>
![image](https://github.com/brokes6/DaliDali/blob/master/app/src/showresources/home_1.gif)<br>
![image](https://github.com/brokes6/DaliDali/blob/master/app/src/showresources/home_2.gif)<br>
![image](https://github.com/brokes6/DaliDali/blob/master/app/src/showresources/home_3.gif)<br>
![image](https://github.com/brokes6/DaliDali/blob/master/app/src/showresources/dongtai.gif)<br>
![image](https://github.com/brokes6/DaliDali/blob/master/app/src/showresources/video_horizontal.gif)<br>
还有些小细节就不展示了(后面将会补充)<br>

/ps: 如有侵权，请联系作者！
