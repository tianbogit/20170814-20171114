# -*- coding: utf-8 -*-
import scrapy
from selenium import webdriver
from tripspider.items import DestItem


class tripSpider(scrapy.Spider):
    name = 'xiecheng'
    allowed_domains = ['flights.ctrip.com']
    start_urls = ['https://www.mafengwo.cn/sales/0-0-0-0-0-0-0-0.html?group=4']

    def __init__(self):
        # use any browser you wish
        self.browser = webdriver.Chrome()  # 使用前请安装对应的webdriver

    def __del__(self):
        self.browser.close()

    def parse(self, response):
        self.browser.get(response.url)
        lis = self.browser.find_elements_by_xpath("//a[@class='item clearfix']")
        print(len(lis))
        for li in lis:
            item = DestItem()
            item['Title'] = li.find_element_by_xpath("div[2]/div[1]/h3").text
            item['Remark'] = li.find_element_by_xpath('div[2]/div[1]/div/span[1]').text
            item['ImgUrl'] = li.find_element_by_xpath('div[1]/img').get_attribute("src")
            item['BasePrice'] = li.find_element_by_xpath('div[2]/div[2]/span/strong').text
            item['TripType'] = li.find_element_by_xpath('div[1]/span/span').text
            yield item



            


            

