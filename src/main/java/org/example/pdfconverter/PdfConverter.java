package org.example.pdfconverter;

import org.apache.pdfbox.TextToPDF;
import org.apache.pdfbox.exceptions.COSVisitorException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.edit.PDPageContentStream;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDPixelMap;
import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.Scanner;

/**
 * Created by hadesgames on 7/1/13.
 */
public class PdfConverter {

    public static PDDocument imagesToPdf(List<String> files) throws IOException {
        PDDocument document = new PDDocument();

        for (String img: files) {
           addImage(document, img);
        }

        return document;
    }
    private static void addImage(PDDocument document,String filename) throws IOException {
        // Load Image from file
        BufferedImage awtImage = ImageIO.read(new File(filename));
        PDXObjectImage ximage = new PDPixelMap(document, awtImage);

        // Create Page

        PDPage page = new PDPage(PDPage.PAGE_SIZE_A4);
        // Create content stream

        PDPageContentStream stream = new PDPageContentStream(document, page);

        stream.drawXObject(ximage, 0, 0, PDPage.PAGE_SIZE_A4.getWidth(), PDPage.PAGE_SIZE_A4.getHeight() );
        stream.close();

        document.addPage(page);
    }

    public static PDDocument textToPdf(String text) throws IOException, COSVisitorException {
       PDDocument doc = new TextToPDF().createPDFFromText(new StringReader(text));

       return doc;
    }

    /*
       Was used for testing.


    private static void testImage() throws IOException, COSVisitorException {
        PDDocument document = new PDDocument();

        addImage(document, "bla_1.jpg");
        addImage(document, "bla_2.jpg");

        document.save("bla.pdf");
        document.close();
    }

    private static void testText () throws IOException, COSVisitorException {
        String content = new Scanner(new File("bla.txt"), "UTF-8").useDelimiter("\\A").next();
        PDDocument doc = addText(content);

        doc.save("bla.pdf");
        doc.close();

    }

    public static void main(String[] args) throws IOException, COSVisitorException {
        testText();
        //testImage();
    }
    */
}
