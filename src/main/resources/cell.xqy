for $x in doc("src/main/resources/style.xml")/elements/element
where ($x/@type="Cell")
return concat($x/name/string(),
	"~" , $x/id/string(),
	"~" , $x/parentId/string(),
	"~"	, if($x/hide/@always/string()="false") then "Show" else "hide",
	"~"	, data($x/size/@minimalWidth),
	"~"	, data($x/size/@minimalHeight),
	"~"	, data($x/position/@x),
	"~"	, data($x/position/@y),
	"~" , data($x/position/@horizontalAnchorType),
    "~" , data($x/position/@horizontalAnchorId),	
	"~" , data($x/position/@verticalAnchorType),
	"~" , data($x/position/@verticalAnchorId),
	"~" , if($x/style/background/@mode/string()="Color") then $x/style/background/color/@rgb/string() else 
				if($x/style/background/@mode/string()="ImageAndColor") then $x/style/background/image/@src/string() 
				else if($x/style/background/@mode/string()="None") then "None" else "#000000",
	"~"	, concat("[border","[top: ",$x/style/border/top/@thickness/string(),",",$x/style/border/top/@rgb/string(),",",$x/style/border/top/@style/string(),"],",
				  	 "[bottom: ",$x/style/border/top/@thickness/string(),",",$x/style/border/top/@rgb/string(),",",$x/style/border/top/@style/string(),"],",
				  	 "[left: ",$x/style/border/top/@thickness/string(),",",$x/style/border/top/@rgb/string(),",",$x/style/border/top/@style/string(),"],",
				  	 "[right: ",$x/style/border/top/@thickness/string(),",",$x/style/border/top/@rgb/string(),",",$x/style/border/top/@style/string(),"]]"),
				  	 
	"~"	, concat("[font size: ",$x/style/font/@size/string(),", face: ",$x/style/font/@face/string(),", italic: ",$x/style/font/@italic/string(),
						"bold: ",$x/style/font/@bold/string(),", underline: ",$x/style/font/@underline/string(),", rgb: ",$x/style/font/@rgb/string(),"]"),
				
	"~"	, concat("[alignment horizontal: ",$x/style/alignment/@horizontal/string(),", vertical: ",$x/style/alignment/@vertical/string(),", wrapText: ",$x/style/alignment/@wrapText/string(),"]"),
	
	"~" , "[", string-join($x/content/expression/formula/(concat("[formula: ", string(), ",", @type/string(),"," , @dataType/string(),",", @qualification/string(),",", @dataObjectId/string(),"]")),"|") , "]"	,
	"~" , $x/content/alerters/id/string()  	 
	)