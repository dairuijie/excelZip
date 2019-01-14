import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @ClassName: ReadJson
 * @Description:TODO(这里用一句话描述这个类的作用)
 * @author: drj
 * @date: 2019年1月14日 下午10:57:15
 * 
 * @Copyright: 2019
 *
 */
public class ReadJson {
	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader("src/main/java/data.json"));
		try {
			StringBuffer sb = new StringBuffer();
			String s = null;
			while ((s = br.readLine()) != null) {
				sb.append(s.trim());
			}
			System.out.println(sb.toString());
			JSONObject json = JSONObject.fromObject(sb.toString());
			JSONArray result_province_array = json.getJSONArray("data");
			for (int i = 0; i < 32; i++) {
				System.out.println(result_province_array.getJSONObject(i).getString("name"));
				JSONArray result_province_city_array = result_province_array.getJSONObject(i).getJSONArray("child");
				for(int j =0; j< result_province_city_array.size(); j++) {
					System.err.println(""+result_province_city_array.getJSONObject(j).getString("name")+" parentId is" + result_province_array.getJSONObject(i).getString("id"));
					JSONArray result_province_city_district_array = result_province_city_array
							.getJSONObject(j).getJSONArray("child");
					//System.out.println(result_province_city_district_array);
					for(int k =0; k< result_province_city_district_array.size(); k++) {
						System.out.println(result_province_city_district_array.getJSONObject(k).getString("name") + result_province_city_array.getJSONObject(j).getString("id"));
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			br.close();
		}
	}
}
