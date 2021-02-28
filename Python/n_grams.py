# -*- coding: utf-8 -*-
"""
Created on Sun Jan 10 04:57:34 2021

@author: buse
"""

# This is a sample Python script.

# Press Shift+F10 to execute it or replace it with your code.
# Press Double Shift to search everywhere for classes, files, tool windows, actions, and settings.

import codecs
from timeit import default_timer as timer
import operator
import re
import string

#ngrams function
def ngrams(n, str):
    frequency = dict()
    words = str.split(' ')
    for i in range(0, len(words) - n + 1):
        word = concat(words, i, i + n)
        if word in frequency.keys():
            frequency[word] = frequency.get(word) + 1
        else:
            frequency[word] = 1

    sort_frequency = dict(sorted(frequency.items(), key=operator.itemgetter(1), reverse=True))
    return sort_frequency


def concat(words, start, end):
    sb = ''
    for i in range(start, end):
        if i > start:
            sb = sb + " " + words[i]
        else:
            sb = sb + "" + words[i]
    return sb

#reading files
text = ''
filename = ["UNUTULMUŞ DİYARLAR.txt", "BİLİM İŞ BAŞINDA.txt", "BOZKIRDA.txt", "DEĞİŞİM.txt", "DENEMELER.txt"]
for file in filename:
    with codecs.open(file, 'r', encoding='utf8') as f:
        temp = f.read()
        text = text + temp
        text = text.replace("\r\n", "")

#and separating punctuation
text = re.sub('(?<! )(?=[' + string.punctuation + '])|(?<=[' + string.punctuation + '])(?! )', r' ', text)
text = re.sub('([»,«,-,“,’])', r' \1 ', text)
text = text.replace("! . .", "!..").replace("? . .", "?..")
text = text.replace("( ? )", "(?)").replace("( ! )", "(!)").replace("( * )", "(*)")
text = text.replace(". . .", "...").replace(". .", "..")
text = re.sub('\s+', ' ', text)
text = text.replace("Ü", "ü").replace("İ", "i").replace("Ö", "ö").replace("Ü", "ü").replace("Ş", "ş").replace("Ğ","ğ").replace("Ç", "ç")
text = text.lower()

def runtime (n,text):
    start = timer()
    fullstart = start
    frequency = ngrams(n, text)
    end = timer()
    print(n,'-Gram Runtime: ', (end - fullstart) * 1000,'miliseconds')
    first100pairs = {k: frequency[k] for k in list(frequency)[:100]}
    i = 1
    for k, v in first100pairs.items():
        print(i, ".VALUE --> ", k, " FREQUENCY --> ", v)
        i = i + 1
    print()


runtime(1,text)
runtime(2,text)
runtime(3,text)
k=input("press close to exit") 