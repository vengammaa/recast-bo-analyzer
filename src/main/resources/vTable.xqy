for $x in doc("src/main/resources/style.xml")/elements/element
where ($x/@type="VTable")
return concat($x/name/string(),
	"~" , $x/id/string(),
	"~" , $x/parentId/string(),
	"~" , data($x/@type),
	"~"	, if($x/hide/@always/string()="false") then "Show" else "hide",
	"~"	, data($x/size/@minimalWidth),
	"~"	, data($x/size/@minimalHeight),
	"~"	, data($x/position/@x),
	"~"	, data($x/position/@y),
	"~" , $x/style/alternateColor/@rgb/string(),
	"~" , $x/style/alternateColor/@frequency/string(),
	"~"	, "Border ","[top: ","[thickness:",$x/style/border/top/@thickness/string(),", rgb:",$x/style/border/top/@rgb/string(),", style:",$x/style/border/top/@style/string(),"]], ",
				  	 "[bottom: ","[thickness:",$x/style/border/bottom/@thickness/string(),", rgb:",$x/style/border/bottom/@rgb/string(),", style:",$x/style/border/bottom/@style/string(),"]], ",
				  	 "[left: ","[thickness:",$x/style/border/left/@thickness/string(),", rgb:",$x/style/border/left/@rgb/string(),", style:",$x/style/border/left/@style/string(),"]], ",
				  	 "[right: ","[thickness:",$x/style/border/right/@thickness/string(),", rgb:",$x/style/border/right/@rgb/string(),", style:",$x/style/border/right/@style/string(),"]]",	
	"~"  , "Axes ", string-join($x/content/axes/axis/(concat("[role:",@role/string(),"` ",string-join(expressions/formula/(concat("[formula:[Name:",string(),", hide:",@hide,", dataType:",@dataType,", dataObjId:",@dataObjectId,", qualification:",@qualification,"]]")),"|"))), "#^"),
	"~"  , "Layout ", string-join($x/content/layout/zone/(string-join(child/(concat("[child:[row:",@row/string(),", rowSpan:",@rowSpan/string(),", column:",@column/string(),", columnSpan:",@columnSpan/string(),", id:",@id/string(),"]]")),"|")), ";")	
)		