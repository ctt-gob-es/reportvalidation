package es.oaw.irapvalidator.pdfutils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import es.oaw.irapvalidator.Constants;
import es.oaw.irapvalidator.controller.FileUploadController;
import es.oaw.irapvalidator.validator.ValidationError;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PdfReportGenerator {

    private static final Font categoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private static final Font subcategoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);

    public static Path generateValidationResultPDF(List <String> validFiles, List<FileUploadController.ErrorInfo> invalidFiles){
        Document document = new Document();

        Path root = Paths.get(Constants.PDF_FOLDER);
        Path filepath = root.resolve("ResultadosValidacion.pdf");
        String path = filepath.toAbsolutePath().toString();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(path));
            document.open();
            int chapter = 1;

            //Ficheros válidos
            Anchor anchor = new Anchor("Ficheros validados", categoryFont);
            Chapter catPart = new Chapter(new Paragraph(anchor), chapter);

            if (validFiles.size() != 0){
                Paragraph paragraph = new Paragraph("Los siguientes ficheros han sido validados satisfactoriamente: ");
                addEmptyLine(paragraph, 1);
                // Creating a list
                com.itextpdf.text.List list = new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
                for (String file: validFiles){
                    list.add(new ListItem(file));
                }
                catPart.add(paragraph);
                catPart.add(list);
            }
            else{
                Paragraph paragraph = new Paragraph("Níngún fichero validado satisfactoriamente.");
                catPart.add(paragraph);
            }
            document.add(catPart);

            // Ficheros no válidos
            chapter = 2;
            for (FileUploadController.ErrorInfo file: invalidFiles) {
                // File category
                anchor = new Anchor("Fichero: " + file.getFilename(), categoryFont);
                anchor.setName(file.getFilename());
                catPart = new Chapter(new Paragraph(anchor), chapter);

                for (Map.Entry<String, List<ValidationError>> entry : file.getErrors().entrySet()) {
                    // Sheet subcategory
                    Paragraph subPara = new Paragraph("Hoja: " + entry.getKey(), subcategoryFont);
                    Section subCatPart = catPart.addSection(subPara);

                    Paragraph emptyLines = new Paragraph();
                    addEmptyLine(emptyLines, 1);
                    subCatPart.add(emptyLines);

                    // Error table
                    if (entry.getValue().size() != 0){
                        PdfPTable errorTable = createTable(entry.getValue());
                        subCatPart.add(errorTable);
                    }
                    else{
                        Paragraph paragraph = new Paragraph("Hoja validada correctamente.");
                        subCatPart.add(paragraph);
                    }

                    //separation between subsections
                    Paragraph paragraph = new Paragraph();
                    addEmptyLine(paragraph, 1);
                    subCatPart.add(paragraph);

                }
                document.add(catPart);
                chapter++;
            }
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filepath;
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
        }
    }

    private static PdfPTable createTable(List<ValidationError> errors) {
        int numColumns = 2;
        PdfPTable table = new PdfPTable(numColumns);

        PdfPCell c1 = new PdfPCell(new Phrase("Celda"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);

        c1 = new PdfPCell(new Phrase("Error"));
        c1.setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell(c1);
        table.setHeaderRows(1);

        errors.forEach(error -> {
            table.addCell(error.getCell());
            table.addCell(error.getMessage());
        });
        return table;
    }
}
