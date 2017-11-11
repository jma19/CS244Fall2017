import csv
import numpy as np
from sklearn.cross_validation import train_test_split
from sklearn.cross_validation import cross_val_score
from sklearn import svm


def preprocess(windowSize = 0.12):
    with open('as5csv.csv', 'r') as f:
        with open('processed.csv', 'w') as f2:
            reader = csv.reader(f)
            writer = csv.writer(f2)
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
                        processBatch(cache, writer)
                        timeStart = curTime
                        for k, v in cache.items():
                            v.clear()

                except Exception:
                    pass
            processBatch(cache, writer)


def processBatch(cache, writer):
    data = generateData(cache)
    for row in data:
        writer.writerow(row)


def generateData(cache):
    r = []
    for k, v in cache.items():
        row = []
        matrix = np.array(v)
        for i in range(0, 3):
            col = matrix[:,i]
            row.append(col.mean())
            row.append(col.var())
        row.append(k)
        r.append(row)
    return r


def classify():
    raw = np.genfromtxt('processed.csv', delimiter=',')
    np.random.shuffle(raw)
    data = raw[:, 0:-1]
    labels = raw[:, -1]
    clf = svm.SVC()
    scores = cross_val_score(clf, data, labels, cv=5)
    print(scores)
    print("Accuracy: %0.8f (+/- %0.8f)" % (scores.mean(), scores.std() * 2))
    # data_train, data_test, labels_train, labels_test = train_test_split(data, labels, test_size=0.20)
    #
    # clf.fit(data_train, labels_train)
    # print(clf.score(data_test, labels_test))




if __name__ == '__main__':
    #preprocess()
    classify()
    pass