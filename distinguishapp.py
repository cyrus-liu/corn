from PIL import Image
import tensorflow as tf
from tensorflow.keras.models import load_model
import base64
from tensorflow.keras import utils
from tensorflow.python.keras.backend import set_session
from flask import Flask,request,Response,jsonify
from io import BytesIO

app = Flask(__name__)
# 中文显示
app.config[ 'JSON_AS_ASCII']= False

global model

model = load_model('./model/20221105-141453.hdf5')
class_names = ['细菌性枯萎病', '瘟病', '褐斑病', '水稻东格鲁病毒病']


def model_predict(img):
    global model
    x = utils.img_to_array(img)

    x = tf.expand_dims(x, axis=0)

    print(x)

    preds = model.predict(x)

    return preds



@app.route('/',methods=['GET'])
def index():
    return '<h1>成功连接服务器</h1>'
@app.route('/predict/', methods=['POST'])
def image_classifier():
    if request.method == 'POST':
        # 获取从客户端传来的数据中 image_base64 字段的值
        image_base64 = request.form['image_base64']
        # 将 Base64 解码
        image_str = base64.b64decode(image_base64)
        # 将解码后的字符串转成 uint8 的 NumPy
        img = Image.open(BytesIO(image_str)).resize((227,227))

        img = utils.img_to_array(img)

        img = tf.expand_dims(img,0)
        with tf.device("/cpu:0"):
            preds = model.predict(img)

        filelist = {}
        for i in range(len(class_names)):
            filelist[class_names[i]] = "{:.2f}%".format(preds[0][i])


        data = {"status": "200", "msg": "success"}
        data["data"] = filelist

        return jsonify(data)
    return None

if __name__ == '__main__':
    model = load_model('./model/20221105-141453.hdf5')
    app.run('0.0.0.0',8080,debug=True)





