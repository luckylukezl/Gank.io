# Gank.io
基于 干货集中营 API 文档 编写

架构：MVP模式

依赖库：RxJava，Retrofit，Glide，OkHttp，Material Design，RecyclerView，ButterKnife
---
## 开始做每日记录
### 2月23日
- [x] 上传到github
- [x] 写webView界面
    - [x] 完成toolbar，返回键和菜单键（分享，收藏）
    - [x] 利用textSwitch制作文字改变及textView的跑马灯效果
    - [x] webView页面的按键直接返回，设置webClient和ChromeClient及settings
    - [x] 设置加载进度
- [ ] gif图：点击查看，并可以左右滑动。界面为gif图和文字。缩略图为静态图。
- [x] 调整界面，将“福利”和“今日推荐”调整到工具栏

### 实现说明
- 跑马灯效果设置Ellipsize为TruncateAt.MARQUEE即可，但处于点击状态，继承TextView，重写isFocus方法。
- 调整界面，将原来的tab和ViewPager组合放到另一个Fragment内。

### 遗留问题
- Error: WebView.destroy() called while still attached!以及打开视频网站的错误。
- webView有些界面打不开，如2月21日，Android，“一周日历”。关于github的都打不开，可能是被墙了，囧。更多问题参看[webview坑集合](https://www.zhihu.com/question/31316646)
- 分享功能采用Android自带的分享功能，需要更强大的功能可以采用shareSDK。
- 收藏功能涉及到数据库，之后集中弄。
- 调整界面的主页跳转还未写，先空着。
- git图没有时间做了，留到明天。
- 图片界面的toolbar应该改成后退。将tool脱离出来，许多地方要用。
