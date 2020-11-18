package es.oaw.irapvalidator.pdfutils;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import com.itextpdf.text.Anchor;
import com.itextpdf.text.Chapter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Section;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import es.oaw.irapvalidator.Constants;
import es.oaw.irapvalidator.controller.FileUploadController;
import es.oaw.irapvalidator.validator.ValidationError;

/**
 * The Class PdfReportGenerator.
 */
@Service
public class PdfReportGenerator {

	/** The Constant TWO_DOTS_SPACE. */
	private static final String TWO_DOTS_SPACE = ": ";

	/** The Constant categoryFont. */
	private static final Font categoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);

	/** The Constant subcategoryFont. */
	private static final Font subcategoryFont = new Font(Font.FontFamily.TIMES_ROMAN, 16, Font.BOLD);

	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	/**
	 * Generate validation result PDF.
	 *
	 * @param validFiles   the valid files
	 * @param invalidFiles the invalid files
	 * @return the path
	 */
	public Path generateValidationResultPDF(List<String> validFiles,
			List<FileUploadController.ErrorInfo> invalidFiles) {
		Document document = new Document();

		Path root = Paths.get(Constants.PDF_FOLDER);
		Path filepath = root.resolve("ResultadosValidacion.pdf");
		String path = filepath.toAbsolutePath().toString();
		try {
			PdfWriter.getInstance(document, new FileOutputStream(path));
			document.open();
			int chapter = 1;

			// Ficheros válidos
			Anchor anchor = new Anchor(
					messageSource.getMessage("file.valid.files", null, LocaleContextHolder.getLocale()), categoryFont);
			Chapter catPart = new Chapter(new Paragraph(anchor), chapter);

			if (validFiles.size() != 0) {

				Paragraph paragraph = new Paragraph(
						messageSource.getMessage("file.valid.files.message", null, LocaleContextHolder.getLocale()));
				addEmptyLine(paragraph, 1);

				// Creating a list
				com.itextpdf.text.List list = new com.itextpdf.text.List(com.itextpdf.text.List.UNORDERED);
				for (String file : validFiles) {
					list.add(new ListItem(file));
				}
				catPart.add(paragraph);
				catPart.add(list);
			} else {
				Paragraph paragraph = new Paragraph(messageSource.getMessage("file.valid.files.message.none", null,
						LocaleContextHolder.getLocale()));
				catPart.add(paragraph);
			}
			document.add(catPart);

			// Ficheros no válidos
			chapter = 2;
			for (FileUploadController.ErrorInfo file : invalidFiles) {
				// File category
				anchor = new Anchor(messageSource.getMessage("file", null, LocaleContextHolder.getLocale())
						+ PdfReportGenerator.TWO_DOTS_SPACE + file.getFilename(), categoryFont);
				anchor.setName(file.getFilename());
				catPart = new Chapter(new Paragraph(anchor), chapter);

				for (Map.Entry<String, List<ValidationError>> entry : file.getErrors().entrySet()) {
					// Sheet subcategory
					Paragraph subPara = new Paragraph(
							messageSource.getMessage("sheet", null, LocaleContextHolder.getLocale())
									+ PdfReportGenerator.TWO_DOTS_SPACE + entry.getKey(),
							subcategoryFont);
					Section subCatPart = catPart.addSection(subPara);

					Paragraph emptyLines = new Paragraph();
					addEmptyLine(emptyLines, 1);
					subCatPart.add(emptyLines);

					// Error table
					if (entry.getValue().size() != 0) {
						PdfPTable errorTable = createTable(entry.getValue());
						subCatPart.add(errorTable);
					} else {

						Paragraph paragraph = new Paragraph(messageSource.getMessage("file.validation.sheet.no.errors",
								null, LocaleContextHolder.getLocale()));
						subCatPart.add(paragraph);
					}

					// separation between subsections
					Paragraph paragraph = new Paragraph();
					addEmptyLine(paragraph, 1);
					subCatPart.add(paragraph);
					//subCatPart.add(Chunk.NEXTPAGE);

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

	/**
	 * Adds the empty line.
	 *
	 * @param paragraph the paragraph
	 * @param number    the number
	 */
	private void addEmptyLine(Paragraph paragraph, int number) {
		for (int i = 0; i < number; i++) {
			paragraph.add(Chunk.NEWLINE);
		}
	}

	/**
	 * Creates the table.
	 *
	 * @param errors the errors
	 * @return the pdf P table
	 * @throws DocumentException the document exception
	 */
	private PdfPTable createTable(List<ValidationError> errors) throws DocumentException {
		int numColumns = 2;
		PdfPTable table = new PdfPTable(numColumns);
		table.setWidths(new float[] { 25f, 75f });

		PdfPCell c1 = new PdfPCell(
				new Phrase(messageSource.getMessage("file.cell", null, LocaleContextHolder.getLocale())));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setPadding(10);
		table.addCell(c1);

		c1 = new PdfPCell(new Phrase(messageSource.getMessage("file.errors", null, LocaleContextHolder.getLocale())));
		c1.setHorizontalAlignment(Element.ALIGN_CENTER);
		c1.setPadding(10);
		table.addCell(c1);
		table.setHeaderRows(1);

		errors.forEach(error -> {
			PdfPCell cellError = new PdfPCell(new Phrase(error.getCell()));

			cellError.setHorizontalAlignment(Element.ALIGN_CENTER);
			cellError.setPadding(10);
			table.addCell(cellError);

			PdfPCell cellMessage = new PdfPCell(new Phrase(error.getMessage()));

			cellMessage.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
			cellMessage.setPadding(10);
			table.addCell(cellMessage);
		});
		return table;
	}
}
