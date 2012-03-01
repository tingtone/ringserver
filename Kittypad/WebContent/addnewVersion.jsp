<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Add new Version</title>
</head>
<body>
<br>
<br>
<br>

			<form method="post" action="/game/addAppVersion" >
						<table width="740" height="100%" align="center" cellpadding="0" cellspacing="0">
						<tr>
						 <td style="text-align:right;">user</td>
						 <td><input name="userName" type="text"  /></td>
						</tr>
						<tr>
						<td style="text-align:right;">password</td>
						<td><input type="password" name="password" ></td>
						</tr>
						<tr>
						<td style="text-align:right;">appName</td>
						<td><input name="appName" type="text" /></td>
						</tr>
						<tr>
						<td style="text-align:right;">appVersion</td>
						<td><input name="appVersion" type="text"  /></td>
						</tr>
						<tr>
						<td style="text-align:right;">appLink</td>
						<td><input name="appLink" type="text"  /></td>
						</tr>
						<tr><td></td>
						<td>
							<input type="submit" value="submit" style="margin-left:30px;"/>
							</td></tr>
						</table>
						</form>
											
						
</body>
</html>