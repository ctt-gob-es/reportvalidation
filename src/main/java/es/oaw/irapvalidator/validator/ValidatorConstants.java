package es.oaw.irapvalidator.validator;

/**
 * The Class ValidatorConstants.
 */
public final class ValidatorConstants {

	/** The Constant STRING_EMPTY. */
	public static final String STRING_EMPTY = "";

	/** The Constant VALIDATION_RESULTS_NOTALLOWED. */
	public static final String VALIDATION_RESULTS_NOTALLOWED = "validation.results.notallowed";

	/** The Constant VALIDATION_RESULTS_PENDING. */
	public static final String VALIDATION_RESULTS_PENDING = "validation.results.pending";

	/** The Constant VALIDATION_CELL_NOTPERMITED_RESULT. */
	public static final String VALIDATION_CELL_NOTPERMITED_RESULT = "validation.cell.notpermited.result";

	/** The Constant VALIDATION_CELL_INVALID_RESULT. */
	public static final String VALIDATION_CELL_INVALID_RESULT = "validation.cell.invalid.result";

	/** The Constant VALIDATION_CELL_EMPTY_RESULT. */
	public static final String VALIDATION_CELL_EMPTY_RESULT = "validation.cell.empty.result";

	/** The Constant VALIDATION_PRINCIPLE_GREATER_PAGES. */
	public static final String VALIDATION_PRINCIPLE_GREATER_PAGES = "validation.principle.greater.pages";

	/** The Constant VALIDATION_PRINCIPLE_LESS_PAGES. */
	public static final String VALIDATION_PRINCIPLE_LESS_PAGES = "validation.principle.less.pages";

	/** The Constant VALIDATION_SAMPLE_PAGES_RANDOM. */
	public static final String VALIDATION_SAMPLE_PAGES_RANDOM = "validation.sample.pages.random";

	/** The Constant VALIDATION_SAMPLE_PAGES. */
	public static final String VALIDATION_SAMPLE_PAGES = "validation.sample.pages";

	/** The Constant SHEET_03_RANDOM_PAGE_COUNT_CELLREFERENCE. */
	public static final String SHEET_03_RANDOM_PAGE_COUNT_CELLREFERENCE = "D49";

	/** The Constant SHEET_03_PAGE_COUNT_CELLREFERENCE. */
	public static final String SHEET_03_PAGE_COUNT_CELLREFERENCE = "D45";

	/** The Constant VALIDATION_CELL_EMPTY_BREADCUMB. */
	public static final String VALIDATION_CELL_EMPTY_BREADCUMB = "validation.cell.empty.breadcumb";

	/** The Constant VALIDATION_CELL_INVALID_URL. */
	public static final String VALIDATION_CELL_INVALID_URL = "validation.cell.invalid.url";

	/** The Constant VALIDATION_CELL_EMPTY_URL. */
	public static final String VALIDATION_CELL_EMPTY_URL = "validation.cell.empty.url";

	/** The Constant VALIDATION_CELL_INVALID_TYPE. */
	public static final String VALIDATION_CELL_INVALID_TYPE = "validation.cell.invalid.type";

	/** The Constant VALIDATION_CELL_EMPTY_TYPE. */
	public static final String VALIDATION_CELL_EMPTY_TYPE = "validation.cell.empty.type";

	/** The Constant VALIDATION_CELL_EMPTY_SHORTNAME. */
	public static final String VALIDATION_CELL_EMPTY_SHORTNAME = "validation.cell.empty.shortname";

	/** The Constant VALIDATION_SHEET1_TECHS_ATLEASTONE. */
	public static final String VALIDATION_SHEET1_TECHS_ATLEASTONE = "validation.sheet1.techs.atleastone";

	/** The Constant VALIDATION_SHEET1_TECHS_MANDATORY_ATLEASTONE. */
	public static final String VALIDATION_SHEET1_TECHS_MANDATORY_ATLEASTONE = "validation.sheet1.techs.mandatory.atleastone";

	/** The Constant VALIDATION_SHEET1_AMBIT_ATLEASTONE. */
	public static final String VALIDATION_SHEET1_AMBIT_ATLEASTONE = "validation.sheet1.ambit.atleastone";

