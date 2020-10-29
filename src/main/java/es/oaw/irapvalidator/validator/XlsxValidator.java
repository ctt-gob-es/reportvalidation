package es.oaw.irapvalidator.validator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import es.oaw.irapvalidator.service.Dir3Service;

/**
 * The Class XlsxValidator.
 */
@Service
public class XlsxValidator {

	/** The Constant COLUMN_I. */
	private static final String COLUMN_I = "I";

	/** The Constant COLUMN_F. */
	private static final String COLUMN_F = "F";

	/** The Constant COLUMN_D. */
	private static final String COLUMN_D = "D";

	/** The Constant COLUMN_C. */
	private static final String COLUMN_C = "C";

	/** The Constant COLUMN_B. */
	private static final String COLUMN_B = "B";

	/** The Constant MAX_PAGES. */
	private static final int MAX_PAGES = 35;

	/** The Constant SI. */
	private static final String SI = "Sí";

	/** The Constant VALIDATION_CELL_EMPTY. */
	private static final String VALIDATION_CELL_EMPTY = "validation.cell.empty";

	/** The Constant pageTypes. */
	private static final String[] pageTypes = new String[] { "Página inicio", "Inicio de sesión", "Mapa web",
			"Contacto", "Ayuda", "Legal", "Servicio / Proceso", "Búsqueda", "Declaración accesibilidad",
			"Mecanismo de comunicación", "Pagina tipo", "Otras páginas", "Documento descargable", "Aleatoria" };

	/** The Constant resultTypes. */
	private static final String[] resultTypes = new String[] { "N/T", "N/D", "N/A", "Falla", "Pasa" };

	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	/** The dir 3 service. */
	@Autowired
	private Dir3Service dir3Service;

	/** The num pages. */
	private int numPages = 0;

	/**
	 * Validate.
	 *
	 * @param workbook the workbook
	 * @return the list
	 */
	public List<ValidationError> validate(final Workbook workbook) {
		List<ValidationError> errors = new ArrayList<ValidationError>();

		final Sheet sheet01 = workbook.getSheetAt(1);
		final Sheet sheet02 = workbook.getSheetAt(2);
		final Sheet sheet03 = workbook.getSheetAt(3);
		final Sheet sheetP1 = workbook.getSheetAt(4);
		final Sheet sheetP2 = workbook.getSheetAt(5);
		final Sheet sheetP3 = workbook.getSheetAt(6);
		final Sheet sheetP4 = workbook.getSheetAt(7);
		final Sheet sheetResults = workbook.getSheetAt(8);

		errors.addAll(validateSheet01(sheet01));
		errors.addAll(validateSheet02(sheet02));
		errors.addAll(validateSheet03(sheet03));
		errors.addAll(validateSheetPrinciple(sheetP1, 19, 776));
		errors.addAll(validateSheetPrinciple(sheetP2, 19, 661));
		errors.addAll(validateSheetPrinciple(sheetP3, 19, 395));
		errors.addAll(validateSheetPrinciple(sheetP4, 19, 129));
		errors.addAll(validateSheetResults(sheetResults));
		Collections.sort(errors);
		return errors;

	}

