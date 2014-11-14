Repository for Groovy Command Line tools

Premise:
-----------------------------------------------------------------------
set up %GROOVY-CMD% Home directory 

Common:
-----------------------------------------------------------------------
m.groovy        	Memorize the input parameter, and allow parameter to 
					be edited.
					Can be used by many other functions, 


Dictionary
-----------------------------------------------------------------------
dic.cmd 		search english in google transalate
g.cmd			grep english vocabulary

Vocabulary
-----------------------------------------------------------------------
bdc.groovy		Recite Vocabulary


Stocks
-----------------------------------------------------------------------
gsw.groovy		List stock quotes using google finance url
sw.groovy       List stock ticker using netfonts url
yf.groovy  		Edit stock symbols, Yahoo Finance 

Push/List/Remove/Change Directory
-----------------------------------------------------------------------
ld.cmd          List Directory History, without parameter, list Directory History
				with number, goto selected directory
				
				ld -l or ld    list directory
				ld -p          push current directory
				ld -r 0        remove directory 0 from the list
				ld 0           cd directory 0
				
				
gld.groovy      Groovy List directory history , default history file .dirlist
pd.groovy     	Push list, save current directory to .dirlist

Replace a line of properties file
-----------------------------------------------------------------------
rp.cmd  %1 %2 %3

rp.groovy fileName attribute value



