import datetime

import tensorflow as tf
from tensorflow.keras import layers
from tensorflow.keras import optimizers
from tensorflow.keras import models
from tensorflow.keras import datasets
from tensorflow.keras.callbacks import TensorBoard
from tensorflow.keras.callbacks import ModelCheckpoint
from tensorflow.keras.utils import image_dataset_from_directory
import os
# 关掉tensorflow的红色信息提示
os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'
# 检测文件夹是否存在，不存在就创建
if not os.path.exists('logs'):
    os.mkdir('logs')
elif not os.path.exists('model'):
    os.mkdir('model')
elif not os.path.exists('dataset'):
    os.mkdir('dataset')
# tf.random.set_seed(2345)
# 设置时间
current_time = datetime.datetime.now().strftime('%Y%m%d-%H%H%S')
# 日志路径
log_dir = './logs/' + current_time
# 数据集的路径
dadtapath = r'./dataset/Rice Leaf Disease Images'
# 超参数设置
Pre_training = False
BATCH_SIZE = 24
HEIGHT = 227
WIDTH = 227

# 数据增强层
data_argumentation = tf.keras.Sequential([
    layers.experimental.preprocessing.RandomFlip('horizontal', input_shape=(HEIGHT,WIDTH,3)),
    layers.experimental.preprocessing.RandomRotation(0.1),
    layers.experimental.preprocessing.RandomZoom(0.1)
])

vgg13 = [
    layers.experimental.preprocessing.Rescaling(1./255,input_shape=(HEIGHT,WIDTH,3)), # 数据归一化
    layers.Conv2D(64, kernel_size=[3,3], padding='same',activation=tf.nn.relu),
    layers.Conv2D(64, kernel_size=[3,3], padding='same',activation=tf.nn.relu),
    layers.MaxPool2D(pool_size=[2,2],strides=2,padding='same'),

    layers.Conv2D(128,kernel_size=[3,3],padding='same',activation=tf.nn.relu),
    layers.Conv2D(128, kernel_size=[3, 3], padding='same', activation=tf.nn.relu),
    layers.MaxPool2D(pool_size=[2, 2], strides=2, padding='same'),

    layers.Conv2D(256, kernel_size=[3, 3], padding='same', activation=tf.nn.relu),
    layers.Conv2D(256, kernel_size=[3, 3], padding='same', activation=tf.nn.relu),
    layers.MaxPool2D(pool_size=[2, 2], strides=2, padding='same'),

    layers.Conv2D(512, kernel_size=[3, 3], padding='same', activation=tf.nn.relu),
    layers.Conv2D(512, kernel_size=[3, 3], padding='same', activation=tf.nn.relu),
    layers.MaxPool2D(pool_size=[2, 2], strides=2, padding='same'),

    layers.Conv2D(512, kernel_size=[3, 3], padding='same', activation=tf.nn.relu),
    layers.Conv2D(512, kernel_size=[3, 3], padding='same', activation=tf.nn.relu),
    layers.MaxPool2D(pool_size=[2, 2], strides=2, padding='same'),

    layers.Flatten(),
    layers.Dense(256,activation=tf.nn.relu),
    layers.Dense(128,activation=tf.nn.relu),
    layers.Dense(5,activation=tf.nn.softmax)

]


vgg13after = [
    data_argumentation,
    #layers.experimental.preprocessing.Rescaling(1./255,input_shape=(HEIGHT,WIDTH,3)), # 数据归一化
    # 第一块 conv1_1-conv1_2-pool1
    layers.Conv2D(64, kernel_size=3, padding='same', activation='relu'),
    layers.Conv2D(64, kernel_size=3, padding='same', activation='relu'),
    layers.BatchNormalization(),
    layers.MaxPool2D(pool_size=2, strides=2),
    # 第二块 conv2_1-conv2_2-pool2
    layers.Conv2D(128, kernel_size=3, padding='same', activation='relu'),
    layers.Conv2D(128, kernel_size=3, padding='same', activation='relu'),
    layers.BatchNormalization(),
    layers.MaxPool2D(pool_size=2, strides=2),
    # 第三块 conv3_1-conv3_2-conv3_3-pool3
    layers.Conv2D(256, kernel_size=3, padding='same', activation='relu'),
    layers.Conv2D(256, kernel_size=3, padding='same', activation='relu'),
    layers.BatchNormalization(),
    layers.MaxPool2D(pool_size=2, strides=2),
    # 第四块 conv4_1-conv4_2-conv4_3-pool4
    layers.Conv2D(512, kernel_size=3, padding='same', activation='relu'),
    layers.Conv2D(512, kernel_size=3, padding='same', activation='relu'),
    layers.BatchNormalization(),
    layers.MaxPool2D(pool_size=2, strides=2),
    # 第五块 conv5_1-conv5_2-conv5_3-pool5
    layers.Conv2D(512, kernel_size=3, padding='same', activation='relu'),
    layers.Conv2D(512, kernel_size=3, padding='same', activation='relu'),
    layers.BatchNormalization(),
    layers.MaxPool2D(pool_size=2, strides=2),
    # 把pool5 ( [7, 7, 512] )  拉成向量
    layers.Flatten(),
    layers.Dense(256, activation=tf.nn.relu,kernel_regularizer='l2'),
    # 全连接层1 添加了 Droput来防止过拟合
    layers.Dropout(0.25),
    layers.Dense(128, activation=tf.nn.relu,kernel_regularizer='l2'),
    # 全连接层2 添加了 Droput来防止过拟合
    layers.Dropout(0.25),
    layers.Dense(4, activation=tf.nn.softmax)

]

