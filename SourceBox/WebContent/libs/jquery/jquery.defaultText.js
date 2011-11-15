/**
 * jQuery defaultText v1.0
 * 2011-08-20
 * http://code.google.com/p/jquery-default-text/
 * Copyright Beckerle Matias
 * Licensed under The MIT License (http://www.opensource.org/licenses/mit-license.php)
 */
(function(a){a.fn.defaultText=function(b){return this.each(function(){var c=a(this);var d=c.val();if(b){d=b;c.val(d)}c.focus(function(){if(a(this).val()==d){a(this).val("")}});c.blur(function(){if(a(this).val()==""){a(this).val(d)}})})}})(jQuery)