# -*- coding: utf-8 -*-

# Define your item pipelines here
#
# Don't forget to add your pipeline to the ITEM_PIPELINES setting
# See: http://doc.scrapy.org/en/latest/topics/item-pipeline.html

import pymysql

def dbHandle():
    conn = pymysql.connect(
        host='101.200.154.139',
        user='***',
        passwd='***',
        charset='utf8',
        use_unicode=False
    )
    return conn


class tripPipeline(object):
    def process_item(self, item, spider):
        dbObject = dbHandle()
        cursor = dbObject.cursor()
        sql = 'insert into trip.products(Title,Remark,TripType,Amount,ImgUrl) values (%s,%s,%s,%s,%s)'

        try:
            cursor.execute(sql,
                           (item['Title'],item['Remark'], item['TripType'], item['BasePrice'], item['ImgUrl']))
            dbObject.commit()
        except Exception as e:
            print(e)
            dbObject.rollback()

        return item
