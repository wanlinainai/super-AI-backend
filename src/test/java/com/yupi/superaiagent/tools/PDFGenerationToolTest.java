package com.yupi.superaiagent.tools;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Zhangxh
 * @Description
 * @date 2025/06/21 11:19
 */
@SpringBootTest
class PDFGenerationToolTest {

    @Test
    void generatePDF() {
        PDFGenerationTool tool = new PDFGenerationTool();
        String fileName = "哈哈.pdf";
        String content = "《红楼梦》是由中央电视台、中国电视剧制作中心出品，王扶林执导， 曹雪芹原著 [8]，周雷、周岭、刘耕路编剧，欧阳奋强、陈晓旭、张莉、邓婕、孙梦泉、李志新、杨俊勇、吴晓东、王羊、战爱霞等主演的古装电视剧。 [11] [36]\n" +
                "该剧改编自中国古典文学名著《红楼梦》，以贾宝玉与薛宝钗、林黛玉的爱情婚姻悲剧为主线，讲述了贾、薛、王、史四大家族兴衰的过程，折射出世间百态、各人物的故事 [1] [5\n" +
                "]。\n" +
                "1987年春节期间在中央台试播前6集 [25]，同年5月2日起，正式播出全剧 [6]。本剧播出后，得到了大众的一致好评，被誉为“中国电视史上的绝妙篇章”和“不可逾越的经典” [9]。1986年，该剧获得第7届中国电视剧飞天奖特等奖 [23]；1987年，获得第五届中国电视金鹰奖优秀连续剧奖 [22]。";
        String result = tool.generatePDF(fileName, content);
        assertNotNull(result);
    }
}