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
 * @Description:TODO(������һ�仰��������������)
 * @author: drj
 * @date: 2019��1��13�� ����6:56:44
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
			System.out.println("�ļ�����ɹ�!");
		} else {
			System.out.println("�ļ����ʧ��!");
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
			System.out.println("��ѹ�����ļ�Ŀ¼��" + sourceFilePath + "������.");
		} else {
			try {
				File zipFile = new File(zipFilePath + "/" + fileName + ".zip");
				if (zipFile.exists()) {
					System.out.println(zipFilePath + "Ŀ¼�´�������Ϊ:" + fileName + ".zip" + "����ļ�.");
				} else {
					File[] sourceFiles = sourceFile.listFiles();
					if (null == sourceFiles || sourceFiles.length < 1) {
						System.out.println("��ѹ�����ļ�Ŀ¼��" + sourceFilePath + "���治�����ļ�������ѹ��.");
					} else {
						fos = new FileOutputStream(zipFile);
						zos = new ZipOutputStream(new BufferedOutputStream(fos));
						byte[] bufs = new byte[1024 * 10];
						for (int i = 0; i < sourceFiles.length; i++) {
							// ����ZIPʵ�壬����ӽ�ѹ����
							ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());
							zos.putNextEntry(zipEntry);
							// ��ȡ��ѹ�����ļ���д��ѹ������
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
				// �ر���
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
			// �ر���
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