	/**
	 * Validate sheet 01.
	 *
	 * @param sheet the sheet
	 * @return the list
	 */
	private List<ValidationError> validateSheet01(final Sheet sheet) {
		List<ValidationError> errors = new ArrayList<ValidationError>();

		final String columnNotEmptyLabel = COLUMN_B;
		final String columnNotEmptyValue = COLUMN_C;
		final int[] cellNotEmptyRows = new int[] { 7, 9, 11, 13, 17, 19, 21, 25, 27, 29, 31, 33, 35, 39, 41, 45 };

		// Checks cells not empty
		for (int i = 0; i < cellNotEmptyRows.length; i++) {
			if (cellIsEmpty(sheet, columnNotEmptyValue + cellNotEmptyRows[i])) {
				errors.add(new ValidationError(sheet.getSheetName(), columnNotEmptyValue + cellNotEmptyRows[i],
						messageSource.getMessage(VALIDATION_CELL_EMPTY,
								new String[] { columnNotEmptyValue + cellNotEmptyRows[i],
										getCellValue(sheet, columnNotEmptyLabel + cellNotEmptyRows[i]) },
								LocaleContextHolder.getLocale())));

			}
		}

		// C9 Validate URA Dir3
		final String dir3Cell = columnNotEmptyValue + 9;
		if (!cellIsEmpty(sheet, dir3Cell)) {
			// dir3 not exists
			if (!dir3Service.existsDir3(getCellValue(sheet, dir3Cell))) {
				errors.add(new ValidationError(sheet.getSheetName(), dir3Cell, messageSource.getMessage(
						"validation.cell.invalid.dir3", new String[] { dir3Cell }, LocaleContextHolder.getLocale())));
			}

		}

		// C13 Validate URA Ambit Dir3
		final String dir3AmbitCell = columnNotEmptyValue + 13;
		if (!cellIsEmpty(sheet, dir3AmbitCell)) {
			// dir3 not exists
			if (!dir3Service.existsDir3(getCellValue(sheet, dir3AmbitCell))) {
				errors.add(new ValidationError(sheet.getSheetName(), dir3AmbitCell,
						messageSource.getMessage("validation.cell.invalid.dir3ambit", new String[] { dir3AmbitCell },
								LocaleContextHolder.getLocale())));
			}

		}

		// D49 --> D59 (at least one selected)
		final String atLeastOneSelectedColumn = COLUMN_D;
		final int atLeastOneSelectedFirstRow = 49;
		final int atLeastOneSelectedLastRow = 59;

		boolean atLeastOneAmbit = false;
		for (int i = 0; i < atLeastOneSelectedLastRow - atLeastOneSelectedFirstRow; i++) {
			if (SI.equalsIgnoreCase(getCellValue(sheet, atLeastOneSelectedColumn + atLeastOneSelectedFirstRow + i))) {
				atLeastOneAmbit = true;
				break;
			}
		}

		if (!atLeastOneAmbit) {
			errors.add(new ValidationError(sheet.getSheetName(),
					atLeastOneSelectedColumn + atLeastOneSelectedFirstRow + "-" + atLeastOneSelectedColumn
							+ atLeastOneSelectedLastRow,
					messageSource.getMessage("validation.sheet1.ambit.atleastone", null,
							LocaleContextHolder.getLocale())));
		}

		// C63 (optional)
		// C65 (optional)

		return errors;
	}

	/**
	 * Validate sheet 02.
	 *
	 * @param sheet the sheet
	 * @return the list
	 */
	private List<ValidationError> validateSheet02(final Sheet sheet) {
		List<ValidationError> errors = new ArrayList<ValidationError>();

		// C9 --> C12 mandatories

		String atLeastOneSelectedColumn = COLUMN_C;
		int atLeastOneSelectedFirstRow = 9;
		int atLeastOneSelectedLastRow = 12;
		boolean atLeastOneMandatorySelected = false;

		for (int i = 0; i < atLeastOneSelectedLastRow - atLeastOneSelectedFirstRow; i++) {
			int j = atLeastOneSelectedFirstRow + i;
			if (SI.equalsIgnoreCase(getCellValue(sheet, atLeastOneSelectedColumn + j))) {
				atLeastOneMandatorySelected = true;
				break;
			}
		}

		if (!atLeastOneMandatorySelected) {
			errors.add(new ValidationError(sheet.getSheetName(), "", messageSource.getMessage(
					"validation.sheet1.techs.mandatory.atleastone", null, LocaleContextHolder.getLocale())));
		}

		// C9 --> C13 (at least one selected)
		atLeastOneSelectedColumn = COLUMN_C;
		atLeastOneSelectedFirstRow = 9;
		atLeastOneSelectedLastRow = 13;

		boolean atLeastOneTechSelected = false;

		for (int i = 0; i < atLeastOneSelectedLastRow - atLeastOneSelectedFirstRow; i++) {
			int j = atLeastOneSelectedFirstRow + i;
			if (SI.equalsIgnoreCase(getCellValue(sheet, atLeastOneSelectedColumn + j))) {
				atLeastOneTechSelected = true;
				break;
			}
		}
		// F9 --> F13 (at least one selected)
		atLeastOneSelectedColumn = COLUMN_F;
		atLeastOneSelectedFirstRow = 9;
		atLeastOneSelectedLastRow = 13;
		for (int i = 0; i < atLeastOneSelectedLastRow - atLeastOneSelectedFirstRow; i++) {
			int j = atLeastOneSelectedFirstRow + i;
			if (SI.equalsIgnoreCase(getCellValue(sheet, atLeastOneSelectedColumn + j))) {
				atLeastOneTechSelected = true;
				break;
			}
		}

		atLeastOneSelectedColumn = COLUMN_I;
		atLeastOneSelectedFirstRow = 9;
		atLeastOneSelectedLastRow = 12;

		// I9 --> C12 (at least one selected)
		for (int i = 0; i < atLeastOneSelectedLastRow - atLeastOneSelectedFirstRow; i++) {
			int j = atLeastOneSelectedFirstRow + i;
			if (SI.equalsIgnoreCase(getCellValue(sheet, atLeastOneSelectedColumn + j))) {
				atLeastOneTechSelected = true;
				break;
			}
		}
		if (!atLeastOneTechSelected) {
			errors.add(new ValidationError(sheet.getSheetName(), "", messageSource
					.getMessage("validation.sheet1.techs.atleastone", null, LocaleContextHolder.getLocale())));
		}

		return errors;
	}