	/** The Constant VALIDATION_CELL_INVALID_DIR3AMBIT. */
	public static final String VALIDATION_CELL_INVALID_DIR3AMBIT = "validation.cell.invalid.dir3ambit";

	/** The Constant VALIDATION_CELL_INVALID_DIR3. */
	public static final String VALIDATION_CELL_INVALID_DIR3 = "validation.cell.invalid.dir3";

	/** The Constant VALIDATION_CELL_SAMPLE_CORRECT. */
	public static final String VALIDATION_CELL_SAMPLE_CORRECT = "validation.cell.sample.correct";

	/** The Constant RESULT_PASS. */
	public static final String RESULT_PASS = "Pasa";

	/** The Constant RESULT_FAIL. */
	public static final String RESULT_FAIL = "Falla";

	/** The Constant RESULT_NOT_APPLY. */
	public static final String RESULT_NOT_APPLY = "N/A";

	/** The Constant RESULT_CANNOT_TELL. */
	public static final String RESULT_CANNOT_TELL = "N/D";

	/** The Constant RESULT_NOT_TESTED. */
	public static final String RESULT_NOT_TESTED = "N/T";

	/** The Constant IN_REVIEW_VALUE. */
	public static final String IN_REVIEW_VALUE = "En curso";

	/** The Constant VALID_SAMPLE_LABEL. */
	public static final String VALID_SAMPLE_LABEL = "¿MUESTRA CORRECTA?";

	/** The Constant COLUMN_I. */
	public static final String COLUMN_I = "I";

	/** The Constant COLUMN_F. */
	public static final String COLUMN_F = "F";

	/** The Constant COLUMN_D. */
	public static final String COLUMN_D = "D";

	/** The Constant COLUMN_D. */
	public static final String COLUMN_E = "E";

	/** The Constant COLUMN_C. */
	public static final String COLUMN_C = "C";

	/** The Constant COLUMN_B. */
	public static final String COLUMN_B = "B";

	/** The Constant MAX_PAGES. */
	public static final int MAX_PAGES = 35;

	/** The Constant YES_VALUE. */
	public static final String YES_VALUE = "Sí";
	
	/** The Constant NO_VALUE. */
	public static final String NO_VALUE = "no";

	/** The Constant VALIDATION_CELL_EMPTY. */
	public static final String VALIDATION_CELL_EMPTY = "validation.cell.empty";

	/** The Constant ALLOWED_PAGE_TYPES. */
	public static final String[] ALLOWED_PAGE_TYPES = new String[] { "Página inicio", "Inicio de sesión", "Mapa web",
			"Contacto", "Ayuda", "Legal", "Servicio / Proceso", "Servicio", "Proceso", "Búsqueda",
			"Declaración accesibilidad", "Mecanismo de comunicación", "Pagina tipo", "Otras páginas",
			"Documento descargable", "Aleatoria" };

	/** The Constant ALLOWED_RESULT_TYPES. */
	public static final String[] ALLOWED_RESULT_TYPES = new String[] { RESULT_NOT_TESTED, RESULT_CANNOT_TELL,
			RESULT_NOT_APPLY, RESULT_FAIL, RESULT_PASS };

	/** The Constant NOT_ALLOWED_RESULT_TYPES. */
	public static final String[] NOT_ALLOWED_RESULT_TYPES = new String[] { RESULT_NOT_TESTED, RESULT_CANNOT_TELL, };

	/** The Constant SHEET_01_MANDATORY_CONTENT_ROWS. */
	public static final int[] SHEET_01_MANDATORY_CONTENT_ROWS = new int[] { 7, 9, 11, 13, 17, 19, 21, 25, 27, 29, 31,
			33, 35, 39, 41, 45 };

	/** The Constant PRINCIPLE_TABLE_SEPARATION_IRAP_35_PAGES. */
	public static final int PRINCIPLE_TABLE_SEPARATION_IRAP_35_PAGES = 38;

	/** The Constant PRINCIPLE_TABLE_SEPARATION_IRAP_33_PAGES. */
	public static final int PRINCIPLE_TABLE_SEPARATION_IRAP_33_PAGES = 33;

	/** The Constant SHEET_P1_PERCEPTIBLE_NAME. */
	public static final String SHEET_P1_PERCEPTIBLE_NAME = "P1.Perceptible";

}
