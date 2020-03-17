$(document).ready(function() {

	/** 添加按钮点击事件 */
	$("#append").click(function() {
		window.open("insertview");
	});

	$("#tab1 tr").each(function(i) {
		// 编辑用户
		$(this).find("input:eq(0)").click(function() {
			window.open("updateview?no=" + (i + 1));
		});
		// 删除用户
		$(this).find("input:eq(1)").click(function() {
			if (!confirm("确定要删除该用户吗？"))
				return false;
			$(this).css("background-color", "#ccc");
			$(this).attr("disabled", true);
			if (Ajax("delete?no=" + (i + 1), null))
				alert("用户删除成功！");
			else
				location.href = "/write/user/loginview";
			setTimeout("location.reload()", 1000);
		});
	});

	/** ************************************************ */
	function Ajax(url, data) {
		var result = null;
		$.ajax({
			url : url,
			data : data,
			type : "post",
			async : false,
			datatype : "json",
			success : function(data) {
				result = data;
			}
		});
		return result;
	}
});