AlexNet = [
    layers.experimental.preprocessing.Rescaling(1./255,input_shape=(HEIGHT,WIDTH,3)), # 数据归一化
    # 第1层：conv1 -> relu -> norm1 -> max pool1
    layers.Conv2D(48,(11,11),strides=4,padding='same',activation='relu'),
    layers.BatchNormalization(),
    layers.MaxPool2D((3,3),strides=2,padding='same'),
    # 第2层：conv2 -> relu -> norm2 -> max pool2
    layers.Conv2D(128, (5, 5), padding='same', activation='relu'),
    layers.BatchNormalization(),
    layers.MaxPool2D((3, 3), strides=2,padding='same'),
    # 第3层：conv3 -> relu
    layers.Conv2D(192,(3,3),padding='same',activation='relu'),
    # 第4层：conv4 -> relu
    layers.Conv2D(192, (3, 3), padding='same', activation='relu'),
    # 第5层：conv5 -> relu -> max pool5
    layers.Conv2D(128,(3,3),padding='same',activation='relu'),
    layers.MaxPool2D((3,3),strides=2),

    layers.Flatten(),
    # 第6层：FC6 -> relu -> dropout 6, 4096个神经元
    layers.Dense(1024,activation='relu'),
    layers.Dropout(0.25),
    # 第7层：FC7 -> relu -> dropout 7, 4096个神经元
    layers.Dense(1024,activation='relu'),
    layers.Dropout(0.25),

    layers.Dense(4,activation='softmax')
]

# (x,y), (x_test,y_test) = datasets.cifar10.load_data()



def preprocess(x,y):
    x = tf.cast(x,dtype=tf.float32) /255.
    y = tf.cast(y,dtype=tf.int32)
    return x,y

# tain_db = tf.data.Dataset.from_tensor_slices((x,y)) # 测试
# tain_db = tain_db.shuffle(1000).map(preprocess).batch(12)
# test_db = tf.data.Dataset.from_tensor_slices((x_test,y_test))
# test_db = test_db.map(preprocess).batch(12)
# 创建数据集
tain_db = image_dataset_from_directory(directory=dadtapath,batch_size=BATCH_SIZE,validation_split=0.2,
                                       subset='training',seed=666,image_size=(HEIGHT,WIDTH))
test_db = image_dataset_from_directory(directory=dadtapath,batch_size=BATCH_SIZE,validation_split=0.2,
                                       subset='validation',seed=666,image_size=(HEIGHT,WIDTH))
class_names = tain_db.class_names # 数据集类型
print(class_names)
def main():
    # 设置张量板
    tb_callback = TensorBoard(log_dir,update_freq=1)
    # 设置模型检查点
    model_checkpoint_callback = ModelCheckpoint(filepath=os.path.join('model/', current_time + '.hdf5'), monitor='val_accuracy', verbose=1
                                                , save_best_only=True)
    # 优化器
    optimizer = optimizers.Adam(lr=1e-4)
    if not Pre_training:
        # 模型创建
        alex_net = models.Sequential(AlexNet)
        alex_net.build(input_shape=[None,HEIGHT,WIDTH,3])
    else:
        # 模型读取
        alex_net = models.load_model('./model/*.hdf5')
    # 模型编译
    alex_net.compile(optimizer=optimizer,
              loss=tf.losses.SparseCategoricalCrossentropy(from_logits=False),
              metrics=['accuracy'])
    # 打印模型结构
    print(alex_net.summary())
    # 训练模型
    history = alex_net.fit(tain_db,epochs=60,validation_data=test_db,callbacks=[tb_callback,model_checkpoint_callback])


if __name__ == '__main__':
    main()
