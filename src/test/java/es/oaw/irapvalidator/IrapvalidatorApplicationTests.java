package es.oaw.irapvalidator;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jopendocument.dom.spreadsheet.SpreadSheet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.oaw.irapvalidator.validator.ValidationError;
import es.oaw.irapvalidator.validator.WorkbookValidator;


/**
 * The Class IrapvalidatorApplicationTests.
 */
@SpringBootTest
class IrapvalidatorApplicationTests {

	/** The xlsx validator. */
	@Autowired
	private WorkbookValidator unifiedValidator;

	/**
	 * Test XLSX.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testXLSX() throws Exception {

		File inputFile = new File(
				"/home/alvaro/Development/Projects/irapvalidator/src/test/resources/Informe_Revision_Profunidad_v1.xlsx");
		FileInputStream inputStream = new FileInputStream(inputFile);
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		Map<String,List<ValidationError>> errors = unifiedValidator.validate(workbook);
//		if (!CollectionUtils.isEmpty(errors)) {
//			for (ValidationError error : errors) {
//				System.out.println(error.toString());
//			}
//
//		}

	}

	/**
	 * Test ODS.
	 *
	 * @throws Exception the exception
	 */
	@Test
	void testODS() throws Exception {

		File inputFile = new File(
				"/home/alvaro/Development/Projects/irapvalidator/src/test/resources/Informe_Revision_Profunidad_v1.ods");
		final SpreadSheet workbook = SpreadSheet.createFromFile(inputFile);
		Map<String,List<ValidationError>> errors = unifiedValidator.validate(workbook);
//		if (!CollectionUtils.isEmpty(errors)) {
//			for (ValidationError error : errors) {
//				System.out.println(error.toString());
//			}
//
//		}

	}

}
