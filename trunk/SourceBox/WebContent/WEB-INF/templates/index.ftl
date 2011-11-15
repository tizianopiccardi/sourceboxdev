<html>
<head>


<meta name="description" content="Share and discuss your source code in real time" />
<meta name="keywords" content="source, code, share, chat, board, discuss, paste, collaborative, developer, box" />


<link type="text/css" href="css/jqueryui/ui-lightness/jqueryui.css" rel="stylesheet" /> 

<link rel="stylesheet" href="libs/codemirror/lib/codemirror.css"> 
<script src="libs/codemirror/lib/codemirror.js"></script> 
    
<link type="text/css" href="css/styles.css" rel="stylesheet" />
<script type="text/javascript" src="libs/jquery/jquery.js"></script>
<script type="text/javascript" src="libs/jquery/jquery-ui.js"></script>
  <!-- <script type="text/javascript" src="js/view/SyncManager.js"></script>  -->
  
  <script type="text/javascript" src="libs/jquery/jquery.defaultText.js"></script>
  
<script type="text/javascript" src="js/index/index.js"></script>

<link rel="stylesheet" href="libs/codemirror/theme/default.css"> 

<link rel="stylesheet" href="css/index.css"> 

<script type="text/javascript">
var SyncManager = {
		bufferAdd: function(from, to, newText){}
}

</script>


<script type="text/javascript" src="libs/codemirror/modes.js"></script>


<title>SourceBox</title>

</head>


<body>

<#include "/header.ftl">  

<center><div id="maincontent">
<table border="0" style="margin-bottom:0px;">
<tr><td>
<textarea name="code" id="codetext"></textarea>
</td>
<td valign="top">
  	<fieldset id="infoset">
 
	   <label>Language:</label>
	    <select id="language-selector" class="text ui-corner-all" size="18">
		  <#include "/language_selector.ftl">  
		</select>
	

	 
	 <hr>
	 		<label>Security code:</label>
		<img id="captcha" src="captcha.png" alt="CAPTCHA Image" height="40" width="115" />
		<input type="text" id="captcha_code" size="14" maxlength="6"  class="text ui-widget-content ui-corner-all"/>
		<a href="#" class="ui-state-default" onclick="document.getElementById('captcha').src = 'captcha.png?' + Math.random(); return false">
		[ Different Image ]
		</a>
	 
 	</fieldset>
</td>
</tr>
 
 
 
 
</table>

<table style="margin-top:0px;" border="0"><tr>
<td align="right"><input type="checkbox" name="private" id="private" /></td>
<td>Private</td>
</tr><tr>
<td colspan="2"><input type="text" id="password" class="text ui-widget-content ui-corner-all"/></td>
</tr>

<tr>
<td align="right"><input type="checkbox" name="readonly" id="readonly"/></td>
<td>Read Only</td>
</tr>

</table>


		<div id="send">Make new BOX</div>

<div id="error"></div>

</center>


</div>

<div id="upload-dialog">
	<p>Wait...</p>
	<div id="uploadbar"></div>
</div>

<center>
	<div id="BoxURL">
	</div>
</center>

<#include "/footer.ftl">  

</body>
</html>