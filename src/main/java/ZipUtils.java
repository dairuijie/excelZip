import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 
 * @ClassName: ZipUtils
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: drj
 * @date: 2019年1月13日 下午6:56:44
 * 
 * @Copyright: 2019
 *
 */
public class ZipUtils {
	
	private static final String[] NAMES= {"user.xlsx","demo02.xlsx"};

	public static void main(String[] args) {
		String sourceFilePath = "D:\\excel";
		String zipFilePath = "D:\\excel";
		String fileName = "dairuijie";
		boolean flag = fileToZip(sourceFilePath, zipFilePath, fileName);
		if (flag) {
			System.out.println("文件打包成功!");
		} else {
			System.out.println("文件打包失败!");
		}
	}

	public static boolean fileToZip(String sourceFilePath, String zipFilePath, String fileName) {
		boolean flag = false;
		File sourceFile = new File(sourceFilePath);
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		ZipOutputStream zos = null;

		if (sourceFile.exists() == false) {
			System.out.println("待压缩的文件目录：" + sourceFilePath + "不存在.");
		} else {
			try {
				File zipFile = new File(zipFilePath + "/" + fileName + ".zip");
				if (zipFile.exists()) {
					System.out.println(zipFilePath + "目录下存在名字为:" + fileName + ".zip" + "打包文件.");
				} else {
					File[] sourceFiles = sourceFile.listFiles();
					if (null == sourceFiles || sourceFiles.length < 1) {
						System.out.println("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");
					} else {
						fos = new FileOutputStream(zipFile);
						zos = new ZipOutputStream(new BufferedOutputStream(fos));
						byte[] bufs = new byte[1024 * 10];
						for (int i = 0; i < sourceFiles.length; i++) {
							// 创建ZIP实体，并添加进压缩包
							ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
							zos.putNextEntry(zipEntry);
							// 读取待压缩的文件并写进压缩包里
							fis = new FileInputStream(sourceFiles[i]);
							bis = new BufferedInputStream(fis, 1024 * 10);
							int read = 0;
							while ((read = bis.read(bufs, 0, 1024 * 10)) != -1) {
								zos.write(bufs, 0, read);
							}
						}
						flag = true;
					}
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} finally {
				// 关闭流
				try {
					if (null != bis)
						bis.close();
					if (null != zos)
						zos.close();
				} catch (IOException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
		}
		return flag;
	}
	
	public static void   streamZip( String target, List<ByteArrayOutputStream> osList) {
		ZipOutputStream zos = null;
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(target);
			zos = new ZipOutputStream(new BufferedOutputStream(fos));
			int i =0;
			for(ByteArrayOutputStream os : osList) {
				ZipEntry zipEntry = new ZipEntry(NAMES[i]);
				zos.putNextEntry(zipEntry);
				zos.write(os.toByteArray());
				i++;
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			// 关闭流
			try {
				if (null != bis)
					bis.close();
				if (null != zos)
					zos.close();
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
	}
}
