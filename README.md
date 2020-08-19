# DaliDali<br>
[![](https://img.shields.io/badge/个人博客-Android笔记-green.svg)](https://brokes6.github.io)<br>
个人学习，练习制作的软件
## 项目介绍<br>
apk在Releases附件里<br>
这是一款仿造B站的软件，后期会与 "chenzijia12300"的 仿哔哩哔哩的后台系统 连接上（ https://github.com/chenzijia12300/dalidali ） <br>
目前使用的数据，都是采用本地数据<br>
<br>
## 自产自销（xswl）<br>
[![](https://jitpack.io/v/brokes6/CustomControl.svg)](https://jitpack.io/#brokes6/CustomControl)<br>
一些小的效果，我都写成了自定义控件，方便直接使用<br>
CustomControl: https://github.com/brokes6/CustomControl<br>
```Java
implementation 'com.github.brokes6:CustomControl:1.1.7'
```
## 细节介绍<br>
```HTML
1.全局（任何页面）都并没有使用 NestedScrollView 嵌套 RecyclerView
2.所有的Fragment都采取的懒加载
3.全片都采用DataBinding
4.基于AndroidX
5.屏幕完全适配
6..暂时想到这么多....
```

## 视频介绍:（JIG的效果会没这么好，实际效果更棒）<br>
### 主页:<br>
![image](https://github.com/brokes6/DaliDali/blob/master/app/src/showresources/home.gif)<br>
### 视频详情页面:<br>
![image](https://github.com/brokes6/DaliDali/blob/master/app/src/showresources/videoPage.gif)<br>
### 动态视频页面:<br>
跟随你的屏幕来进行自动播放<br>
![image](https://github.com/brokes6/DaliDali/blob/master/app/src/showresources/dynanmic_videoPage.gif)<br>
### 动态综合页面:<br>
![image](https://github.com/brokes6/DaliDali/blob/master/app/src/showresources/dynanmic_SynthPage.gif)<br>
### 频道页面:<br>
![image](https://github.com/brokes6/DaliDali/blob/master/app/src/showresources/channelPage.gif)<br>
### My页面:<br>
![image](https://github.com/brokes6/DaliDali/blob/master/app/src/showresources/mypage.gif)<br>
### Video_Horizontal_Screen页面:<br>
![image](https://github.com/brokes6/DaliDali/blob/master/app/src/showresources/video_horizontal.gif)<br>
还有些小细节就不展示了(后面将会补充)<br>
