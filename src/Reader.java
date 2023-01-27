import org.apache.pdfbox.text.*;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.IOException;

public class Reader {
    private String chemin;
    private String contenu;

    public Reader(String chemin){
        this.chemin = chemin;
        this.contenu = "empty";
    }

    public void readFile(){
        File pdf = new File(chemin) ;
        try {
            PDDocument document = PDDocument.load(pdf);
            PDFTextStripper pdfStripper = new PDFTextStripper();
            contenu = pdfStripper.getText(document);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public String getText(){
        return contenu;
    }
}
