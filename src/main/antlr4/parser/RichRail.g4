grammar RichRail;

// Rules
command         : newcommand | addcommand | getcommand | delcommand | remcommand;
newcommand      : newtraincommand | newwagoncommand;
newtraincommand : 'new' 'train' ID 'engine' ID;
newwagoncommand : 'new' 'wagon' ID 'numseats' NUMBER;
addcommand		: 'add' type ID 'to' ID;
getcommand      : 'getnumseats' type id=ID;
delcommand      : 'delete' type ID;
remcommand      : 'remove' NUMBER 'from' ID;
type            : 'train' | 'wagon' | 'locomotive';

// Tokens
ID          : ('a'..'z')('a'..'z'|'0'..'9')*;
NUMBER      : ('0'..'9')+;
WHITESPACE  : [ \t\r\n\u000C] -> skip;