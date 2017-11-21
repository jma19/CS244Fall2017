import csv
import numpy as np
from sklearn.cross_validation import train_test_split
from sklearn.cross_validation import cross_val_score
from sklearn import svm
from sklearn import preprocessing


def preprocess(windowSize = 0.12):
    allData = []
    with open('as5csv.csv', 'r') as f:
        reader = csv.reader(f)
        timeStart = 0.0
        cache = {1:[], 2:[], 3:[], 4:[], 5:[]}
        for row in reader:
            ls = []
            curTime = 0.0
            try:
                for i in range(0, len(row)):
                    element = row[i]
                    if i == 0:
                        curTime = float(element)
                        continue
                    if i % 4 != 0:
                        ls.append(float(element))
                    else:
                        ls.append(int(element))
                        cache[int(element)].append(ls)
                        ls = []

                if curTime - timeStart >= windowSize:
                    processBatch(cache, allData)
                    timeStart = curTime
                    for k, v in cache.items():
                        v.clear()

            except Exception:
                pass
        processBatch(cache, allData)

    allData = np.array(allData)
    labels = allData[:,-1]
    allData = allData[:,0:-1]
    allData = preprocessing.normalize(allData, axis = 0)
    with open('processed.csv', 'w') as f2:
        writer = csv.writer(f2)
        for i in range(0, allData.shape[0]):
            row = allData[i].tolist()
            row.append(labels[i])
            writer.writerow(row)


def processBatch(cache, allData):
    data = generateData(cache)
    allData += data


def normalize(v):
    norm = np.linalg.norm(v)
    if norm == 0:
        return v
    return v / norm


def generateData(cache):
    r = []
    for k, v in cache.items():
        row = []
        matrix = np.array(v)
        for i in range(0, 3):
            col = matrix[:,i]
            row.append(col.mean())
        row.append(k)
        r.append(row)
    return r


def classify():
    raw = np.genfromtxt('processed.csv', delimiter=',')
    np.random.shuffle(raw)
    data = raw[:, 0:-1]
    labels = raw[:, -1]
    clf = svm.SVC()

    # scores = cross_val_score(clf, data, labels, cv=5)
    # print(scores)
    # print("Accuracy: %0.8f (+/- %0.8f)" % (scores.mean(), scores.std() * 2))

    clf.fit(data, labels)
    testData = np.genfromtxt('team9_assignment6.csv', delimiter=',')
    tData = testData[:,2:5]
    tData = preprocessing.normalize(tData, axis = 0)
    tLabels = np.ones(tData.shape[0], dtype=np.int)

    print(1 - clf.score(tData, tLabels))




if __name__ == '__main__':
    #preprocess()
    classify()
    pass