for $x in doc("src/main/resources/style.xml")/elements/element
where ($x/@type="PageZone")
return concat($x/name/string(),
	"~" , $x/id/string(),
	"~"	, if($x/hide/@always/string()="false") then "Show" else "hide",
	"~"	, data($x/size/@minimalHeight),
	"~" , if($x/style/background/@mode/string()="Color") then $x/style/background/color/@rgb/string() else "#000000",
	"~"	, concat("[border: ","[top: ",$x/style/border/top/@thickness/string(),",",$x/style/border/top/@rgb/string(),",",$x/style/border/top/@style/string(),"],",
				  	 "[bottom: ",$x/style/border/top/@thickness/string(),",",$x/style/border/top/@rgb/string(),",",$x/style/border/top/@style/string(),"],",
				  	 "[left: ",$x/style/border/top/@thickness/string(),",",$x/style/border/top/@rgb/string(),",",$x/style/border/top/@style/string(),"],",
				  	 "[right: ",$x/style/border/top/@thickness/string(),",",$x/style/border/top/@rgb/string(),",",$x/style/border/top/@style/string(),"]]")
	)