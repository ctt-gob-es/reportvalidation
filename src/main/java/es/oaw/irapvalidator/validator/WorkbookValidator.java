package es.oaw.irapvalidator.validator;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.jopendocument.dom.spreadsheet.MutableCell;
import org.jopendocument.dom.spreadsheet.Sheet;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import es.oaw.irapvalidator.service.Dir3Service;

/**
 * The Class UnifiedValidator.
 */
@Service
public class WorkbookValidator {

	/** The evaluator. */
	private FormulaEvaluator evaluator;

	/** The principle table separation. */
	private int principleTableSeparation;

	/** The max num pages. */
	private int maxNumPages;

	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	/** The dir 3 service. */
	@Autowired
	private Dir3Service dir3Service;

	/** The num pages. */
	private int numPages;

	/**
	 * Validate.
	 *
	 * @param object the object
	 * @return the map
	 */
	public Map<String, List<ValidationError>> validate(final Object object) {
		Map<String, List<ValidationError>> errorMap = new TreeMap<String, List<ValidationError>>();
		numPages = 0;
		maxNumPages = ValidatorConstants.MAX_PAGES;
		principleTableSeparation = ValidatorConstants.PRINCIPLE_TABLE_SEPARATION_IRAP_35_PAGES;
		if (object instanceof Workbook) {
			Workbook workbook = (Workbook) object;
			evaluator = workbook.getCreationHelper().createFormulaEvaluator();

			final org.apache.poi.ss.usermodel.Sheet sheet01 = workbook.getSheetAt(1);
			final org.apache.poi.ss.usermodel.Sheet sheet02 = workbook.getSheetAt(2);
			final org.apache.poi.ss.usermodel.Sheet sheet03 = workbook.getSheetAt(3);
			final org.apache.poi.ss.usermodel.Sheet sheetP1 = workbook.getSheetAt(4);
			final org.apache.poi.ss.usermodel.Sheet sheetP2 = workbook.getSheetAt(5);
			final org.apache.poi.ss.usermodel.Sheet sheetP3 = workbook.getSheetAt(6);
			final org.apache.poi.ss.usermodel.Sheet sheetP4 = workbook.getSheetAt(7);
			final org.apache.poi.ss.usermodel.Sheet sheetResults = workbook.getSheetAt(8);

			errorMap.put(sheet01.getSheetName(), validateSheet01(sheet01));
			errorMap.put(sheet02.getSheetName(), validateSheet02(sheet02));
			errorMap.put(sheet03.getSheetName(), validateSheet03(sheet03));
			if (principleTableSeparation == ValidatorConstants.PRINCIPLE_TABLE_SEPARATION_IRAP_33_PAGES) {
				errorMap.put(sheetP1.getSheetName(), validateSheetPrinciple(sheetP1, 19, 647));
				errorMap.put(sheetP2.getSheetName(), validateSheetPrinciple(sheetP2, 19, 547));
				errorMap.put(sheetP3.getSheetName(), validateSheetPrinciple(sheetP3, 19, 319));
				errorMap.put(sheetP4.getSheetName(), validateSheetPrinciple(sheetP4, 19, 85));
			} else {
				errorMap.put(sheetP1.getSheetName(), validateSheetPrinciple(sheetP1, 19, 776));
				errorMap.put(sheetP2.getSheetName(), validateSheetPrinciple(sheetP2, 19, 661));
				errorMap.put(sheetP3.getSheetName(), validateSheetPrinciple(sheetP3, 19, 395));
				errorMap.put(sheetP4.getSheetName(), validateSheetPrinciple(sheetP4, 19, 129));
			}
			errorMap.put(sheetResults.getSheetName(), validateSheetResults(sheetResults));

		} else if (object instanceof SpreadSheet) {
			SpreadSheet workbook = (SpreadSheet) object;
			final Sheet sheet01 = workbook.getSheet(1);
			final Sheet sheet02 = workbook.getSheet(2);
			final Sheet sheet03 = workbook.getSheet(3);
			final Sheet sheetP1 = workbook.getSheet(4);
			final Sheet sheetP2 = workbook.getSheet(5);
			final Sheet sheetP3 = workbook.getSheet(6);
			final Sheet sheetP4 = workbook.getSheet(7);
			final Sheet sheetResults = workbook.getSheet(8);

			errorMap.put(sheet01.getName(), validateSheet01(sheet01));
			errorMap.put(sheet02.getName(), validateSheet02(sheet02));
			errorMap.put(sheet03.getName(), validateSheet03(sheet03));

			// in this sheet in old irap separation between las two tables is one row more
			if (principleTableSeparation == ValidatorConstants.PRINCIPLE_TABLE_SEPARATION_IRAP_33_PAGES) {
				errorMap.put(sheetP1.getName(), validateSheetPrinciple(sheetP1, 19, 647));
				errorMap.put(sheetP2.getName(), validateSheetPrinciple(sheetP2, 19, 547));
				errorMap.put(sheetP3.getName(), validateSheetPrinciple(sheetP3, 19, 319));
				errorMap.put(sheetP4.getName(), validateSheetPrinciple(sheetP4, 19, 85));
			} else {
				errorMap.put(sheetP1.getName(), validateSheetPrinciple(sheetP1, 19, 776));
				errorMap.put(sheetP2.getName(), validateSheetPrinciple(sheetP2, 19, 661));
				errorMap.put(sheetP3.getName(), validateSheetPrinciple(sheetP3, 19, 395));
				errorMap.put(sheetP4.getName(), validateSheetPrinciple(sheetP4, 19, 129));
			}
			errorMap.put(sheetResults.getName(), validateSheetResults(sheetResults));

		}
		return errorMap;
	}

