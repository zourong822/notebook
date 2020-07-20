package com.leyou.service.impl;

import com.leyou.page.service.impl.PageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PageServiceImplTest {

    @Autowired
    private PageServiceImpl pageService;
    @Test
    void staticHtmlTest() {
        pageService.staticHtml(141L);
    }
}