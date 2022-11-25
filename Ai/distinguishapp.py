from PIL import Image
import tensorflow as tf
from tensorflow.keras.models import load_model
from tensorflow.keras import utils
from flask import Flask, request, jsonify, render_template
from io import BytesIO
import numpy as np
import requests
from util import base64_to_pil

app = Flask(__name__)
# 中文显示
app.config['JSON_AS_ASCII'] = False

global model
# 读取模型
model = load_model('model/flowervgg13after.hdf5')
# 模型的标签
class_names = ['雏菊', '蒲公英', '玫瑰', '向日葵', '郁金香']


# 测试服务是否正常
@app.route('/', methods=['GET'])
def index():
    return '<h1>成功连接服务器</h1>'
# 网页端识别
@app.route('/classifierhtml',methods=['GET','POST'])
def classifierhtml():
    # get请求就返回html
    if request.method == 'GET':
        return render_template('index.html')
    # post请求进行预测处理
    elif request.method == 'POST':
        # 对前端传来的图片进行base64解码并进行转换PIL对象
        img = base64_to_pil(request.json)
        img = img.resize((227, 227))
        #  把PIL对象 PIL对象numpy数组
        img = utils.img_to_array(img)
        # 扩充一维
        img = tf.expand_dims(img, 0)
        # 使用cpu预测图片
        with tf.device("/cpu:0"):
            preds = model.predict(img)
        # 筛选概率最高的标签
        pred_class = class_names[np.argmax(preds[0])]
        # 转换字符串
        result = str(pred_class)
        return jsonify(result=result, probability=str(np.argmax(preds[0])))

# webAPI
@app.route('/predict/', methods=['POST', 'GET'])
def image_classifier():
    if request.method == 'POST':
        # 获取从客户端传来的数据中 ,获取请求体
        image_base64 = request.json
        # 获取图片url
        image_str = requests.get(image_base64['img_url'])

        # 把图片二进制数据流转换为PIL对象
        img = Image.open(BytesIO(image_str.content)).resize((227, 227))
        #  把PIL对象 PIL对象numpy数组
        img = utils.img_to_array(img)
        # 扩充一维
        img = tf.expand_dims(img, 0)
        # 使用cpu预测图片
        with tf.device("/cpu:0"):
            preds = model.predict(img)
        # 遍历结果把结果和标签对应起来
        filelist = [{"name": class_names[0], "value": str(round(preds[0][0],2))},
                    {"name": class_names[1], "value": str(round(preds[0][1],2))},
                    {"name": class_names[2], "value": str(round(preds[0][2],2))},
                    {"name": class_names[3], "value": str(round(preds[0][3],2))},
                    {"name": class_names[4], "value": str(round(preds[0][4],2))}
                     ]
        # for i in range(len(class_names)):
        #     filelist[class_names[i]] = "{:.2f}%".format(preds[0][i])

        data = {"status": "200", "msg": "success", "data": filelist}

        return jsonify(data)
    return None

if __name__ == '__main__':
    # model = load_model('model/aucc0.94loss0.3724.hdf5')
    app.run('0.0.0.0', 8080, debug=False)
