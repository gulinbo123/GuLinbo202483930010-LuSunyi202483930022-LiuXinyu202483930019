import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class WebService {

	// ================= 登录验证 =================
	boolean loginValidation(String un, String pw) {
		HttpURLConnection con = null;
		BufferedReader in = null;

		try {
			String encodedUsername = URLEncoder.encode(un, "UTF-8");
			String encodedPassword = URLEncoder.encode(pw, "UTF-8");

			String urlStr = "http://localhost:8080/validate"
					+ "?username=" + encodedUsername
					+ "&password=" + encodedPassword;

			URL url = new URL(urlStr);
			con = (HttpURLConnection) url.openConnection();

			// ⭐⭐⭐ 核心：超时设置 ⭐⭐⭐
			con.setConnectTimeout(3000); // 3 秒连不上直接失败
			con.setReadTimeout(3000);    // 3 秒没响应直接失败
			con.setRequestMethod("GET");

			int responseCode = con.getResponseCode();
			System.out.println("Response Code: " + responseCode);

			InputStream stream;
			if (responseCode == HttpURLConnection.HTTP_OK) {
				stream = con.getInputStream();
			} else {
				// ❗服务器异常时不能用 getInputStream
				stream = con.getErrorStream();
			}

			in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = in.readLine()) != null) {
				response.append(line.trim());
			}

			String result = response.toString();
			System.out.println("Response Body: " + result);

			return Boolean.parseBoolean(result);

		} catch (Exception e) {
			// ❗ 不再打印一大坨红字
			System.out.println("❌ 登录请求失败：" + e.getMessage());
			return false;
		} finally {
			// ⭐⭐⭐ 确保资源释放 ⭐⭐⭐
			try {
				if (in != null) in.close();
			} catch (IOException ignored) {}
			if (con != null) con.disconnect();
		}
	}

	// ================= 查询玩家 =================
	void fetchPlayers(int wid) {
		sendSimpleGet("http://localhost:8080/players?wid=" + wid);
	}

	// ================= 查询世界 =================
	void fetchWorlds(int pid) {
		sendSimpleGet("http://localhost:8080/worlds?pid=" + pid);
	}

	// ================= 通用 GET 方法 =================
	private void sendSimpleGet(String urlStr) {
		HttpURLConnection con = null;
		BufferedReader in = null;

		try {
			URL url = new URL(urlStr);
			con = (HttpURLConnection) url.openConnection();

			con.setConnectTimeout(3000);
			con.setReadTimeout(3000);
			con.setRequestMethod("GET");

			int responseCode = con.getResponseCode();
			System.out.println("Response Code: " + responseCode);

			InputStream stream = responseCode == 200
					? con.getInputStream()
					: con.getErrorStream();

			in = new BufferedReader(new InputStreamReader(stream, "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				System.out.println(line);
			}

		} catch (Exception e) {
			System.out.println("❌ 请求失败：" + e.getMessage());
		} finally {
			try {
				if (in != null) in.close();
			} catch (IOException ignored) {}
			if (con != null) con.disconnect();
		}
	}
}
