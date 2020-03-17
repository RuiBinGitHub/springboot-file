$(document).ready(function() {

	/** 确定按钮点击事件 */
	$("#commit").click(function() {
		if ($("input[name=name]").val() == "") {
			alert("请输入用户登录账号！");
			return false;
		}
		if ($("input[name=pass]").val() == "") {
			alert("请输入用户登录账号！");
			return false;
		}
		$(this).css("background-color", "#ccc");
		$(this).attr("disabled", true);
		$("#form1").submit();
	});
	
});