	/**
	 * Gets the sheet name.
	 *
	 * @param sheet the sheet
	 * @return the sheet name
	 */
	private String getSheetName(final Object sheet) {
		if (sheet instanceof Sheet) {
			return ((Sheet) sheet).getName();
		} else if (sheet instanceof org.apache.poi.ss.usermodel.Sheet) {
			return ((org.apache.poi.ss.usermodel.Sheet) sheet).getSheetName();
		}
		return null;
	}

	/**
	 * Validate sheet 01.
	 *
	 * @param sheet the sheet
	 * @return the list
	 */
	private List<ValidationError> validateSheet01(final Object sheet) {
		List<ValidationError> errors = new ArrayList<ValidationError>();

		final String columnNotEmptyLabel = ValidatorConstants.COLUMN_B;
		final String columnNotEmptyValue = ValidatorConstants.COLUMN_C;

		// Checks cells not empty
		String sheetName = getSheetName(sheet);
		for (int i = 0; i < ValidatorConstants.SHEET_01_MANDATORY_CONTENT_ROWS.length; i++) {
			if (cellIsEmpty(sheet, columnNotEmptyValue + ValidatorConstants.SHEET_01_MANDATORY_CONTENT_ROWS[i])) {
				errors.add(new ValidationError(sheetName,
						columnNotEmptyValue + ValidatorConstants.SHEET_01_MANDATORY_CONTENT_ROWS[i],
						messageSource.getMessage(ValidatorConstants.VALIDATION_CELL_EMPTY, new String[] {
								columnNotEmptyValue + ValidatorConstants.SHEET_01_MANDATORY_CONTENT_ROWS[i],
								getCellValue(sheet,
										columnNotEmptyLabel + ValidatorConstants.SHEET_01_MANDATORY_CONTENT_ROWS[i]) },
								LocaleContextHolder.getLocale())));

			}
		}

		// C9 Validate URA Dir3
		final String dir3Cell = columnNotEmptyValue + 9;
		if (!cellIsEmpty(sheet, dir3Cell)) {
			// dir3 not exists
			if (!dir3Service.existsDir3(getCellValue(sheet, dir3Cell))) {
				errors.add(new ValidationError(sheetName, dir3Cell,
						messageSource.getMessage(ValidatorConstants.VALIDATION_CELL_INVALID_DIR3,
								new String[] { dir3Cell }, LocaleContextHolder.getLocale())));
			}

		}

		// C13 Validate URA Ambit Dir3
		final String dir3AmbitCell = columnNotEmptyValue + 13;
		if (!cellIsEmpty(sheet, dir3AmbitCell)) {
			// dir3 not exists
			if (!dir3Service.existsDir3(getCellValue(sheet, dir3AmbitCell))) {
				errors.add(new ValidationError(sheetName, dir3AmbitCell,
						messageSource.getMessage(ValidatorConstants.VALIDATION_CELL_INVALID_DIR3AMBIT,
								new String[] { dir3AmbitCell }, LocaleContextHolder.getLocale())));
			}

		}

		// D49 --> D59 (at least one selected)
		final String atLeastOneSelectedColumn = ValidatorConstants.COLUMN_D;
		final int atLeastOneSelectedFirstRow = 49;
		final int atLeastOneSelectedLastRow = 59;

		boolean atLeastOneAmbit = false;
		for (int i = 0; i < atLeastOneSelectedLastRow - atLeastOneSelectedFirstRow; i++) {
			if (ValidatorConstants.YES_VALUE.equalsIgnoreCase(
					getCellValue(sheet, atLeastOneSelectedColumn + (atLeastOneSelectedFirstRow + i)))) {
				atLeastOneAmbit = true;
				break;
			}
		}

		if (!atLeastOneAmbit) {
			errors.add(new ValidationError(sheetName,
					atLeastOneSelectedColumn + atLeastOneSelectedFirstRow + "-" + atLeastOneSelectedColumn
							+ atLeastOneSelectedLastRow,
					messageSource.getMessage(ValidatorConstants.VALIDATION_SHEET1_AMBIT_ATLEASTONE, null,
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
	private List<ValidationError> validateSheet02(final Object sheet) {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		String sheetName = getSheetName(sheet);
		// C9 --> C12 mandatories

		String atLeastOneSelectedColumn = ValidatorConstants.COLUMN_C;
		int atLeastOneSelectedFirstRow = 9;
		int atLeastOneSelectedLastRow = 12;
		boolean atLeastOneMandatorySelected = false;

		for (int i = 0; i < atLeastOneSelectedLastRow - atLeastOneSelectedFirstRow; i++) {
			int j = atLeastOneSelectedFirstRow + i;
			if (ValidatorConstants.YES_VALUE.equalsIgnoreCase(getCellValue(sheet, atLeastOneSelectedColumn + j))) {
				atLeastOneMandatorySelected = true;
				break;
			}
		}

		if (!atLeastOneMandatorySelected) {
			errors.add(new ValidationError(sheetName, ValidatorConstants.STRING_EMPTY,
					messageSource.getMessage(ValidatorConstants.VALIDATION_SHEET1_TECHS_MANDATORY_ATLEASTONE, null,
							LocaleContextHolder.getLocale())));
		}

		// C9 --> C13 (at least one selected)
		atLeastOneSelectedColumn = ValidatorConstants.COLUMN_C;
		atLeastOneSelectedFirstRow = 9;
		atLeastOneSelectedLastRow = 13;

		boolean atLeastOneTechSelected = false;

		for (int i = 0; i < atLeastOneSelectedLastRow - atLeastOneSelectedFirstRow; i++) {
			int j = atLeastOneSelectedFirstRow + i;
			if (ValidatorConstants.YES_VALUE.equalsIgnoreCase(getCellValue(sheet, atLeastOneSelectedColumn + j))) {
				atLeastOneTechSelected = true;
				break;
			}
		}
		// F9 --> F13 (at least one selected)
		atLeastOneSelectedColumn = ValidatorConstants.COLUMN_F;
		atLeastOneSelectedFirstRow = 9;
		atLeastOneSelectedLastRow = 13;
		for (int i = 0; i < atLeastOneSelectedLastRow - atLeastOneSelectedFirstRow; i++) {
			int j = atLeastOneSelectedFirstRow + i;
			if (ValidatorConstants.YES_VALUE.equalsIgnoreCase(getCellValue(sheet, atLeastOneSelectedColumn + j))) {
				atLeastOneTechSelected = true;
				break;
			}
		}

		atLeastOneSelectedColumn = ValidatorConstants.COLUMN_I;
		atLeastOneSelectedFirstRow = 9;
		atLeastOneSelectedLastRow = 12;

		// I9 --> C12 (at least one selected)
		for (int i = 0; i < atLeastOneSelectedLastRow - atLeastOneSelectedFirstRow; i++) {
			int j = atLeastOneSelectedFirstRow + i;
			if (ValidatorConstants.YES_VALUE.equalsIgnoreCase(getCellValue(sheet, atLeastOneSelectedColumn + j))) {
				atLeastOneTechSelected = true;
				break;
			}
		}
		if (!atLeastOneTechSelected) {
			errors.add(new ValidationError(sheetName, ValidatorConstants.STRING_EMPTY, messageSource.getMessage(
					ValidatorConstants.VALIDATION_SHEET1_TECHS_ATLEASTONE, null, LocaleContextHolder.getLocale())));
		}

		return errors;
	}

	/**
	 * Validate sheet 03.
	 *
	 * @param sheet the sheet
	 * @return the list
	 */
	private List<ValidationError> validateSheet03(final Object sheet) {
		List<ValidationError> errors = new ArrayList<ValidationError>();
		String sheetName = getSheetName(sheet);
		// in this sheet wil be detect id fils is older version wiith only 30 rows
		// cell B31 will be 31
		// D column next to B column "PAGINAS SELECCIONADAS"??

		if (cellIsEmpty(sheet, "B38") || (!cellIsEmpty(sheet, "B38") && !"31".equals(getCellValue(sheet, "B38")))) {
			maxNumPages = 30;
			principleTableSeparation = ValidatorConstants.PRINCIPLE_TABLE_SEPARATION_IRAP_33_PAGES;

		}

		// check if sample is correct

		if (maxNumPages == 30) {
			String cellReference = "D47";
			if (ValidatorConstants.NO_VALUE.equalsIgnoreCase(getCellValue(sheet, cellReference))) {
				errors.add(new ValidationError(sheetName, cellReference,
						messageSource.getMessage(ValidatorConstants.VALIDATION_CELL_SAMPLE_CORRECT,
								new String[] { cellReference }, LocaleContextHolder.getLocale())));
			}

		} else if (maxNumPages == 35) {
			String cellReference = "D52";
			if (ValidatorConstants.NO_VALUE.equalsIgnoreCase(getCellValue(sheet, cellReference))) {
				errors.add(new ValidationError(sheetName, cellReference,
						messageSource.getMessage(ValidatorConstants.VALIDATION_CELL_SAMPLE_CORRECT,
								new String[] { cellReference }, LocaleContextHolder.getLocale())));
			}
		}

		// if from 8 to 42 one of columns c,d,e is filled, this 3 columns will pass
		// validations
		int initRow = 8;
		for (int i = 0; i < maxNumPages; i++) {

			int currentRow = i + initRow;
			String shortNameCell = ValidatorConstants.COLUMN_C + currentRow;
			String typeCell = ValidatorConstants.COLUMN_D + currentRow;
			String urlCell = ValidatorConstants.COLUMN_E + currentRow;
			String breadcumbCell = ValidatorConstants.COLUMN_F + currentRow;

			if (!cellIsEmpty(sheet, shortNameCell) || !cellIsEmpty(sheet, typeCell) || !cellIsEmpty(sheet, urlCell)
					|| !cellIsEmpty(sheet, breadcumbCell)) {
				// If detect any cell filled, count
				numPages++;

			}

		}
		int cellNumPages = 0;
		String cellReference = "";
		if (maxNumPages == 30) {
			cellReference = "D40";
			cellNumPages = Integer.parseInt(getCellValue(sheet, cellReference));

		} else if (maxNumPages == 35) {
			cellReference = "D45";
			cellNumPages = Integer.parseInt(getCellValue(sheet, cellReference));
		}

		// takes as valid the value marked in file
		if (cellNumPages != 0) {

			numPages = cellNumPages;
		}

		// After count
		initRow = 8;
		for (int i = 0; i < numPages; i++) {

			// Check C column if filled

			int currentRow = i + initRow;
			String shortNameCell = ValidatorConstants.COLUMN_C + currentRow;
			String typeCell = ValidatorConstants.COLUMN_D + currentRow;
			String urlCell = ValidatorConstants.COLUMN_E + currentRow;
			String breadcumbCell = ValidatorConstants.COLUMN_F + currentRow;

			if (cellIsEmpty(sheet, shortNameCell)) {
				errors.add(new ValidationError(sheetName, shortNameCell,
						messageSource.getMessage(ValidatorConstants.VALIDATION_CELL_EMPTY_SHORTNAME,
								new String[] { shortNameCell }, LocaleContextHolder.getLocale())));

			}

			// Check D column has a valid value

			if (cellIsEmpty(sheet, typeCell)) {
				errors.add(new ValidationError(sheetName, typeCell,
						messageSource.getMessage(ValidatorConstants.VALIDATION_CELL_EMPTY_TYPE,
								new String[] { typeCell }, LocaleContextHolder.getLocale())));
			} else if (!Arrays.asList(ValidatorConstants.ALLOWED_PAGE_TYPES).contains(getCellValue(sheet, typeCell))) {
				errors.add(new ValidationError(sheetName, typeCell, messageSource.getMessage(
						"validation.cell.invalid.type", new String[] { typeCell }, LocaleContextHolder.getLocale())));
			}

			// Check E column is a valid URL

			if (cellIsEmpty(sheet, urlCell)) {
				errors.add(new ValidationError(sheetName, urlCell,
						messageSource.getMessage(ValidatorConstants.VALIDATION_CELL_EMPTY_URL, new String[] { urlCell },
								LocaleContextHolder.getLocale())));
			} else {
				try {
					new URL(getCellValue(sheet, urlCell));
				} catch (MalformedURLException e) {
					errors.add(new ValidationError(sheetName, urlCell,
							messageSource.getMessage(ValidatorConstants.VALIDATION_CELL_INVALID_URL,
									new String[] { urlCell }, LocaleContextHolder.getLocale())));
				}

			}

			// Check F column if filled

			if (cellIsEmpty(sheet, breadcumbCell)) {
				errors.add(new ValidationError(sheetName, breadcumbCell,
						messageSource.getMessage(ValidatorConstants.VALIDATION_CELL_EMPTY_BREADCUMB,
								new String[] { breadcumbCell }, LocaleContextHolder.getLocale())));

			}
		}

		// Check if number of indicated (D45)
		// Check if random us at least 10%
		if (numPages > 0) {
			try {
				int numberofPagesCalculated = (int) Double
						.parseDouble(getCellValue(sheet, ValidatorConstants.SHEET_03_PAGE_COUNT_CELLREFERENCE));
				if (numberofPagesCalculated != numPages) {
					errors.add(new ValidationError(sheetName, ValidatorConstants.SHEET_03_PAGE_COUNT_CELLREFERENCE,
							messageSource.getMessage(ValidatorConstants.VALIDATION_SAMPLE_PAGES, null,
									LocaleContextHolder.getLocale())));
				}
				int numberofRandomPages = (int) Double
						.parseDouble(getCellValue(sheet, ValidatorConstants.SHEET_03_RANDOM_PAGE_COUNT_CELLREFERENCE));

				if (numberofRandomPages < (numPages / 10)) {
					errors.add(
							new ValidationError(sheetName, ValidatorConstants.SHEET_03_RANDOM_PAGE_COUNT_CELLREFERENCE,
									messageSource.getMessage(ValidatorConstants.VALIDATION_SAMPLE_PAGES_RANDOM, null,
											LocaleContextHolder.getLocale())));
				}

			} catch (NumberFormatException e) {

			}
		}

		if (numPages != cellNumPages) {
			errors.add(new ValidationError(sheetName, cellReference,
					messageSource.getMessage(ValidatorConstants.VALIDATION_CELL_SAMPLE_DISMATCH,
							new String[] { cellReference }, LocaleContextHolder.getLocale())));
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
	private List<ValidationError> validateSheetPrinciple(final Object sheet, final int beginRow, final int endRow) {

		List<ValidationError> errors = new ArrayList<ValidationError>();
		String sheetName = getSheetName(sheet);

		// Check for every table if num of results registred is the same of detected
		// pages

		// Begin of table data. Evary table has 35 rows. Next table begins 38 next
		int tableRowIndex = beginRow;

		while (tableRowIndex <= endRow) {

			// First check filled rows

			int countFilled = 0;

			for (int i = 0; i < maxNumPages; i++) {

				int currentRow = tableRowIndex + i;
				String pageTitleCell = ValidatorConstants.COLUMN_B + currentRow;
				String pageUrlCell = ValidatorConstants.COLUMN_C + currentRow;
				String pageResultCell = ValidatorConstants.COLUMN_D + currentRow;

				if (!cellIsEmpty(sheet, pageTitleCell) || !cellIsEmpty(sheet, pageUrlCell)
						|| !cellIsEmpty(sheet, pageResultCell)) {
					countFilled++;
				}
			}

			// Less or greater than filled

			if (countFilled < numPages) {

				String principleTitleCell = ValidatorConstants.COLUMN_C + (tableRowIndex - 1);

				errors.add(new ValidationError(sheetName, principleTitleCell,
						messageSource.getMessage(ValidatorConstants.VALIDATION_PRINCIPLE_LESS_PAGES,
								new String[] { getCellValue(sheet, principleTitleCell) },
								LocaleContextHolder.getLocale())));
			} else if (countFilled > numPages) {
				String principleTitleCell = ValidatorConstants.COLUMN_C + (tableRowIndex - 1);

				errors.add(new ValidationError(sheetName, principleTitleCell,
						messageSource.getMessage(ValidatorConstants.VALIDATION_PRINCIPLE_GREATER_PAGES,
								new String[] { getCellValue(sheet, principleTitleCell) },
								LocaleContextHolder.getLocale())));

			}

			// Check for all maxNumPages the columns B,C and D are valid
			for (int i = 0; i < maxNumPages; i++) {

				int currentRow = tableRowIndex + i;
				String pageTitleCell = ValidatorConstants.COLUMN_B + currentRow;
				String pageUrlCell = ValidatorConstants.COLUMN_C + currentRow;
				String pageResultCell = ValidatorConstants.COLUMN_D + currentRow;
				String pageResultErrorCell = ValidatorConstants.COLUMN_E + currentRow;

				// Check B column if filled

				if ("ERR".equals(getCellValue(sheet, pageResultErrorCell))) {

					if (cellIsEmpty(sheet, pageTitleCell)) {
						errors.add(new ValidationError(sheetName, pageTitleCell,
								messageSource.getMessage(ValidatorConstants.VALIDATION_CELL_EMPTY_SHORTNAME,
										new String[] { pageTitleCell }, LocaleContextHolder.getLocale())));

					}

					// Check C column is a valid URL

					if (cellIsEmpty(sheet, pageUrlCell)) {
						errors.add(new ValidationError(sheetName, pageUrlCell,
								messageSource.getMessage(ValidatorConstants.VALIDATION_CELL_EMPTY_URL,
										new String[] { pageUrlCell }, LocaleContextHolder.getLocale())));
					} else {
						try {
							new URL(getCellValue(sheet, pageUrlCell));
						} catch (MalformedURLException e) {
							errors.add(new ValidationError(sheetName, pageUrlCell,
									messageSource.getMessage(ValidatorConstants.VALIDATION_CELL_INVALID_URL,
											new String[] { pageUrlCell }, LocaleContextHolder.getLocale())));
						}

					}

					// Check D column if filled

					if (cellIsEmpty(sheet, pageResultCell)) {
						errors.add(new ValidationError(sheetName, pageResultCell,
								messageSource.getMessage(ValidatorConstants.VALIDATION_CELL_EMPTY_RESULT,
										new String[] { pageResultCell }, LocaleContextHolder.getLocale())));
					} else if (!Arrays.asList(ValidatorConstants.ALLOWED_RESULT_TYPES)
							.contains(getCellValue(sheet, pageResultCell))) {
						errors.add(new ValidationError(sheetName, pageResultCell,
								messageSource.getMessage(ValidatorConstants.VALIDATION_CELL_INVALID_RESULT,
										new String[] { pageResultCell }, LocaleContextHolder.getLocale())));
					} else if (Arrays.asList(ValidatorConstants.NOT_ALLOWED_RESULT_TYPES)
							.contains(getCellValue(sheet, pageResultCell))) {
						// Cannot exists N/T or N/D values
						errors.add(new ValidationError(sheetName, pageResultCell,
								messageSource.getMessage(ValidatorConstants.VALIDATION_CELL_NOTPERMITED_RESULT,
										new String[] { pageResultCell }, LocaleContextHolder.getLocale())));
					}
				}

			}
			tableRowIndex = tableRowIndex + principleTableSeparation;

			// in old filles in sheet 01 , separation between tables is one row more
			if (principleTableSeparation == ValidatorConstants.PRINCIPLE_TABLE_SEPARATION_IRAP_33_PAGES
					&& ValidatorConstants.SHEET_P1_PERCEPTIBLE_NAME.equalsIgnoreCase(sheetName)
					&& tableRowIndex == 646) {
				tableRowIndex++;
			}
		}

		return errors;
	}

	/**
	 * Validate sheet results.
	 *
	 * @param sheet the sheet
	 * @return the list
	 */
	private List<ValidationError> validateSheetResults(final Object sheet) {

		List<ValidationError> errors = new ArrayList<ValidationError>();
		String sheetName = getSheetName(sheet);
		// B-C-19 --> 53 filled

		int tableRowIndex = 19;
		if (maxNumPages == 30) {
			tableRowIndex = 20;
		}

		int countFilled = 0;

		for (int i = 0; i < maxNumPages; i++) {

			int currentRow = tableRowIndex + i;
			String pageTitleCell = ValidatorConstants.COLUMN_B + currentRow;
			String pageUrlCell = ValidatorConstants.COLUMN_C + currentRow;

			if (!cellIsEmpty(sheet, pageTitleCell) || !cellIsEmpty(sheet, pageUrlCell)) {
				countFilled++;
			}
		}

		// Less or greater than filled

		if (countFilled!=0 && countFilled < numPages) {

			String principleTitleCell = ValidatorConstants.COLUMN_C + (tableRowIndex - 1);

			errors.add(new ValidationError(sheetName, principleTitleCell,
					messageSource.getMessage(ValidatorConstants.VALIDATION_PRINCIPLE_LESS_PAGES,
							new String[] { getCellValue(sheet, principleTitleCell) },
							LocaleContextHolder.getLocale())));
		} else if (countFilled > numPages) {
			String principleTitleCell = ValidatorConstants.COLUMN_C + (tableRowIndex - 1);

			errors.add(new ValidationError(sheetName, principleTitleCell,
					messageSource.getMessage(ValidatorConstants.VALIDATION_PRINCIPLE_GREATER_PAGES,
							new String[] { getCellValue(sheet, principleTitleCell) },
							LocaleContextHolder.getLocale())));

		}

		// B-C-60 --> 94 filled

		tableRowIndex = 60;
		if (maxNumPages == 30) {
			tableRowIndex = 56;
		}
		countFilled = 0;

		for (int i = 0; i < maxNumPages; i++) {

			int currentRow = tableRowIndex + i;
			String pageTitleCell = ValidatorConstants.COLUMN_B + currentRow;
			String pageUrlCell = ValidatorConstants.COLUMN_C + currentRow;

			if (!cellIsEmpty(sheet, pageTitleCell) || !cellIsEmpty(sheet, pageUrlCell)) {
				countFilled++;
			}
		}

		// Less or greater than filled
		if (countFilled!=0 && countFilled < numPages) {

			String principleTitleCell = ValidatorConstants.COLUMN_C + (tableRowIndex - 1);

			errors.add(new ValidationError(sheetName, principleTitleCell,
					messageSource.getMessage(ValidatorConstants.VALIDATION_PRINCIPLE_LESS_PAGES,
							new String[] { getCellValue(sheet, principleTitleCell) },
							LocaleContextHolder.getLocale())));
		} else if (countFilled > numPages) {
			String principleTitleCell = ValidatorConstants.COLUMN_C + (tableRowIndex - 1);

			errors.add(new ValidationError(sheetName, principleTitleCell,
					messageSource.getMessage(ValidatorConstants.VALIDATION_PRINCIPLE_GREATER_PAGES,
							new String[] { getCellValue(sheet, principleTitleCell) },
							LocaleContextHolder.getLocale())));

		}

		// D54 --> AG54 not in finished (30 columns)
		int initColumnNumber = 3;
		int rowNumber = 53; // 0 based

		if (maxNumPages == 30) {
			rowNumber = 50;
		}
		int principleRowNumber = 17; // 0 based

		for (int i = 0; i < 30; i++) {
			int columnNumber = initColumnNumber + i;
			CellReference cr = new CellReference(rowNumber, columnNumber);

			if (ValidatorConstants.IN_REVIEW_VALUE.equalsIgnoreCase(getCellValue(sheet, cr.formatAsString()))) {
				CellReference principle = new CellReference(principleRowNumber, columnNumber);
				errors.add(new ValidationError(sheetName, cr.formatAsString(),
						messageSource.getMessage(ValidatorConstants.VALIDATION_RESULTS_PENDING,
								new String[] { getCellValue(sheet, principle.formatAsString()) },
								LocaleContextHolder.getLocale())));
			}

		}

		// D95 --> W95 not in finished (20 columns)

		rowNumber = 94; // 0 based
		principleRowNumber = 58; // 0 based
		if (maxNumPages == 30) {
			rowNumber = 86;
		}

		for (int i = 0; i < 20; i++) {
			int columnNumber = initColumnNumber + i;
			CellReference cr = new CellReference(rowNumber, columnNumber);
			if (ValidatorConstants.IN_REVIEW_VALUE.equalsIgnoreCase(getCellValue(sheet, cr.formatAsString()))) {
				CellReference principle = new CellReference(principleRowNumber, columnNumber);
				errors.add(new ValidationError(sheetName, cr.formatAsString(),
						messageSource.getMessage(ValidatorConstants.VALIDATION_RESULTS_PENDING,
								new String[] { getCellValue(sheet, principle.formatAsString()) },
								LocaleContextHolder.getLocale())));
			}

		}

		// Check values in this sheet are same in principles
		// Check not N/T or N/D --> D19 --> D53 --> AG19 --> AG53
		initColumnNumber = 3; // 0 based
		rowNumber = 18; // 0 based
		principleRowNumber = 17; // 0 based

		for (int i = 0; i < 30; i++) {
			for (int j = 0; i < maxNumPages; i++) {
				int columnNumber = initColumnNumber + i;
				int currentRowNumber = rowNumber + j;
				CellReference cr = new CellReference(currentRowNumber, columnNumber);
				if (Arrays.asList(ValidatorConstants.NOT_ALLOWED_RESULT_TYPES)
						.contains(getCellValue(sheet, cr.formatAsString()))) {
					CellReference principle = new CellReference(principleRowNumber, columnNumber);
					errors.add(new ValidationError(sheetName, cr.formatAsString(),
							messageSource.getMessage(
									ValidatorConstants.VALIDATION_RESULTS_NOTALLOWED, new String[] {
											getCellValue(sheet, principle.formatAsString()), cr.formatAsString() },
									LocaleContextHolder.getLocale())));
				}
			}
		}

		// Check not N/T or N/D --> D60 --> D94 --> W19 --> W53

		initColumnNumber = 3; // 0 based
		rowNumber = 59; // 0 based
		principleRowNumber = 58; // 0 based

		for (int i = 0; i < 20; i++) {
			for (int j = 0; i < maxNumPages; i++) {
				int columnNumber = initColumnNumber + i;
				int currentRowNumber = rowNumber + j;
				CellReference cr = new CellReference(currentRowNumber, columnNumber);
				if (Arrays.asList(ValidatorConstants.NOT_ALLOWED_RESULT_TYPES)
						.contains(getCellValue(sheet, cr.formatAsString()))) {
					CellReference principle = new CellReference(principleRowNumber, columnNumber);
					errors.add(new ValidationError(sheetName, cr.formatAsString(),
							messageSource.getMessage(
									ValidatorConstants.VALIDATION_RESULTS_NOTALLOWED, new String[] {
											getCellValue(sheet, principle.formatAsString()), cr.formatAsString() },
									LocaleContextHolder.getLocale())));
				}
			}
		}

		return errors;
	}

	/**
	 * Gets the cell value.
	 *
	 * @param sheet         the sheet
	 * @param cellReference the cell reference
	 * @return the cell value
	 */
	public String getCellValue(final Object sheet, final String cellReference) {
		if (sheet instanceof Sheet) {
			return this.getOdsCellValue((Sheet) sheet, cellReference);
		} else if (sheet instanceof org.apache.poi.ss.usermodel.Sheet) {
			return this.getXlsxCellValue((org.apache.poi.ss.usermodel.Sheet) sheet, cellReference);
		}
		return null;
	}

	/**
	 * Cell is empty.
	 *
	 * @param sheet         the sheet
	 * @param cellReference the cell reference
	 * @return the boolean
	 */
	public Boolean cellIsEmpty(final Object sheet, final String cellReference) {
		if (sheet instanceof Sheet) {
			return this.cellOdsIsEmpty((Sheet) sheet, cellReference);
		} else if (sheet instanceof org.apache.poi.ss.usermodel.Sheet) {
			return this.cellXslxIsEmpty((org.apache.poi.ss.usermodel.Sheet) sheet, cellReference);
		}
		return null;

	}

	/**
	 * Gets the cell value.
	 *
	 * @param sheet         the sheet
	 * @param cellReference the cell reference
	 * @return the cell value
	 */
	private String getOdsCellValue(final Sheet sheet, final String cellReference) {

		MutableCell<SpreadSheet> c = sheet.getCellAt(cellReference);

		if (c == null) {
			return ValidatorConstants.STRING_EMPTY;
		} else {
			return c.getTextValue();
		}

	}

	/**
	 * Cell is empty.
	 *
	 * @param sheet         the sheet
	 * @param cellReference the cell reference
	 * @return true, if successful
	 */
	private boolean cellOdsIsEmpty(final Sheet sheet, final String cellReference) {

		MutableCell<SpreadSheet> c = sheet.getCellAt(cellReference);
		if (c == null) {
			return true;
		} else if (c != null && StringUtils.isEmpty(c.getTextValue().trim())) {
			return true;
		}

		return false;
	}

	/**
	 * Gets the cell value.
	 *
	 * @param sheet         the sheet
	 * @param cellReference the cell reference
	 * @return the cell value
	 */
	private String getXlsxCellValue(final org.apache.poi.ss.usermodel.Sheet sheet, final String cellReference) {
		CellReference ref = new CellReference(cellReference);
		Row r = sheet.getRow(ref.getRow());
		Cell c = r.getCell(ref.getCol());
		if (c == null || CellType.BLANK.equals(c.getCellType())) {
			return ValidatorConstants.STRING_EMPTY;
		} else if (CellType.STRING.equals(c.getCellType())) {
			return c.getStringCellValue();
		} else if (CellType.NUMERIC.equals(c.getCellType())) {
			// cast to int
			return String.valueOf((int) c.getNumericCellValue());
		} else if (CellType.BOOLEAN.equals(c.getCellType())) {
			return String.valueOf(c.getBooleanCellValue());
		} else if (CellType.FORMULA.equals(c.getCellType())) {

			if (CellType.STRING.equals(evaluator.evaluateFormulaCell(c))) {
				return c.getStringCellValue();
			} else if (CellType.NUMERIC.equals(evaluator.evaluateFormulaCell(c))) {
				// cast to int
				return String.valueOf((int) c.getNumericCellValue());
			} else {
				return ValidatorConstants.STRING_EMPTY;
			}

		} else {
			return ValidatorConstants.STRING_EMPTY;
		}

	}

	/**
	 * Cell is empty.
	 *
	 * @param sheet         the sheet
	 * @param cellReference the cell reference
	 * @return true, if successful
	 */
	private boolean cellXslxIsEmpty(final org.apache.poi.ss.usermodel.Sheet sheet, final String cellReference) {

		CellReference ref = new CellReference(cellReference);
		Row r = sheet.getRow(ref.getRow());
		Cell c = r.getCell(ref.getCol());
		if (c == null || CellType.BLANK.equals(c.getCellType())
				|| (CellType.STRING.equals(c.getCellType()) && StringUtils.isEmpty(c.getStringCellValue().trim()))) {
			return true;
		}

		return false;
	}

}
