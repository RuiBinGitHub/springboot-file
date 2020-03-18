package com.springboot.controller;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.springboot.entity.User;
import com.springboot.utils.AppUtils;
import com.springboot.utils.FileUtils;

@RestController
@RequestMapping(value = "/user")
public class UserController {

	@Value(value = "${mypath}")
	private String mypath;

	@Resource
	private FileUtils fileUtils;

	@RequestMapping(value = "/login")
	public ModelAndView login(String name, String pass) {
		ModelAndView view = new ModelAndView();
		List<User> list = fileUtils.getList();
		for (int i = 0; list != null && i < list.size(); i++) {
			User user = list.get(i);
			if (user.getName().equals(name) && user.getPass().equals(pass)) {
				view.setViewName("redirect:/index/index");
				AppUtils.pushMap("user", user);
				return view;
			}
		}
		view.setViewName("users/loginview");
		view.addObject("tips", "*账号或密码不正确！");
		view.addObject("name", name);
		view.addObject("pass", pass);
		return view;
	}

	/** 退出登录 */
	@RequestMapping(value = "/quit")
	public ModelAndView quit() {
		ModelAndView view = new ModelAndView();
		view.setViewName("redirect:loginview");
		AppUtils.removeMap("user");
		System.out.println("123");
		return view;
	}

	@RequestMapping(value = "/findlist")
	public ModelAndView findlist() {
		ModelAndView view = new ModelAndView();
		User user = (User) AppUtils.findMap("user");
		// 用户未登录
		if (StringUtils.isEmpty(user)) {
			view.setViewName("users/loginview");
			return view;
		}
		// 用户权限不足
		if (!"role1".equals(user.getRole())) {
			view.setViewName("index/failuer");
			return view;
		}
		List<User> list = fileUtils.getList();
		if (list != null && list.size() != 0)
			list.remove(0); // 不显示默认管理人员
		view.setViewName("users/findlist");
		view.addObject("list", list);
		return view;
	}

	@RequestMapping(value = "/insertview")
	public ModelAndView insertView() {
		ModelAndView view = new ModelAndView();
		User user = (User) AppUtils.findMap("user");
		// 用户未登录
		if (StringUtils.isEmpty(user)) {
			view.setViewName("users/loginview");
			return view;
		}
		// 用户权限不足
		if (!"role1".equals(user.getRole())) {
			view.setViewName("index/failuer");
			return view;
		}
		File file = new File(mypath);
		view.addObject("list", file.list());
		view.setViewName("users/insert");
		return view;
	}

	@RequestMapping(value = "/updateview")
	public ModelAndView findview(int no) {
		ModelAndView view = new ModelAndView();
		User user = (User) AppUtils.findMap("user");
		// 用户未登录
		if (StringUtils.isEmpty(user)) {
			view.setViewName("users/loginview");
			return view;
		}
		// 用户权限不足
		if (!"role1".equals(user.getRole())) {
			view.setViewName("index/failuer");
			return view;
		}
		List<User> list = fileUtils.getList();
		if (list != null && no < list.size())
			view.addObject("user", list.get(no));
		File file = new File(mypath);
		view.addObject("list", file.list());
		String vest = list.get(no).getVest();
		if (!StringUtils.isEmpty(vest))
			view.addObject("vest", vest.split(","));
		else
			view.addObject("vest", new String[] {});
		view.setViewName("users/update");
		return view;
	}

	@RequestMapping(value = "/insert")
	public ModelAndView insert(User user) {
		ModelAndView view = new ModelAndView();
		User temp = (User) AppUtils.findMap("user");
		// 用户未登录
		if (StringUtils.isEmpty(temp)) {
			view.setViewName("users/loginview");
			return view;
		}
		// 用户权限不足
		if (!"role1".equals(temp.getRole())) {
			view.setViewName("index/failuer");
			return view;
		}
		user.setRole("role2");
		if (user.getVest() == null)
			user.setVest("");
		fileUtils.append(user);
		view.setViewName("redirect:/index/success");
		return view;
	}

	@RequestMapping(value = "/update")
	public ModelAndView update(int no, User user) {
		ModelAndView view = new ModelAndView();
		User temp = (User) AppUtils.findMap("user");
		// 用户未登录
		if (StringUtils.isEmpty(temp)) {
			view.setViewName("users/loginview");
			return view;
		}
		// 用户权限不足
		if (!"role1".equals(temp.getRole())) {
			view.setViewName("index/failuer");
			return view;
		}
		if (user.getVest() == null)
			user.setVest("");
		List<User> list = fileUtils.getList();
		if (list != null && no < list.size()) {
			list.get(no).setName(user.getName());
			list.get(no).setPass(user.getPass());
			list.get(no).setVest(user.getVest());
			fileUtils.rewrite(list);
		}
		view.setViewName("redirect:/index/success");
		return view;
	}

	@RequestMapping(value = "/delete")
	public boolean delete(int no) {
		User user = (User) AppUtils.findMap("user");
		// 用户未登录
		if (StringUtils.isEmpty(user))
			return false;
		// 用户权限不足
		if (!"role1".equals(user.getRole()))
			return false;
		List<User> list = fileUtils.getList();
		list.remove(no);
		fileUtils.rewrite(list);
		return true;
	}
}
