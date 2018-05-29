
# -*- coding: utf-8 -*-

import csv

from pymongo import MongoClient
conn = MongoClient('localhost', 27017)
db = conn.datamining



filename = 'D:/data/08.csv'
result_dict={}
i=0
with open(filename) as f:
    reader = csv.reader(f)
    # 读取一行，下面的reader中已经没有该行了
    head_row = next(reader)
    for row in reader:
 	    u = dict(loc = row[2], time=row[1],Pid=row[0])
 	    db.col08.insertd;
 	    print (i)
 	    i+=1
 		
 		



