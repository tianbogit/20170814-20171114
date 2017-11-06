# -*- coding: utf-8 -*-

# Define here the models for your scraped items
#
# See documentation in:
# http://doc.scrapy.org/en/latest/topics/items.html

import scrapy


class DestItem(scrapy.Item):   
    Title = scrapy.Field()
    Describe = scrapy.Field()
    BasePrice = scrapy.Field()
    ImgUrl = scrapy.Field()
