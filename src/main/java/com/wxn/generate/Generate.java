package com.wxn.generate;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import javax.swing.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Generate {
	private static String sysPath = System.getProperty("user.dir");
	private static String projectFilePath= File.separator + "wxn_mybatisGenerate";

	public static void genController(JProgressBar bar, JTable table, String packageName, String packageName2, String fileSavePath) {
		System.out.println("生成controller");
		try {
			InputStream file = Generate.class.getResourceAsStream("/template/controller.txt");
			String content = getResourceConten(file);
			List<Map<String, Object>> list = dealTables(table);
			int pro = list.size()<90?90/list.size():1;
			int n = 0;
			//导入需要的表
			Iterator<Map<String, Object>> var10 = list.iterator();
			while(var10.hasNext()) {
				Map<String, Object> map = var10.next();
				++n;
				String modelUpName = (String)map.get("modelUpName");
				String modelName = (String)map.get("modelName");
				String out = content.replace("${packageName}", packageName).replace("${modelUpName}", modelUpName).replace("${modelName}", modelName);
				if ("".equals(packageName2)) {
					out = out.replace("${packageName2}", packageName2);
				} else {
					out = out.replace("${packageName2}", "." + packageName2);
				}

				String path = (("".equals(fileSavePath) || null == fileSavePath)?sysPath:fileSavePath) + projectFilePath + File.separator + "out";
				if ("".equals(packageName2)) {
					path = path + File.separator + "com" + File.separator + packageName;
				} else {
					path = path + File.separator + "com" + File.separator + packageName + File.separator + packageName2;
				}

				System.out.println(path + File.separator + "controller");
				File conFileDir = new File(path + File.separator + "controller" + File.separator);
				conFileDir.mkdirs();
				File conFile = new File(path + File.separator + "controller" + File.separator + modelUpName + "Controller.java");
				FileWriter fw = new FileWriter(conFile);
				fw.write(out);
				fw.flush();
				fw.close();
				AppMain.pro = Math.min(pro * n, 99);
				Thread.sleep(100);
			}
		} catch (Exception var20) {
			var20.printStackTrace();
		}
		AppMain.pro = 100;
	}
	
	public static void genService(JProgressBar bar, JTable table, String packageName, String packageName2, String fileSavePath) {
		System.out.println("生成Service");
		try {
			InputStream ServiceFile = Generate.class.getResourceAsStream("/template/service.txt");
			InputStream ServiceImplFile = Generate.class.getResourceAsStream("/template/serviceImpl.txt");
			String contentService = getResourceConten(ServiceFile);
			String contentServiceImpl = getResourceConten(ServiceImplFile);
			List<Map<String, Object>> list = dealTables(table);
			int pro = list.size()<90?90/list.size():1;
			int n = 0;
			
			//导入需要的表
			for(Map<String, Object> map: list) {
				n++;
				String modelUpName = (String)map.get("modelUpName");
				String modelName = (String)map.get("modelName");
				String tableName = (String)map.get("tableName");
				String type = (String)map.get("type");
				String serviceOut = contentService
						.replace("${packageName}", packageName)
						.replace("${modelUpName}", modelUpName)
						.replace("${dateParam}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
				if ("".equals(packageName2)) {
					serviceOut = serviceOut.replace("${packageName2}", packageName2);
				} else {
					serviceOut = serviceOut.replace("${packageName2}", "." + packageName2);
				}

				if (-1 != type.indexOf("VARCHAR")) {
					serviceOut = serviceOut.replace("${keyType}", "String");
				} else if (-1 != type.indexOf("BIGINT")) {
					serviceOut = serviceOut.replace("${keyType}", "Long");
				} else {
					serviceOut = serviceOut.replace("${keyType}", "Integer");
				}

				String path = (("".equals(fileSavePath) || null == fileSavePath)?sysPath:fileSavePath) + projectFilePath + "/out";
				if ("".equals(packageName2)) {
					path = path+ File.separator + "com" + File.separator  + packageName;
				} else {
					path = path + File.separator + "com" + File.separator + packageName + File.separator + packageName2;
				}

				System.out.println(path + File.separator + "service");
				File serviceFileDir = new File(path + File.separator + "service/");
				serviceFileDir.mkdirs();
				File serviceFile = new File(path + File.separator + "service" + File.separator + modelUpName + "Service.java");
				FileWriter sfw = new FileWriter(serviceFile);
				sfw.write(serviceOut);
				sfw.flush();
				sfw.close();
				String serviceImplOut = contentServiceImpl.toString().replace("${packageName}", packageName).replace("${modelUpName}", modelUpName).replace("${modelName}", modelName);
				if ("".equals(packageName2)) {
					serviceImplOut = serviceImplOut.replace("${packageName2}", packageName2);
				} else {
					serviceImplOut = serviceImplOut.replace("${packageName2}", "." + packageName2);
				}

				if (-1 != type.indexOf("VARCHAR")) {
					serviceImplOut = serviceImplOut.replace("${keyType}", "String");
				} else if (-1 != type.indexOf("BIGINT")) {
					serviceImplOut = serviceImplOut.replace("${keyType}", "Long");
				} else {
					serviceImplOut = serviceImplOut.replace("${keyType}", "Integer");
				}

				System.out.println(path + "/server/impl");
				File serviceImplFileDir = new File(path + File.separator + "service" + File.separator + "impl");
				serviceImplFileDir.mkdirs();
				File serviceImplFile = new File(path + File.separator + "service" + File.separator + "impl" + File.separator + modelUpName + "ServiceImpl.java");
				FileWriter sifw = new FileWriter(serviceImplFile);
				sifw.write(serviceImplOut);
				sifw.flush();
				sifw.close();
				AppMain.pro = Math.min(pro * n, 99);
				Thread.sleep(100);
			}
		} catch (Exception var26) {
			var26.printStackTrace();
		}
		AppMain.pro = 100;
	}

	public static void genMapper(JProgressBar bar,String packageName, String packageName2, JTable table, String url, String user, String password, String fileSavePath) {
		try {
			if(!url.contains("&amp;")){
				url = url.replace("&", "&amp;");
			}
			String path = (("".equals(fileSavePath) || null == fileSavePath)?sysPath:fileSavePath) + projectFilePath + File.separator + "out";
			System.out.println("path:" + path);

//			InputStream baseEntity = Generate.class.getResourceAsStream("/template/BaseEntity.txt");
//			String baseEntityContent = getResourceConten(baseEntity);
//			String baseEntityOut = baseEntityContent
//					.replace("${dateParam}", LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
//					.replace("${packageName}", packageName);
//			if ("".equals(packageName2)) {
//				baseEntityOut = baseEntityOut.replace("${packageName2}", packageName2);
//			} else {
//				baseEntityOut = baseEntityOut.replace("${packageName2}", "." + packageName2);
//			}
//
//			String baseEntityOutPath = path;
//			if ("".equals(packageName2)) {
//				baseEntityOutPath = baseEntityOutPath+ File.separator + "com" + File.separator + packageName;
//			} else {
//				baseEntityOutPath = baseEntityOutPath + File.separator + "com" + File.separator + packageName + File.separator + packageName2;
//			}
//
//			File baseEntityFileDir = new File(baseEntityOutPath + "/entity/");
//			baseEntityFileDir.mkdirs();
//
//			OutputStreamWriter write = new OutputStreamWriter(new FileOutputStream(baseEntityOutPath + File.separator + "entity" + File.separator + "BaseEntity.java"),"GBK");
//			BufferedWriter writer=new BufferedWriter(write);
//			writer.write(baseEntityOut);
//			writer.flush();
//			writer.close();

			InputStream xmlFile = Generate.class.getResourceAsStream("/template/generatorConfig.xml");
			String content = getResourceConten(xmlFile);
			List<Map<String, Object>> list = dealTables(table);
			StringBuilder tableConfig = new StringBuilder();
			int pro = list.size()<90?90/list.size():1;
			int n = 0;
			
			//导入需要的表
			for(Map<String, Object> map: list) {
				n++;
				String modelUpName = (String)map.get("modelUpName");
				String modelName = (String)map.get("modelName");
				String tableName = (String)map.get("tableName");
				tableConfig.append("\n<table tableName=\"" + tableName + "\" enableCountByExample=\"false\" " +
						"enableUpdateByExample=\"false\" enableDeleteByExample=\"false\"  " +
						"enableSelectByExample=\"false\"  selectByExampleQueryId=\"false\" />");
				AppMain.pro = Math.min(pro * n, 99);
				Thread.sleep(100);
			}

			String conf = content.replace("${tableConfig}", tableConfig)
					.replace("${url}", url)
					.replace("${user}", user)
					.replace("${password}", password)
					.replace("${packageName}", packageName)
					.replace("${target}", path);
			if ("".equals(packageName2)) {
				conf = conf.replace("${packageName2}", "");
			} else {
				conf = conf.replace("${packageName2}", "." + packageName2);
			}

			File fileDir = new File(path);
			fileDir.mkdirs();
			File file = new File(path + File.separator + "generatorConfig.xml");
			FileWriter fw = new FileWriter(file);
			fw.write(conf);
			fw.flush();
			fw.close();
			List<String> warnings = new ArrayList();
			boolean overwrite = true;
			ConfigurationParser cp = new ConfigurationParser(warnings);
			Configuration config = cp.parseConfiguration(file);
			DefaultShellCallback callback = new DefaultShellCallback(overwrite);
			MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
			myBatisGenerator.generate(null);
			file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
		AppMain.pro = 100;
		
	}

	private static String getResourceConten(InputStream in) throws Exception {
		StringBuilder template = new StringBuilder();
		InputStreamReader inputStreamReader = new InputStreamReader(in);
		BufferedReader reader = new BufferedReader(inputStreamReader);

		String tmp;
		while((tmp = reader.readLine()) != null) {
			template.append(tmp + "\t\n");
		}

		reader.close();
		inputStreamReader.close();
		in.close();
		return template.toString();
	}

	private static List<Map<String, Object>> dealTables(JTable table) {
		List<Map<String, Object>> list = new ArrayList();
		int[] rows = table.getSelectedRows();
		boolean flag = false;
		int[] var7 = rows;
		int var6 = rows.length;

		for(int var5 = 0; var5 < var6; ++var5) {
			int row = var7[var5];
			String tableName = (String)table.getValueAt(row, 0);
			String type = (String)table.getValueAt(row, 1);
			StringBuilder sb = new StringBuilder();
			StringBuilder sbUp = new StringBuilder();

			for(int i = 0; i < tableName.length(); ++i) {
				char s = tableName.charAt(i);
				if (i == 0) {
					sb.append(s);
					sbUp.append(Character.toUpperCase(s));
				} else if ('_' == s) {
					flag = true;
				} else if (flag) {
					sb.append(Character.toUpperCase(s));
					sbUp.append(Character.toUpperCase(s));
					flag = false;
				} else {
					sb.append(s);
					sbUp.append(s);
				}
			}

			String modelUpName = sbUp.toString();
			String modelName = sb.toString();
			Map<String, Object> map = new HashMap<>();
			map.put("modelUpName", modelUpName);
			map.put("modelName", modelName);
			map.put("tableName", tableName);
			map.put("type", type);
			list.add(map);
		}

		return list;
	}

}
