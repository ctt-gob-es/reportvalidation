package es.oaw.irapvalidator;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import es.oaw.irapvalidator.validator.ValidationError;
import es.oaw.irapvalidator.validator.XlsxValidator;

@SpringBootTest
class IrapvalidatorApplicationTests {

	@Autowired
	private XlsxValidator xlsxValidator;

	@Test
	void textXlsx() throws Exception {

		File inputFile = new File(
				"/home/alvaro/Development/Projects/irapvalidator/src/test/resources/Informe_Revision_Profunidad_v1.xlsx");
		FileInputStream inputStream = new FileInputStream(inputFile);
		XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
		List<ValidationError> errors = xlsxValidator.validate(workbook);
		if (!CollectionUtils.isEmpty(errors)) {
			for (ValidationError error : errors) {
				System.out.println(error.toString());
			}

		}

	}

}
