# -*- coding: utf-8 -*-
import scrapy


class StackoverflowSpider(scrapy.Spider):
    name = 'stackoverflow'
    allowed_domains = ['stackoverflow.com']
    start_urls = ['http://stackoverflow.com/questions?sort=votes/']

    def parse(self, response):
        print(22)
        for href in response.css('.question-summary h3 a::attr(href)'):
            full_url=response.urljoin(href.extract())
            yield scrapy.Request(full_url,callback=self.parse_question)
    
    def parse_question(self,response):
        yield{
            'title':response.css('h1 a::text').extract()[0],
            'votes':response.css('.question .vote-count-post::text').extract()[0],
            'link':response.url,
        }

