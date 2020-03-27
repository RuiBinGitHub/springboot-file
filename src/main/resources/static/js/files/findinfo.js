$(document).ready(function() {

	$("input[name=iname]").keydown(function() {
        if (event.keyCode == 13)
            $("#query").click();
    });
	
	$("#query").click(function() {
		var name = $("input[name=iname]").val();
		if (name == null || name.length == 0) {
			alert("请输入文件夹名称！");
			return false;
		}
		var value = Ajax("findfile", {name : name});
		if (value == "提示：权限不足或文件不存在！")
			alert("提示：权限不足或文件不存在！");
		else  {
			$("input[name=name]").val(name);
			$("textarea[name=text]").val(value);
		}
	});
	
	$("#commit").click(function() {
		var name = $("input[name=name]").val();
		var text = $("textarea[name=text]").val().trim();
		var value = Ajax("editfile", {name: name, text : text});
		if (value == "提示：权限不足或文件不存在！")
			alert("提示：权限不足或文件不存在！");
		else {
			alert("提示：文件修改成功！");
			setTimeout("location.reload()", 1000);
		}
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