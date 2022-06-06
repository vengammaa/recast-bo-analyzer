for $x in doc("src/main/resources/style.xml")/elements/element
where ($x/@type="Section")
return concat($x/id/string(),
	"~" , $x/parentId/string(),
	"~" , data($x/@type),
	"~" , $x/style/background/color/@rgb/string(),
	"~"  , "Axes ", string-join($x/content/axes/axis/(concat("[role:",@role/string(),"` ",string-join(expressions/formula/(concat("[formula:[Name:",string(),", hide:",@hide,", dataType:",@dataType,", dataObjId:",@dataObjectId,", qualification:",@qualification,"]]")),"|"))), "#^")	
)