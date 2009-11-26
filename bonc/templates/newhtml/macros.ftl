<#macro html_header_no_close title><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-type" content="text/html; charset=iso-8859-1" />
<title>${title}</title>
</#macro>
<#macro html_header title>
<@html_header_no_close title=title/>
</head>
</#macro>
<#macro type type><a href="" onclick="return navTo('class:${type.identifier}');">${StringUtil.prettyPrint(type)}</a></#macro>
<#macro pclass class><a href="" onclick="return navTo('class:${class.name.name}');">${StringUtil.prettyPrintShortenedClass(class)}</a></#macro>