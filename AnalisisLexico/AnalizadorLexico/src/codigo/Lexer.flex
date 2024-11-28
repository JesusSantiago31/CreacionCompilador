package codigo;
import static codigo.Tokens.*;
%%
%class Lexer
%type Tokens
L=[a-zA-Z_]+
D=[0-9]+
espacio=[ ,\t,\r,\n]+
%{
    public String lexeme;
%}
%%
int |
if |
else |
while {lexeme=yytext(); return Reservadas;}
{espacio} {/*Ignore*/}
"//".* {/*Ignore*/}
"+" {return operadorSuma;}  
"-" {return operadorResta;}  
"*" {return operadorMultiplicacion;}  
"/" {return operadorDivision;}  
"%" {return operadorModulo;}  
"^" {return operadorPotencia;}  
"Rcuad" {return operadorRaiz;}  
"=>" {return asignacion;}  
"++" {return incremento;}  
"--" {return decremento;}  
"Pos" {return operadorPositivo;}  
"Neg" {return operadorNegativo;}  
"==" {return comparacionIgualdad;}  
">" {return mayorQue;}  
"<" {return menorQue;}  
">=<" {return mayorIgual;}  
"<=>" {return menorIgual;}  
"&" {return operadorY;}  
"|" {return operadorO;}  
"!" {return operadorNo;}  
[a-z] {return letrasMin;}  
[A-Z] {return letrasMay;}  
[0-9] {return numero;}  
"(:" {return inicioComentarioMult;}  
":)" {return finalComentarioMult;}  
"}=)" {return comentarioLinea;}  
"Ent" {return valorEntero;}  
"Flot" {return valorFlotante;}  
"Boo" {return valorBooleano;}  
"Cad" {return cadena;}  
"Cars" {return caracter;}  
";-;" {return finLinea;}  
"$l" {return saltoLinea;}  
"<<" {return inicioTexto;}  
">>" {return finalTexto;}  
"(" {return parentIzq;}  
")" {return parentDer;}  
"{" {return llaveIzq;}  
"}" {return llaveDer;}  
"[" {return corchetIzq;}  
"]" {return corchetDer;}  
":-:" {return concatenar;}  
"[]-[]" {return arreglo;}  
".." {return puntoAcceso;}  
"." {return puntoDecimial;}  
":" {return dosPuntos;}  
"\"" {return comillaDoble;}  
"'" {return comillaSimple;}  
"," {return coma;}  
"_" {return guionBajo;}  
"-" {return guionMedio;}  
"\\" {return diagonalInvertido;}  
"/" {return diagonal;}  
"¡" {return signoAdmiracionAbre;}  
"!" {return signoAdmiracionCierra;}  
"#" {return gato;}  
"$" {return pesos;}  
"si" {return condicionalIf;}  
"siNo" {return condicionalElse;}  
"ciclo" {return cicloFor;}  
"mientras" {return cicloWhile;}  
"hacer" {return cicloDo;}  
"segun" {return funcionSwitch;}  
"caso" {return funcionCase;}  
"salir" {return funcionSalirCase;}  
"predeterminado" {return predeterminado;}  
"Detener" {return detener;}  
"Constante" {return constante;}  
"Variable" {return variable;}  
"funcion" {return funcion;}  
"Clase" {return clase;}  
"Imprimir" {return imprimir;}  
"Importar" {return importar;}  
"Leer" {return funcionLeer;}  
"IntroducirD" {return funcionIntroducirDatos;}  
"PI" {return pi;}  
"E" {return euler;}

{L}({L}|{D})* {lexeme=yytext(); return Identificador;}

("(-"{D}+")")|{D}+ {lexeme=yytext(); return Numero;}

 . {return ERROR;}
