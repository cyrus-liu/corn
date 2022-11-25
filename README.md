## 项目介绍

一个基于VGG神经网络的花卉识别定位信息采集系统，代号：苞米豆。通过这个系统能够识别花卉的种类然后将信息保存到数据库中，并在地图上进行标记，共享给其他人查看。





##  在线地址

**小程序端**

![gh_1a57c08597ea_258 (2)](https://nonghb-1306784580.cos.ap-guangzhou.myqcloud.com/uPic/gh_1a57c08597ea_258%20(2).jpg)





**后台管理端**

线上访问链接： wzy.ink

测试账号：[test@qq.com](mailto:test@qq.com)，密码：123456，可登入后台查看。

在线接口文档地址：https://www.abinya.top/doc.html#/home





##  目录结构

**项目目录**

```
corn
├── Ai	--ai模块
├── LICENSE	--开源协议
├── README.md	--项目文档
├── admin-ui	--前端后台管理
├── corn-mysql8.0.sql	--sql文件，需要MySQL8,0以上
├── corn-springboot	--SpringBoot工程
└── phone-ui	--小程序
```



**SpringBoot工程**

```
corn-springboot
├── config	--配置模块
├── controller	--控制器模块
├── dto	--dto模块
├── entity	--实体类
├── error	--异常模块
├── mapper	--持久模块
├── service	--业务模块
├── utils	--工具类模块
└── vo	--vo模块
```



直接导入该项目于本地，修改后端配置文件中的数据库等连接信息，项目中使用到的**腾讯云对象存储**需要自行开通。

本地访问接口文档地址：http://127.0.0.1:8881/doc.html#/home



uni-app配置这里需要自己申请**腾讯地图账号**和**微信小程序AppID**，`npm install`安装所需的依赖。

| <img src="https://nonghb-1306784580.cos.ap-guangzhou.myqcloud.com/uPic/image-20221125153307014.png" alt="image-20221125153307014" style="zoom:50%;" /> | <img src="https://nonghb-1306784580.cos.ap-guangzhou.myqcloud.com/uPic/image-20221125153344776.png" alt="image-20221125153344776" style="zoom:50%;" /> |
| ------------------------------------------------------------ | ------------------------------------------------------------ |



**ps：请先运行后端项目，再启动前端项目，前端项目配置由后端动态加载。**

**ps：小程序基于uni-app开发，小程序工程要导入到uni-app的HBuilderX开发工具运行。**

**ps：AI模块先启动生成model目录，然后将下载的模型放入该目录中。**





##  项目特点

- 支持动态权限修改，采用RBAC模型，前端菜单和后台权限实时更新。
- 使用Java技术，MySQL数据库开发，充分保证了系统稳定性、完整性。
- 本项目采用主流的前后端分离架构SpringBoot + Vue，充分的使系统前后端代码解耦，提高了可维护性。
- 花卉图像识别部分通过API接口与前后端进行交互，解耦了算法与前后端工程。
- 经过测试，识别功能接口稳定且性能高，在网络良好的情况下，平均响应时间在2s内。
- 使用第三方对象存储图片节省服务器资源。



## 技术架构

**架构图**

![B78F8E6C-89CB-449F-8408-2A459D02F644](https://nonghb-1306784580.cos.ap-guangzhou.myqcloud.com/uPic/B78F8E6C-89CB-449F-8408-2A459D02F644.jpeg)



**流程图**

![image-20221125120552562](https://nonghb-1306784580.cos.ap-guangzhou.myqcloud.com/uPic/image-20221125120552562.png)



##  开发环境

| 开发工具  | 说明                       |
| --------- | -------------------------- |
| IDEA      | Java开发工具               |
| WebStorm  | Vue开发工具                |
| Navicat   | MySQL远程连接工具          |
| Tabby     | 远程连接工具、文件上传工具 |
| HBuilderX | 小程序开发工具             |

| 开发环境 | 版本   |
| -------- | ------ |
| JDK      | 1.8    |
| MySQL    | 8.0.20 |
| Redis    | 6.0.5  |



## AI部分说明
### 开发工具
| 开发工具      | 说明            |
|-----------|---------------|
| Pycharm   | python开发工具    |
| Xshell 7     | 远程连接工具、文件上传工具 |
### 环境
| 开发环境       | 版本    |
|------------|-------|
| mininconda | 3.7.5 |
| tensorflow | 2.7.0 |
| numpy      |       |
| Pillow     |       |
| gevent     |       |
| requests   |       |
首先运行`python util.py`生成对应文件夹，然后装备数据集，装备数据集，我使用是[花卉数据集](https://storage.googleapis.com/download.tensorflow.org/example_images/flower_photos.tgz)  
 把准备好的数据集放在dataset文件夹里目录结构如下： 

```
dataset_name
│   
│      
│
└───label1
│      file011.jpg
│      file012.jpg
│         ...
|
│   
└───label2
|
|      file021.jpg
|      file022.jpg
|       ...
...
```

然后用编辑器打开train.py文件，把Pre_training改为Falase  
如果你想用预训练模型进行训练的可以把模型文件放在model文件目录下，  
然后在train.py文件里models.load_model里输入模型的名字，  
再把Pre_training改为True就可以了。  
下载[miniconda](https://docs.conda.io/en/latest/miniconda.html)然后安装<font color="#dd0000">注意添加环境变量</font>打开终端输入以下：  

```CMD
# 创建虚拟环境
conda create -n tensorflow2 python=3.7.5
# 查看虚拟环境列表
conda env list
# 激活虚拟环境
conda activate tensorflow2
# 安装环境依赖
pip install -r requirement.txt
```

<font color="#dd0000">注意安装依赖时要移动到有requirement.txt文件目录下</font>不然会报错  
安装完依赖后运行`python train.py`就开始训练想看训练过程的  
情况的可以再打开一个终端进入tensorflow2环境移动项目目录然  
后`tensorboard --logdir=logs` 根据提示输入网址就可以看到过  
程了  

训练完后会在model目录下有以时间命名的模型文件，也可以用我的  
[模型](https://pan.baidu.com/s/1ltQCTGmYH2Q3E9FsIxtrwg)提取码是：corn把下载好模型放在model目录下打开distinguishapp.py  
在class_names中添加对应的标签在load_model读取对应的模型  
运行`python distinguishapp.py`然后在任何浏览器最好是谷歌  
浏览器输入http://127.0.0.1:8086/classifierhtml

![](https://nonghb-1306784580.cos.ap-guangzhou.myqcloud.com/uPic/snipaste_20221122_214053.png)

除了可以用网页端看到效果外，也可以通过API来开发你自己的  
客户端。使用API方式如下：
http://127.0.0.1:8086/predict/(post请求)

```json
{
    "img_url":"图片的url"
}
```

返回结果是json就拿我的项目举例

```json
{
	"data": [
		{
			"name": "雏菊",
			"value": "0.024256058"
		},
		{
			"name": "蒲公英",
			"value": "0.9540851"
		},
		{
			"name": "玫瑰",
			"value": "0.007327111"
		},
		{
			"name": "向日葵",
			"value": "0.002556602"
		},
		{
			"name": "郁金香",
			"value": "0.011775156"
		}
	],
	"msg": "success",
	"status": "200"
}
```





## 项目截图

| <img src="https://nonghb-1306784580.cos.ap-guangzhou.myqcloud.com/uPic/591669261526_.pic.jpg" alt="591669261526_.pic" style="zoom:25%;" /> | <img src="https://nonghb-1306784580.cos.ap-guangzhou.myqcloud.com/uPic/601669261526_.pic.jpg" alt="601669261526_.pic" style="zoom:25%;" /> |
| :----------------------------------------------------------: | :----------------------------------------------------------: |
| <img src="https://nonghb-1306784580.cos.ap-guangzhou.myqcloud.com/uPic/721669306925_.pic.jpg" alt="721669306925_.pic" style="zoom:25%;" /> | <img src="https://nonghb-1306784580.cos.ap-guangzhou.myqcloud.com/uPic/731669306927_.pic.jpg" alt="731669306927_.pic" style="zoom:25%;" /> |
| <img src="https://nonghb-1306784580.cos.ap-guangzhou.myqcloud.com/uPic/741669306928_.pic.jpg" alt="741669306928_.pic" style="zoom:25%;" /> | <img src="https://nonghb-1306784580.cos.ap-guangzhou.myqcloud.com/uPic/761669349753_.pic.jpg" alt="761669349753_.pic" style="zoom:25%;" /> |
| <img src="https://nonghb-1306784580.cos.ap-guangzhou.myqcloud.com/uPic/771669349854_.pic.jpg" alt="771669349854_.pic" style="zoom:25%;" /> |                                                              |
| ![image-20221125122609120](https://nonghb-1306784580.cos.ap-guangzhou.myqcloud.com/uPic/image-20221125122609120.png) | ![image-20221125122621876](https://nonghb-1306784580.cos.ap-guangzhou.myqcloud.com/uPic/image-20221125122621876.png) |
| ![image-20221125122652430](https://nonghb-1306784580.cos.ap-guangzhou.myqcloud.com/uPic/image-20221125122652430.png) | ![image-20221125122708510](https://nonghb-1306784580.cos.ap-guangzhou.myqcloud.com/uPic/image-20221125122708510.png) |
| ![image-20221125122720781](https://nonghb-1306784580.cos.ap-guangzhou.myqcloud.com/uPic/image-20221125122720781.png) | ![image-20221125122732561](https://nonghb-1306784580.cos.ap-guangzhou.myqcloud.com/uPic/image-20221125122732561.png) |
| ![image-20221125122750047](https://nonghb-1306784580.cos.ap-guangzhou.myqcloud.com/uPic/image-20221125122750047.png) |                                                              |
|                                                              |                                                              |



##  项目总结

历经20天左右的时间，本次的毕业设计已画上了句号。

抱怨一下，之前没有接触过uniapp写小程序，算是现用现学吧，坑太多了，非常不好用，早知道用户端H5了，下次写项目不用了。

项目中有些功能之前都有写过，只能说让原本熟悉的我更加熟悉吧，当然收获也是有的，比如：Web工程对接AI接口那块，SpringBoot整合微信小程序的登录并管理用户。

此次项目从整体看来，已基本达到预期的设计目的，能够实现基本的功能，但相较于市场的一些产品而言，还是有许多不足的地方。

遗憾的是，由于时间的有限，已经不允许再投入更多的时间和精力进行研究开发。相信在以后的工作中，我会接触到更多相关的知识，会更丰富自身的经验，我希望到时能够在此基础上完成一个丰富完整的项目，这将对我有很大的意义。
