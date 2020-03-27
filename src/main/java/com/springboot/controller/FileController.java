package com.springboot.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.entity.User;
import com.springboot.utils.AppUtils;

@RestController
@RequestMapping(value = "/file")
public class FileController {

	@Value("${mypath}")
	private String mypath;

	private File file = null;

	@RequestMapping(value = "/findinfo")
	public ModelAndView findInfo() {
		ModelAndView view = new ModelAndView();
		User user = (User) AppUtils.findMap("user");
		if (StringUtils.isEmpty(user)) {
			view.setViewName("/users/loginview");
			return view;
		}
		File file = new File(mypath);
		String vest = user.getVest();
		List<String> list1 = Arrays.asList(file.list());
		List<String> list2 = Arrays.asList(vest.split(","));
		if (!"role1".equals(user.getRole())) {
			list1 = new ArrayList<String>(list1);
			list2 = new ArrayList<String>(list2);
			list1.retainAll(list2);
		}
		view.setViewName("files/findinfo");
		view.addObject("list", list1);
		return view;
	}

	@RequestMapping(value = "/findfile")
	public String findFile(String name) throws IOException {
		User user = (User) AppUtils.findMap("user");
		List<String> vests = Arrays.asList(user.getVest().split(","));
		if (!user.getRole().equals("role1") && !vests.contains(name))
			return "提示：权限不足或文件不存在！";
		file = new File(mypath + name + "/wx.json");
		if (!file.exists())
			return "提示：权限不足或文件不存在！";
		FileReader reader = new FileReader(file);
		BufferedReader buffer = new BufferedReader(reader);
		String content = null;
		String results = null;
		while ((content = buffer.readLine()) != null) 
			results = content;
		if (results != null && results.length() >= 4) {
			results = results.substring(2, results.length() - 2);
			results = results.replace("\",\"", " ");
		}
		buffer.close();
		reader.close();
		return results;
	}

	@RequestMapping(value = "/editfile")
	public String editFile(String name, String text) throws IOException {
		file = new File(mypath + name + "/wx.json");
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
