package com.springboot.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.entity.User;
import com.springboot.utils.AppUtils;

@RestController
@RequestMapping(value = "/file")
public class FileController {

	@Value("${mypath}")
	private String mypth;

	private File file = null;

	@RequestMapping(value = "/findfile")
	public String findFile(String name) throws IOException {
		User user = (User) AppUtils.findMap("user");
		if (!user.getRole().equals("role1") && user.getVest().indexOf(name) == -1)
			return "提示：权限不足或文件不存在！";
		file = new File(mypth + name + "/wx.json");
		if (!file.exists())
			return "提示：权限不足或文件不存在！";
		FileReader reader = new FileReader(file);
		BufferedReader buffer = new BufferedReader(reader);
		String content = null;
		String results = null;
		while ((content = buffer.readLine()) != null) {
			results = content;
		}
		if (results != null && results.length() > 4)
			results = results.substring(2, results.length() - 2).replace("\",\"", " ");
		buffer.close();
		reader.close();
		return results;
	}

	@RequestMapping(value = "/editfile")
	public String editFile(String text) throws IOException {
		if (file == null || !file.exists())
			return "提示：权限不足或文件不存在！";
		FileOutputStream stream = new FileOutputStream(file);
		OutputStreamWriter writer = new OutputStreamWriter(stream, "utf-8");
		text = "[\"" + text + "\"]";
		writer.write(text.replace(" ", "\",\""));
		writer.close();
		stream.close();
		return "提示：文件修改成功！";
	}
}
