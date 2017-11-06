# -*- coding: utf-8 -*-
import scrapy
from tripspider.items import DestItem


class LianjiaSpider(scrapy.Spider):
    name = 'xiecheng'
    allowed_domains = ['flights.ctrip.com']
    start_urls = ['http://piao.ctrip.com/?allianceid=710653&ouid=000401app-&utm_medium=&utm_campaign=&utm_source=&isctrip=&Allianceid=710653&sid=1251259&OUID=&MultiUnionSupport=true']

    def parse(self, response):
        lis=response.xpath('//*[@id="ticket_list"]/ul/li')        
        print(len(lis))
        for li in lis:
            item = DestItem()
            item['Title'] =  li.xpath('a/h3/text()').extract()
            item['Describe'] = li.xpath('a/p/@title').extract()
            basePrice = li.xpath('a/p[2]/em/strong/text()').extract()

            item['BasePrice'] = basePrice.strip('\r\n')
            print(basePrice.strip('\r\n'))
            item['ImgUrl'] = li.xpath('a/img/@src').extract()
            yield item



            


            

