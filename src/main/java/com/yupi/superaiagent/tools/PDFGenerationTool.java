package com.yupi.superaiagent.tools;

import cn.hutool.core.io.FileUtil;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.yupi.superaiagent.constant.FileConstant;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;



/**
 * @author Zhangxh
 * @Description PDF 自动生成工具
 * @date 2025/06/21 11:09
 */
public class PDFGenerationTool {
    @Tool(description = "Generate a pdf file with given content")
    public String generatePDF(
            @ToolParam(description = "Name of the file to save the generated PDF") String fileName,
            @ToolParam(description = "Content to be included in the PDF") String content) {
        String fileDir = FileConstant.FILE_SAVE_PATH + "/pdf";
        String filePath = fileDir + "/" + fileName;
        try {
            // 创建目录
            FileUtil.mkdir(fileDir);
            // 创建PdfWriter 和 PdfDocument对象
            try(PdfWriter writer = new PdfWriter(filePath);
                PdfDocument pdfDocument = new PdfDocument(writer);
                Document document = new Document(pdfDocument)) {


                // 自定义字体
//                String fontPath = Paths.get("src/main/resource/static/fonts/simsun.ttf")
//                        .toAbsolutePath().toString();
//                PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H");
                // 使用内置字体
                PdfFont font = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H");
                document.setFont(font);

                // 创建段落
                Paragraph paragraph = new Paragraph(content);

                // 添加段落并关闭文档
                document.add(paragraph);
            }
            return "PDF generated successfully to:" + filePath;
        } catch (Exception e) {
            return "Error generating PDF:" + e.getMessage();
        }

    }
}
