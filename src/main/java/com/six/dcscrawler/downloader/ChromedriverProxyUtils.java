package com.six.dcscrawler.downloader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author liusong
 * @date 2017年8月30日
 * @email 359852326@qq.com
 */
public class ChromedriverProxyUtils {

	public static String getChromeProxyExtension(String path, String workName, String proxy) throws IOException {
		String chromeProxyExtensionsDir = path + File.separator + workName + File.separator + "chrome-proxy-extensions";
		String usernameAndPwd = StringUtils.substringBefore(proxy, "@");
		String username = StringUtils.substringBefore(usernameAndPwd, ":");
		String password = StringUtils.substringAfter(usernameAndPwd, ":");
		String ipAndPort = StringUtils.substringAfter(proxy, "@");
		String ip = StringUtils.substringBefore(ipAndPort, ":");
		String port = StringUtils.substringAfter(ipAndPort, ":");
		File extensionFileDir = FileUtils.getFile(chromeProxyExtensionsDir);
		if (!extensionFileDir.exists()) {
			extensionFileDir.mkdirs();
		}
		String extensionFilePath = chromeProxyExtensionsDir + File.separator + proxy.replace(':', '_') + ".zip";
		File extensionFile = FileUtils.getFile(extensionFilePath);
		if (extensionFile.exists() ? extensionFile.delete() : false)
			;
		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(extensionFile));

		String manifestPath = path + File.separator + "manifest.json";
		String manifest = FileUtils.readFileToString(FileUtils.getFile(manifestPath));
		String backgroundContentPath = path + File.separator + "background.js";
		String backgroundContent = FileUtils.readFileToString(FileUtils.getFile(backgroundContentPath));
		backgroundContent = backgroundContent.replace("%proxy_host", ip);
		backgroundContent = backgroundContent.replace("%proxy_port", port);
		backgroundContent = backgroundContent.replace("%username", username);
		backgroundContent = backgroundContent.replace("%password", password);

		ZipEntry manifestZipEntry = new ZipEntry("manifest.json");
		zipOut.putNextEntry(manifestZipEntry);
		zipOut.write(manifest.getBytes());

		ZipEntry backgroundZipEntry = new ZipEntry("background.js");
		zipOut.putNextEntry(backgroundZipEntry);
		zipOut.write(backgroundContent.getBytes());
		zipOut.close();
		return extensionFilePath;
	}

}
