import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 
 * @ClassName: ExcelUtils
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: drj
 * @date: 2019年1月13日 下午7:25:57
 * 
 * @Copyright: 2019
 *
 */
public class ExcelUtils {
	private static final String PATH_ONE = "src/main/java/user.xlsx";
	private static final String PATH_TWO = "src/main/java/user2.xlsx";
	private static final String zipFilePath = "D:\\excel\\dairuijie.zip";
	private static final String[] HAED = { "age", "userName", "loginName" };

	public static void main(String[] args) throws IOException {
		Users user = null;
		JSONArray list = new JSONArray();
		for (int i = 0; i < 5; i++) {
			user = new Users();
			user.setAge(i);
			user.setUserName("userName" + i);
			user.setLoginName("loginName" + i);
			list.add(user);
		}
		Workbook wb = null;
		ByteArrayOutputStream osOne = null;
		ByteArrayOutputStream osTwo = null;
		// OutputStream outXlsx = new FileOutputStream(TARGET_PATH);
		List<ByteArrayOutputStream> osList = new ArrayList<ByteArrayOutputStream>();
		try {
			osOne = new ByteArrayOutputStream();
			wb = getWorkBookObj(PATH_ONE);
			exportToExcel(list, wb);
			wb.write(osOne);
			osList.add(osOne);
			osTwo = new ByteArrayOutputStream();
			wb = getWorkBookObj(PATH_TWO);
			exportToExcel(list, wb);
			wb.write(osTwo);
			osList.add(osTwo);
			ZipUtils.streamZip(zipFilePath, osList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			wb.close();
			osOne.close();
			osTwo.close();
		}
	}

	public static Workbook getWorkBookObj(String path) throws IOException {
		Workbook wb = null;
		FileInputStream fileInputStream = new FileInputStream(path);
		try {
			wb = WorkbookFactory.create(fileInputStream);
		} catch (Exception ex) {
			System.out.println(ex);
		}
		return wb;
	}

	public static void exportToExcel(JSONArray jsonArray, Workbook workbook) {
		// 声明一个工作薄
		// 遍历集合数据，产生数据行
		XSSFSheet sheet = (XSSFSheet) workbook.getSheet("Sheet1");
		int rowIndex = 1;
		Row dataRow = sheet.getRow(0);
		for (Object obj : jsonArray) {
			JSONObject jo = (JSONObject) JSONObject.toJSON(obj);
			Row row = sheet.createRow(rowIndex);
			for (int i = 0; i < dataRow.getLastCellNum(); i++) {
				Cell newCell = row.createCell(i);
				Object o = jo.get(HAED[i]);
				String cellValue = "";
				if (o == null)
					cellValue = "";
				else if (o instanceof Date)
					cellValue = new SimpleDateFormat("yyyy-MM-dd").format(o);
				else if (o instanceof Float || o instanceof Double)
					cellValue = new BigDecimal(o.toString()).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
				else
					cellValue = o.toString();

				newCell.setCellValue(cellValue);
			}
			rowIndex++;
		}
	}

}
