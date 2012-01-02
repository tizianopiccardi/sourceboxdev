<html> 
  <head> 
    <title>SourceBox</title> 
    

    <link rel="stylesheet" href="libs/codemirror/lib/codemirror.css"> 
    <script src="libs/codemirror/lib/codemirror.js"></script> 
    <link rel="stylesheet" href="libs/codemirror/theme/default.css"> 
<script type="text/javascript" src="libs/codemirror/modes.js"></script>
    <script type="text/javascript" src="libs/jquery/jquery.js"></script>
    <script type="text/javascript" src="libs/jquery/jquery.timers.js"></script>
   
   <script type="text/javascript" src="libs/jquery/jquery-ui.js"></script>
  <link type="text/css" href="css/jqueryui/ui-lightness/jqueryui.css" rel="stylesheet" /> 
   <link type="text/css" href="css/jquery_notification.css" rel="stylesheet" /> 
  
   
   <script type="text/javascript" src="libs/json/json2.js"></script>
   
  <!--  <script type="text/javascript" src="libs/googlediff/diff_match_patch.js"></script> -->
   <script type="text/javascript" src="js/view/SyncManager.js"></script>
   <script type="text/javascript" src="js/view/EventsManager.js"></script>
   <script type="text/javascript" src="js/view/User.js"></script>
   <script type="text/javascript" src="js/view/Insert.js"></script>
   <script type="text/javascript" src="js/view/OTEngine.js"></script>
   <script type="text/javascript" src="js/view/view.js"></script>
   <script type="text/javascript" src="js/view/EditorManager.js"></script>
   <script type="text/javascript" src="js/view/chat.js"></script>
    <script type="text/javascript" src="js/view/UsersManager.js"></script>
    <script type="text/javascript" src="libs/jquery/jquery.scrollTo.js"></script>
    
        <script type="text/javascript" src="libs/jquery/jquery_notification.js"></script>
    
    <!-- <script type="text/javascript" src="js/view/EditorManager.js"></script> -->
 
    <link rel="stylesheet" href="css/view.css"> 
     <link rel="stylesheet" href="css/bbar.css"> 
     <script type="text/javascript" src="js/view/bbar.js"></script>
    
   <script type="text/javascript">document.alias = "${alias}"</script>
    
    
  </head> 
  <body> 

 <!--
<div id="user" style="color: red;position:absolute;font-size:10px !important;"><b>^xyz</b></div>
<!-- form><textarea id="code" name="code"> 
</textarea></form>  -->
<br>

<div id="save-loading" align="center">
<img alt="loading" src="images/load.gif" id="save-loading-img"><br>Saving...
</div>

<div id="error" title="Ops..."></div>


<div id="save-status">Not saved</div>

<#include "/bbar.ftl">  



<div class="chat" id="chat-box" title="Chat">
	<div id="chat-wrapper">
		<div id="comment-top" class="ui-widget-header" style="cursor: pointer;" onClick="javascript:$('#chat-wrapper').fadeToggle();"><b>Chat</b></div>
		<div id="comments-list"></div>
		<div id="comments-text-wrapper">
		<textarea id="comment" rows="3" class="text"></textarea></div>
	</div>
	
	<a id="chat-button" class="chat-button" href="#" onClick="javascript: toggleChat(); return false;">
	<img border="0" id="chat-dot" alt="dot" src="images/gray_dot.gif">
Chat
	</a>
</div>



	<div id="password-form" title="Password request">
		<p>This discussion is protected</p>
	
		<div>
		
		Join it:
		<center>
			<table class="invisible-table"><tr>
			<td><label for="pass" class="text">Password: </label></td>
			<td><input type="password" name="pass" id="pass" class="text ui-widget-content ui-corner-all" /></td>
			<td><img alt="loading" src="images/load.gif" id="pw-check-img"></td>
			</tr>
			<tr><td>&nbsp;</td><td><div id="wrong-password" class="error">Wrong password...</div></td>
			</tr>
			</table>
		</center>
		or open a new discussion:
		<br><br><center><a href="./"><img src="images/logo32.png" border="0"></a></center>
		
		</div>
	</div>
	
	
	<div id="share-dialog" title="Share url">
		<p>Box URL:</p>
		<center>
			<input type="text" name="boxurl" id="boxurl" style="width : 250px;" class="text ui-widget-content ui-corner-all" />
		</center>

	</div>


<div id="nickname-panel" title="Enter your name:" align="center">
<label for="pass">Name: </label>

<input type="text" name="nick" id="nick" class="text ui-widget-content ui-corner-all" />
<img alt="loading" src="images/load.gif" id="nick-set-img" style="margin-top:15px;">
</div>

<div id="users-markers"></div>

  </body> 
</html> 