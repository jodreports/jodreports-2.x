<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0//EN" "http://www.w3.org/TR/REC-html40/strict.dtd">
<html>
  <head>
    <title>JOOReports - Letter Sample</title>
	<link rel="stylesheet" type="text/css" href="style.css">
  </head>
  <body>
	<h1>JOOReports - Letter Example</h1>
	
	<p>
	  Please fill in the form to generate your letter
	</p>

	<form method="post" action="">
      <table>
		<tr>
		  <td>Full Name: </td>
		  <td><input type="text" name="name" size="30"></td>
		</tr>
		<tr>
		  <td>Address: </td>
		  <td><input type="text" name="address" size="50"></td>
		</tr>
		<tr>
		  <td>Post Code: </td>
		  <td><input type="text" name="postCode" size="6"></td>
		</tr>
		<tr>
		  <td>City: </td>
		  <td><input type="text" name="city" size="30"></td>
		</tr>
		<tr>
		  <td colspan="2">
		    <input type="submit" value="Create Letter">
		  </td>
	    </tr>
	  </table>
	</form>

	<hr/>
	<p>
      This <i>JOOReports</i> example uses the HTTP parameters as the data model for the template.
	</p>

  </body>
</html>