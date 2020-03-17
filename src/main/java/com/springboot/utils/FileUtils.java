package com.springboot.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.springboot.entity.User;

@Component
public class FileUtils {

	@Value(value = "${myfile}")
	private String myfile;

	/** 获取用户列表 */
	public List<User> getList() {
		try {
			String content = null;
			File file = new File(myfile);
			List<User> list = new ArrayList<User>();
			FileReader reader = new FileReader(file);
			BufferedReader buffer = new BufferedReader(reader);
			while ((content = buffer.readLine()) != null) {
				System.out.println(content);
				list.add(JSON.parseObject(content, User.class));
			}
			buffer.close();
			reader.close();
			return list;
		} catch (IOException e) {
			return null;
		}
	}

	/** 更新用户列表 */
	public void rewrite(List<User> list) {
		try {
			File file = new File(myfile);
			FileOutputStream stream = new FileOutputStream(file);
			OutputStreamWriter writer = new OutputStreamWriter(stream, "utf-8");
			for (int i = 0; list != null && i < list.size(); i++)
				writer.write(JSON.toJSONString(list.get(i)) + "\n");
			writer.close();
			stream.close();
		} catch (IOException e) {
			// 系统异常
		}
	}

	/** 添加用户 */
	public void append(User user) {
		try {
			File file = new File(myfile);
			FileOutputStream stream = new FileOutputStream(file, true);
			OutputStreamWriter writer = new OutputStreamWriter(stream, "utf-8");
			writer.write(JSON.toJSONString(user) + "\n");
			writer.close();
			stream.close();
		} catch (IOException e) {
			// 系统异常
		}
	}

}