	/**
	 * Validate sheet 03.
	 *
	 * @param sheet the sheet
	 * @return the list
	 */
	private List<ValidationError> validateSheet03(final Sheet sheet) {
		List<ValidationError> errors = new ArrayList<ValidationError>();

		// if from 8 to 42 one of columns c,d,e is filled, this 3 columns will pass
		// validations

		int initRow = 8;
		for (int i = 0; i < 35; i++) {

			int currentRow = i + initRow;
			String shortNameCell = COLUMN_C + currentRow;
			String typeCell = COLUMN_D + currentRow;
			String urlCell = "E" + currentRow;
			String breadcumbCell = COLUMN_F + currentRow;

			if (!cellIsEmpty(sheet, shortNameCell) || !cellIsEmpty(sheet, typeCell) || !cellIsEmpty(sheet, urlCell)
					|| !cellIsEmpty(sheet, breadcumbCell)) {

				// If detect any cell filled, count
				numPages++;

				// Check C column if filled

				if (cellIsEmpty(sheet, shortNameCell)) {
					errors.add(new ValidationError(sheet.getSheetName(), shortNameCell,
							messageSource.getMessage("validation.cell.empty.shortname", new String[] { shortNameCell },
									LocaleContextHolder.getLocale())));

				}

				// Check D column has a valid value

				if (cellIsEmpty(sheet, typeCell)) {
					errors.add(new ValidationError(sheet.getSheetName(), typeCell, messageSource.getMessage(
							"validation.cell.empty.type", new String[] { typeCell }, LocaleContextHolder.getLocale())));
				} else if (!Arrays.asList(pageTypes).contains(getCellValue(sheet, typeCell))) {
					errors.add(new ValidationError(sheet.getSheetName(), typeCell,
							messageSource.getMessage("validation.cell.invalid.type", new String[] { typeCell },
									LocaleContextHolder.getLocale())));
				}

				// Check E column is a valid URL

				if (cellIsEmpty(sheet, urlCell)) {
					errors.add(new ValidationError(sheet.getSheetName(), urlCell, messageSource.getMessage(
							"validation.cell.empty.url", new String[] { urlCell }, LocaleContextHolder.getLocale())));
				} else {
					try {
						new URL(getCellValue(sheet, urlCell));
					} catch (MalformedURLException e) {
						errors.add(new ValidationError(sheet.getSheetName(), urlCell,
								messageSource.getMessage("validation.cell.invalid.url", new String[] { urlCell },
										LocaleContextHolder.getLocale())));
					}

				}

				// Check F column if filled

				if (cellIsEmpty(sheet, breadcumbCell)) {
					errors.add(new ValidationError(sheet.getSheetName(), breadcumbCell,
							messageSource.getMessage("validation.cell.empty.breadcumb", new String[] { breadcumbCell },
									LocaleContextHolder.getLocale())));

				}
			}

		}

		// TODO Check if number of indicated
		if (numPages > 0) {

		}

		return errors;

	}

