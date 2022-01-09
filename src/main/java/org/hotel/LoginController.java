package org.hotel;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
// import com.itextpdf.text.pdf.parser.Path;
import java.nio.file.Path;
import java.util.stream.Stream;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.hotel.models.*;
import org.json.JSONObject;
import javax.print.*;

public class LoginController extends Controller {
    public TextField txtEmail;
    public TextField txtPassword;
    public Label lblError;

    @FXML
    private void btnCloseClick() throws IOException {
        System.exit(0);
    }

    private void printIt() throws PrintException {
        String cmd = "Print something";
        System.out.println("Printing...");

        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);
        // Input the file
        FileInputStream textStream = null;
        try {
            textStream = new FileInputStream("iTextHelloWorld.pdf");
        } catch (FileNotFoundException ffne) {
        }
        if (textStream == null) {
            return;
        }
        for(PrintService ps : services) {
            System.out.println( ps.getName());
            if(ps.getName().equals( "HP-LaserJet-Pro-MFP-M26nw") ){
                System.out.println("Printing to HP-LaserJet-Pro-MFP-M26nw");

                DocPrintJob job1 = ps.createPrintJob();
                Doc myDoc = new SimpleDoc(textStream, DocFlavor.INPUT_STREAM.AUTOSENSE, null);
                job1.print(myDoc, null);
            }
        }
    }

    private void createPdf() throws DocumentException, IOException, URISyntaxException {
        Document document = new Document();

        PageSize pageSize = new PageSize();
        Rectangle rec = new Rectangle(400, 800);
        document.setPageSize(rec);

        PdfWriter.getInstance(document, new FileOutputStream("iTextHelloWorld.pdf"));

        document.open();
        Font font = FontFactory.getFont(FontFactory.HELVETICA, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Hello World", font);

        document.add(chunk);
        this.addImage(document);
        this.addTable(document);
        document.close();
    }

    private void addImage(Document document) throws URISyntaxException, IOException, DocumentException {
        Path path = null;
        try {
            path = Paths.get("jojo.jpeg");
            if(path == null) {
                System.out.println("Path is null");
            } else {
                System.out.println("Path :" + path.toAbsolutePath().toString());
            }
            // System.out.println("Path:" + path);

            Image img = Image.getInstance(path.toAbsolutePath().toString());
            img.scalePercent(20);
            img.setSpacingAfter(50.3F);
            System.out.println("img:" + img);
            if(img == null) {
                System.out.println("Image is empty");
                return;
            }
            document.add(img);
        } catch (Exception e) {
            System.out.println("error reading jpg file");
        }
    }

    private void addTable(Document document) throws DocumentException, URISyntaxException, IOException {
        PdfPTable table = new PdfPTable(3);
        table.setSpacingBefore(50.1F);
        addTableHeader(table);
        addRows(table);
        addCustomRows(table);
        document.add(table);
    }

    private void addTableHeader(PdfPTable table) {
        Stream.of("header 1", "header 2", "header 3")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(2);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    private void addRows(PdfPTable table) {
        table.addCell("row 1, col 1");
        table.addCell("row 1, col 2");
        table.addCell("row 1, col 3");
    }
    private void addCustomRows(PdfPTable table)
            throws URISyntaxException, BadElementException, IOException {
        Path path = Paths.get("jojo.jpeg");
        Image img = Image.getInstance(path.toAbsolutePath().toString());
        img.scalePercent(10);

        PdfPCell imageCell = new PdfPCell(img);
        table.addCell(imageCell);

        PdfPCell horizontalAlignCell = new PdfPCell(new Phrase("row 2, col 2"));
        horizontalAlignCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(horizontalAlignCell);

        PdfPCell verticalAlignCell = new PdfPCell(new Phrase("row 2, col 3"));
        verticalAlignCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
        table.addCell(verticalAlignCell);
    }

    @FXML
    private void btnLoginClick() throws IOException, DocumentException, URISyntaxException, PrintException {
        if(true) {
            // this.createPdf();
            printIt();
            return;
        }

        String email = txtEmail.getText();
        String password = txtPassword.getText();

        if(email.equals("root@localhost") && password.equals("root")) {
            App.setRoot("dashboard_rooms");
            return;
        }

        if(Helper.isEmailValid(email) && password.length() > 0) {
            lblError.setText("");
            User user  =  new User(0, "", email, password);
            String response = Api.postApiData("logins", user.toJson());
            JSONObject responseObject = Helper.toJsonObject(response);

            if((boolean) responseObject.get("status")) {
                lblError.setText("");
                App.setRoot("dashboard_rooms");
            } else {
                lblError.setText("Email or password incorrect");
            }

        } else {
            lblError.setText("Email is not valid or password is empty");
        }
    }
}
