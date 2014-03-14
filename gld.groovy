#!/usr/bin/env groovy

import groovy.json.JsonBuilder;
import groovy.json.JsonSlurper
def dataFile=".dirlist"
Map result=[:]
def curDir=new File(".").getCanonicalPath()

def slurper = new JsonSlurper()

def cmd_home=System.getenv().get("GROOVY_CMD_HOME");
if (cmd_home !=null) {
	dataFile=cmd_home + "/" + dataFile
}

result= slurper.parseText(new File(dataFile).getText())

if (args) {
	result.sort{-it.value}.eachWithIndex{key,value,index ->
	  if (Integer.valueOf(args[0])==index)
	  {
		  def f= new File(".ld.cmd")
		  f.write("cd $key")
		  return;
	  }
	}
}
else {
	try {
	}
	catch(Throwable e) {
	}

result.sort{-it.value}.eachWithIndex {key, value, index -> println "$index. $key, $value"}
def f= new File(".ld.cmd")
f.write("cd $curDir")
return;
}