	/**
	 * Validate sheet principle.
	 *
	 * @param sheet    the sheet
	 * @param beginRow the begin row
	 * @param endRow   the end row
	 * @return the list
	 */
	private List<ValidationError> validateSheetPrinciple(final Sheet sheet, final int beginRow, final int endRow) {

		List<ValidationError> errors = new ArrayList<ValidationError>();

		// Check for every table if num of results registred is the same of detected
		// pages

		// Begin of table data. Evary table has 35 rows. Next table begins 38 next
		int tableRowIndex = beginRow;

		while (tableRowIndex <= endRow) {

			// First check filled rows

			int countFilled = 0;

			for (int i = 0; i < MAX_PAGES; i++) {

				int currentRow = tableRowIndex + i;
				String pageTitleCell = COLUMN_B + currentRow;
				String pageUrlCell = COLUMN_C + currentRow;
				String pageResultCell = COLUMN_D + currentRow;

				if (!cellIsEmpty(sheet, pageTitleCell) || !cellIsEmpty(sheet, pageUrlCell)
						|| !cellIsEmpty(sheet, pageResultCell)) {
					countFilled++;
				}
			}

			// Less or greater than filled
			if (countFilled < numPages) {

				String principleTitleCell = COLUMN_C + (tableRowIndex - 1);

				errors.add(new ValidationError(sheet.getSheetName(), principleTitleCell,
						messageSource.getMessage("validation.principle.less.pages",
								new String[] { getCellValue(sheet, principleTitleCell) },
								LocaleContextHolder.getLocale())));
			} else if (countFilled > numPages) {
				String principleTitleCell = COLUMN_C + (tableRowIndex - 1);

				errors.add(new ValidationError(sheet.getSheetName(), principleTitleCell,
						messageSource.getMessage("validation.principle.greater.pages",
								new String[] { getCellValue(sheet, principleTitleCell) },
								LocaleContextHolder.getLocale())));

			}

			// Check for all numPages the columns B,C and D are valid
			for (int i = 0; i < numPages; i++) {

				int currentRow = tableRowIndex + i;
				String pageTitleCell = COLUMN_B + currentRow;
				String pageUrlCell = COLUMN_C + currentRow;
				String pageResultCell = COLUMN_D + currentRow;

				// Check B column if filled

				if (cellIsEmpty(sheet, pageTitleCell)) {
					errors.add(new ValidationError(sheet.getSheetName(), pageTitleCell,
							messageSource.getMessage("validation.cell.empty.shortname", new String[] { pageTitleCell },
									LocaleContextHolder.getLocale())));

				}

				// Check C column is a valid URL

				if (cellIsEmpty(sheet, pageUrlCell)) {
					errors.add(new ValidationError(sheet.getSheetName(), pageUrlCell,
							messageSource.getMessage("validation.cell.empty.url", new String[] { pageUrlCell },
									LocaleContextHolder.getLocale())));
				} else {
					try {
						new URL(getCellValue(sheet, pageUrlCell));
					} catch (MalformedURLException e) {
						errors.add(new ValidationError(sheet.getSheetName(), pageUrlCell,
								messageSource.getMessage("validation.cell.invalid.url", new String[] { pageUrlCell },
										LocaleContextHolder.getLocale())));
					}

				}

				// Check D column if filled

				if (cellIsEmpty(sheet, pageResultCell)) {
					errors.add(new ValidationError(sheet.getSheetName(), pageResultCell,
							messageSource.getMessage("validation.cell.empty.result", new String[] { pageResultCell },
									LocaleContextHolder.getLocale())));
				} else if (!Arrays.asList(resultTypes).contains(getCellValue(sheet, pageResultCell))) {
					errors.add(new ValidationError(sheet.getSheetName(), pageResultCell,
							messageSource.getMessage("validation.cell.invalid.result", new String[] { pageResultCell },
									LocaleContextHolder.getLocale())));
				}

			}
			tableRowIndex = tableRowIndex + 38;
		}

		return errors;
	}

	/**
	 * Validate sheet results.
	 *
	 * @param sheet    the sheet
	 * @return the list
	 */
	private List<ValidationError> validateSheetResults(final Sheet sheet) {

		List<ValidationError> errors = new ArrayList<ValidationError>();
		//B-C-19 --> 53 filled
		//B-C-60 --> 94 filled
		//Check values in this sheet are same in principles 
		//D54 --> AG54 not in finished
		//D95 --> W54 not in finished
		return errors;
	}

	/**
	 * Gets the cell value.
	 *
	 * @param sheet         the sheet
	 * @param cellReference the cell reference
	 * @return the cell value
	 */
	private String getCellValue(final Sheet sheet, final String cellReference) {
		CellReference ref = new CellReference(cellReference);
		Row r = sheet.getRow(ref.getRow());
		Cell c = r.getCell(ref.getCol());
		if (c == null || c.getCellType() == CellType.BLANK) {
			return "";
		} else {
			return c.getStringCellValue();
		}

	}

	/**
	 * Cell is empty.
	 *
	 * @param sheet         the sheet
	 * @param cellReference the cell reference
	 * @return true, if successful
	 */
	private boolean cellIsEmpty(final Sheet sheet, final String cellReference) {

		CellReference ref = new CellReference(cellReference);
		Row r = sheet.getRow(ref.getRow());
		Cell c = r.getCell(ref.getCol());
		if (c == null || CellType.BLANK.equals(c.getCellType())
				|| (CellType.STRING.equals(c.getCellType()) && StringUtils.isEmpty(c.getStringCellValue()))) {
			return true;
		}

		return false;
	}

}
