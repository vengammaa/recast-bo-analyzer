for $x in doc("src/main/resources/style.xml")/elements/element
where ($x/@type="Visualization")
return concat($x/name/string(),
	"~" , $x/id/string(),
	"~" , $x/parentId/string(),
	"~" , data($x/@type),
	"~"	, if($x/hide/@always/string()="false") then "Show" else "hide",
	"~"	, data($x/size/@minimalWidth),
	"~"	, data($x/size/@minimalHeight),
	"~"	, data($x/position/@x),
	"~"	, data($x/position/@y),
	"~" , data($x/position/@horizontalAnchorType),
    "~" , data($x/position/@horizontalAnchorId),	
	"~" , data($x/position/@verticalAnchorType),
	"~" , data($x/position/@verticalAnchorId),
	"~" , $x/style/background/color/@rgb/string(),
	"~" , $x/style/background/color/@alpha/string(),	
	"~"	, "Border ","[top: ","[thickness:",$x/style/border/top/@thickness/string(),", rgb:",$x/style/border/top/@rgb/string(),", style:",$x/style/border/top/@style/string(),"]], ",
				  	 "[bottom: ","[thickness:",$x/style/border/bottom/@thickness/string(),", rgb:",$x/style/border/bottom/@rgb/string(),", style:",$x/style/border/bottom/@style/string(),"]], ",
				  	 "[left: ","[thickness:",$x/style/border/left/@thickness/string(),", rgb:",$x/style/border/left/@rgb/string(),", style:",$x/style/border/left/@style/string(),"]], ",
				  	 "[right: ","[thickness:",$x/style/border/right/@thickness/string(),", rgb:",$x/style/border/right/@rgb/string(),", style:",$x/style/border/right/@style/string(),"]]",	
	"~"  , $x/content/chart/@type/string(),
	"~"  , $x/content/chart/@technicalName/string(),
	"~"  , "Title [Visibility:",$x/content/chart/title/@visible/string(),", LabelMode:",$x/content/chart/title/label/@mode/string(),", LabelName:",$x/content/chart/title/label/string()," ]",
	"~"  , $x/content/chart/legend/@visibility/string(),
	"~"  , $x/content/chart/plotArea/title/@visible/string(),
	"~"  , "Axes ", string-join($x/content/chart/axes/axis/(concat("[role:",@role/string(),"` ","name:",name/string(),"` ",string-join(expressions/formula/(concat("[formula:[Name:",string(),", hide:",@hide,", dataType:",@dataType,", dataObjId:",@dataObjectId,", qualification:",@qualification,"]]")),"|"))), "#^")	

	
	)			  	 