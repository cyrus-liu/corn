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

os.environ['TF_CPP_MIN_LOG_LEVEL'] = '2'
if not os.path.exists('./logs'):
    os.mkdir('./logs')
elif not os.path.exists('./model'):
    os.mkdir('./model')
elif not os.path.exists('./dataset'):
    os.mkdir('./dataset')
# tf.random.set_seed(2345)
current_time = datetime.datetime.now().strftime('%Y%m%d-%H%H%S')
log_dir = './logs/' + current_time
dadtapath = r'./dataset/Rice Leaf Disease Images'

Pre_training = False
# 创建dataset
BATCH_SIZE = 24
HEIGHT = 227
WIDTH = 227



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
    layers.experimental.preprocessing.Rescaling(1./255,input_shape=(HEIGHT,WIDTH,3)), # 数据归一化
    layers.Conv2D(64, kernel_size=3, padding='same', activation='relu'),
    layers.Conv2D(64, kernel_size=3, padding='same', activation='relu'),
    layers.BatchNormalization(),
    layers.MaxPool2D(pool_size=2, strides=2),

    layers.Conv2D(128, kernel_size=3, padding='same', activation='relu'),
    layers.Conv2D(128, kernel_size=3, padding='same', activation='relu'),
    layers.BatchNormalization(),
    layers.MaxPool2D(pool_size=2, strides=2),

    layers.Conv2D(256, kernel_size=3, padding='same', activation='relu'),
    layers.Conv2D(256, kernel_size=3, padding='same', activation='relu'),
    layers.BatchNormalization(),
    layers.MaxPool2D(pool_size=2, strides=2),

    layers.Conv2D(512, kernel_size=3, padding='same', activation='relu'),
    layers.Conv2D(512, kernel_size=3, padding='same', activation='relu'),
    layers.BatchNormalization(),
    layers.MaxPool2D(pool_size=2, strides=2),

    layers.Conv2D(512, kernel_size=3, padding='same', activation='relu'),
    layers.Conv2D(512, kernel_size=3, padding='same', activation='relu'),
    layers.BatchNormalization(),
    layers.MaxPool2D(pool_size=2, strides=2),

    layers.Flatten(),
    layers.Dense(256, activation=tf.nn.relu),
    layers.Dense(128, activation=tf.nn.relu),
    layers.Dense(5, activation=tf.nn.softmax)

]

AlexNet = [
    layers.experimental.preprocessing.Rescaling(1./255,input_shape=(HEIGHT,WIDTH,3)), # 数据归一化
    layers.Conv2D(48,(11,11),strides=4,padding='same',activation='relu'),
    layers.BatchNormalization(),
    layers.MaxPool2D((3,3),strides=2,padding='same'),

    layers.Conv2D(128, (5, 5), padding='same', activation='relu'),
    layers.BatchNormalization(),
    layers.MaxPool2D((3, 3), strides=2,padding='same'),

    layers.Conv2D(192,(3,3),padding='same',activation='relu'),

    layers.Conv2D(192, (3, 3), padding='same', activation='relu'),

    layers.Conv2D(128,(3,3),padding='same',activation='relu'),
    layers.MaxPool2D((3,3),strides=2),

    layers.Flatten(),

    layers.Dense(1024,activation='relu'),
    layers.Dropout(0.25),

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

tain_db = image_dataset_from_directory(directory=dadtapath,batch_size=BATCH_SIZE,validation_split=0.2,
                                       subset='training',seed=666,image_size=(HEIGHT,WIDTH))
test_db = image_dataset_from_directory(directory=dadtapath,batch_size=BATCH_SIZE,validation_split=0.2,
                                       subset='validation',seed=666,image_size=(HEIGHT,WIDTH))
class_names = tain_db.class_names # 数据集类型
print(class_names)
def main():
    tb_callback = TensorBoard(log_dir,update_freq=1)
    model_checkpoint_callback = ModelCheckpoint(filepath=os.path.join('./model/',current_time+'.hdf5'),monitor='val_accuracy',verbose=1
                                                ,save_best_only=True)
    optimizer = optimizers.Adam(lr=1e-4)
    if not Pre_training:

        alex_net = models.Sequential(AlexNet)
        alex_net.build(input_shape=[None,HEIGHT,WIDTH,3])
    else:
        alex_net = models.load_model('./model/*.hdf5')
    alex_net.compile(optimizer=optimizer,
              loss=tf.losses.SparseCategoricalCrossentropy(from_logits=False),
              metrics=['accuracy'])
    print(alex_net.summary())
    history = alex_net.fit(tain_db,epochs=60,validation_data=test_db,callbacks=[tb_callback,model_checkpoint_callback])


if __name__ == '__main__':
    main()
