import csv
import os
import numpy as np
from sklearn.cross_validation import train_test_split
from sklearn.cross_validation import cross_val_score
from sklearn import svm
from sklearn import preprocessing

def preprocess(windowSize = 6):
    root = os.getcwd() + '/raw/'
    files = os.listdir(root)
    with open('processed.csv', 'w') as f2:
        writer = csv.writer(f2)
        label = 1
        for file in files:
            if not file.endswith('.csv'):
                continue
            with open(root + file, 'r') as f:
                reader = csv.reader(f)
                cache = []
                count = 0
                for row in reader:
                    ls = []
                    try:
                        for i in range(3, 6):
                            element = row[i]
                            ls.append(float(element))

                        cache.append(ls)

                        if count % windowSize == 0:
                            processBatch(cache, writer, label)
                            cache.clear()

                    except Exception:
                        pass
                    finally:
                        count += 1

                processBatch(cache, writer, label)
            print(file, label)
            label += 1


def processBatch(cache, writer, label):
    if len(cache) == 0:
        return
    data = generateData(cache, label)
    writer.writerow(data)


def normalize(v):
    norm = np.linalg.norm(v)
    if norm == 0:
        return v
    return v / norm


def generateData(cache, label):
    r = []
    matrix = np.array(cache)
    for i in range(0, len(cache[0])):
        col = matrix[:,i]
        #col = normalize(col)
        r.append(col.mean())
    r.append(label)

    return r


def classify():
    raw = np.genfromtxt('processed.csv', delimiter=',')
    np.random.shuffle(raw)
    data = raw[:, 0:-1]
    labels = raw[:, -1]
    clf = svm.SVC()

    clf.fit(data, labels)
    testData = np.genfromtxt('raw/team9_assignment7_sleeping.csv', delimiter=',')
    tData = testData[:,3:6]
    #tData = preprocessing.normalize(tData, axis = 0)
    ls = []
    for i in range(0, tData.shape[0]):
        ls.append(4)
    tLabels = np.array(ls)

    print(1 - clf.score(tData, tLabels))



if __name__ == '__main__':
    #preprocess()
    classify()
    pass