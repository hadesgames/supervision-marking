package org.example.pdfTools;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.util.PDFMergerUtility;
import org.apache.pdfbox.util.Splitter;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: ap760
 * Date: 02/07/13
 * Time: 10:53
 * To change this template use File | Settings | File Templates.
 */
public class PdfManip {

    public static void main(String[] args) throws IOException {

        String pathName = "/home/ap760/cur/supervision-marking/temp/bla.pdf";  // args[0]

        PDDocument document = PDDocument.load(pathName);

        List listPDF = document.getDocumentCatalog().getAllPages();

        copy(document);

        saveList(listPDF);

        mergeList(listPDF);

        getPage(document, 2);
    }

    public static PDPage getPage(PDDocument document, int pageId)
    {
        try
        {
            List allPages = document.getDocumentCatalog().getAllPages();

            if (pageId >= allPages.size())
                return new PDPage();

            return (PDPage) allPages.get(pageId);
        }
        catch (Exception e)
        {
            System.out.println(e.getStackTrace());

            return new PDPage();
        }
    }

    public static void copy(PDDocument document)
    {
        try {
            document.save("temp/Saved Copy.pdf");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveList(List allPages)
    {

        try {
            PDDocument newDoc = null;

            // Copying and saving pages one by one

            for ( int curPageCnt = 0; curPageCnt < allPages.size(); curPageCnt++ )
            {
                newDoc = new PDDocument();

                newDoc.addPage((PDPage) allPages.get(curPageCnt));
                newDoc.save("temp/Saved Page #" + curPageCnt + ".pdf");
                newDoc.close();
            }
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }

    public static void mergeList(List allPages) {

        try {
            PDDocument newDoc = null;

            newDoc = new PDDocument();

            // Copying pages to new PDF

            for (Object page : allPages)
                newDoc.addPage((PDPage) page);

            newDoc.save("temp/Saved Merge.pdf");
            newDoc.close();
        }
        catch ( Exception e ) {
            e.printStackTrace();
        }
    }
}
