for $x in doc("src/main/resources/style.xml")/elements/element
where not($x/@type=("Section","Cell","HTable","PageZone","Visualization","VTable","XTable"))
return concat($x/id/string(),
 "~" , " Unknown Element",
 "~" , $x/@type
 )
