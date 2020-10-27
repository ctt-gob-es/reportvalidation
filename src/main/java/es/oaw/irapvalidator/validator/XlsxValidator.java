package es.oaw.irapvalidator.validator;

import java.util.ArrayList;
import java.util.List;

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

/**
 * The Class XlsxValidator.
 */
@Service
public class XlsxValidator {

	/** The Constant SI. */
	private static final String SI = "Sí";

	/** The Constant VALIDATION_CELL_EMPTY. */
	private static final String VALIDATION_CELL_EMPTY = "validation.cell.empty";

	/** The message source. */
	@Autowired
	private MessageSource messageSource;

	/**
	 * Validate.
	 *
	 * @param workbook the workbook
	 * @return the list
	 */
	public List<ValidationError> validate(final Workbook workbook) {
		List<ValidationError> errors = new ArrayList<ValidationError>();

		// TODO Validate
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
		// TODO C9 --> VALIDAR DIR3
		// TODO C13 --> VALIDAR DIR3
		final String columnNotEmptyLabel = "B";
		final String columnNotEmptyValue = "C";
		final int[] cellNotEmptyRows = new int[] { 7, 9, 11, 13, 17, 19, 21, 25, 27, 29, 31, 33, 35, 39, 41, 45 };

		for (int i = 0; i < cellNotEmptyRows.length; i++) {
			if (cellIsEmpty(sheet, columnNotEmptyValue + cellNotEmptyRows[i])) {
				errors.add(
						new ValidationError(sheet.getSheetName(), columnNotEmptyValue + cellNotEmptyRows[i],
								messageSource.getMessage(VALIDATION_CELL_EMPTY,
										new String[] { columnNotEmptyValue + cellNotEmptyRows[i],
												columnNotEmptyLabel + cellNotEmptyRows[i] },
										LocaleContextHolder.getLocale())));

			}
		}

		// D49 --> D59 (at least one selected)
		final String atLeastOneSelectedColumn = "D";
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

		boolean atLeastOneMandatorySelected = false;
		// C9 --> C13 (at least one selected)

		String atLeastOneSelectedColumn = "D";
		int atLeastOneSelectedFirstRow = 9;
		int atLeastOneSelectedLastRow = 12;

		for (int i = 0; i < atLeastOneSelectedLastRow - atLeastOneSelectedFirstRow; i++) {
			if (SI.equalsIgnoreCase(getCellValue(sheet, atLeastOneSelectedColumn + atLeastOneSelectedFirstRow + i))) {
				atLeastOneMandatorySelected = true;
				break;
			}
		}
		
		if (!atLeastOneMandatorySelected) {
			errors.add(new ValidationError(sheet.getSheetName(), "", messageSource
					.getMessage("validation.sheet1.techs.mandatory.atleastone", null, LocaleContextHolder.getLocale())));
		}

		// C9 --> C13 (at least one selected)
		atLeastOneSelectedColumn = "D";
		atLeastOneSelectedFirstRow = 9;
		atLeastOneSelectedLastRow = 13;

		boolean atLeastOneTechSelected = false;

		for (int i = 0; i < atLeastOneSelectedLastRow - atLeastOneSelectedFirstRow; i++) {
			if (SI.equalsIgnoreCase(getCellValue(sheet, atLeastOneSelectedColumn + atLeastOneSelectedFirstRow + i))) {
				atLeastOneTechSelected = true;
				break;
			}
		}
		// F9 --> F13 (at least one selected)
		atLeastOneSelectedColumn = "F";
		atLeastOneSelectedFirstRow = 9;
		atLeastOneSelectedLastRow = 13;
		for (int i = 0; i < atLeastOneSelectedLastRow - atLeastOneSelectedFirstRow; i++) {
			if (SI.equalsIgnoreCase(getCellValue(sheet, atLeastOneSelectedColumn + atLeastOneSelectedFirstRow + i))) {
				atLeastOneTechSelected = true;
				break;
			}
		}

		atLeastOneSelectedColumn = "I";
		atLeastOneSelectedFirstRow = 9;
		atLeastOneSelectedLastRow = 12;

		// I9 --> C12 (at least one selected)
		for (int i = 0; i < atLeastOneSelectedLastRow - atLeastOneSelectedFirstRow; i++) {
			if (SI.equalsIgnoreCase(getCellValue(sheet, atLeastOneSelectedColumn + atLeastOneSelectedFirstRow + i))) {
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
		if (c == null || c.getCellType() == CellType.BLANK) {
			return true;
		}

		return false;
	}

}
