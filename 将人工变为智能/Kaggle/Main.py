import pandas as pd
from sklearn.model_selection import train_test_split
from sklearn.linear_model import LogisticRegression
from sklearn.metrics import classification_report

import warnings
warnings.filterwarnings('ignore')

if __name__ == '__main__':
    # 读取数据
    df = pd.read_csv('./input/diabetes.csv')
    # print(df.head())
    # print(df.shape)
    # print(df.describe())

    # 将实际结果分割出来
    x = df.drop('Outcome', axis=1)
    y = df['Outcome']

    # 切分训练集和测试集
    x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=0.2, random_state=0)

    # 逻辑回归
    model = LogisticRegression()

    # 拟合模型
    model.fit(x_train,y_train)

    # 预测
    y_pred = model.predict(x_test)

    # 查看预测结果
    print(f"预测结果:{y_pred}")

    f = open('./output/result.txt', mode='w')

    for x in range(0,len(y_pred)):
        tmp = 'Actual: ' + str(y_test.values[x]) + ' Predicted: ' + str(y_pred[x]) + '\n'
        print(tmp)
        f.write(tmp)

    report = classification_report(y_test, y_pred)
    print('\nreport:')
    print(report)

    # 将结果写入文件
    f.write(report)
    f.close()

