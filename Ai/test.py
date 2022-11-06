import base64
import requests
from PIL import Image
from io import BytesIO
from tensorflow.keras import models
from tensorflow.keras import utils
import tensorflow as tf
import numpy as np
from modelconfig import *
class_names = ['Bacterialblight', 'Blast', 'Brownspot', 'Tungro']
# model = models.load_model('./model/20221105-141453.hdf5')
def imge_to_base64(Image,fmt='png'):
    output_buffer = BytesIO()
    Image.save(output_buffer,format=fmt)
    byte_data = output_buffer.getvalue()
    base64_str = base64.b64encode(byte_data).decode('utf-8')
    return base64_str
img = Image.open(r'C:\Users\Cyrus\Desktop\Bacterial-Blight-angular-shape.jpg')
#
#
image_base64 = imge_to_base64(img)

# 制作指定的数据格式
data = {'image_base64': image_base64}
# 向服务端地址 http://0.0.0.0:8080/mobilenet 发送数据
r = requests.post('http://127.0.0.1:8080/predict/', data=data)
# 打印从服务端获得的结果
print(r.text)


# def model_predict(img):
#     img = utils.img_to_array(img.resize((227,227)))
#     test_img = tf.expand_dims(img, 0)
#     print(test_img.shape)
#     scores = model.predict(test_img) # 预测
#     # scores = model_predict(img)
#     scores2 = scores[0]
#     scores = scores[0][1]
#
#     print(type(scores))
#     print(scores2)
#     filelist = {}
#     for i in range(len(class_names)):
#         filelist[class_names[i]] = scores2[i]
#     print(class_names[np.argmax(scores)],':',scores)
#     print(filelist)

# model_predict(img=img)